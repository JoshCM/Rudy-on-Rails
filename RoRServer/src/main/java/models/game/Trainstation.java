package models.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import communication.MessageInformation;
import communication.queue.receiver.QueueReceiver;
import helper.Geometry;
import helper.Geometry.Coordinate;
import models.session.EditorSession;
import models.session.EditorSessionManager;
import models.session.RoRSession;

public class Trainstation extends InteractiveGameObject implements PlaceableOnSquare {
	static Logger log = Logger.getLogger(QueueReceiver.class.getName());
	
	public static final int RAIL_COUNT = 3;
	private List<UUID> trainstationRailIds;
	private Compass alignment;

	private final int CLOCKWISE = 90;
	private final int COUNTER_CLOCKWISE = -90;
	private Square spawnPointForLoco;

	// Andreas: Habe ich auf transient gesetzt, weil der Deserializer sonst
	// wieder loopt
	transient EditorSession editorSession;

	public Trainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id, Compass alignment) {
		super(sessionName, square, id);
		this.trainstationRailIds = trainstationRailIds;
		this.alignment = alignment;
		editorSession = EditorSessionManager.getInstance().getEditorSessionByName(getName());
		notifyCreatedTrainstation();
	}

	public Compass getAlignment() {
		return alignment;
	}

	public void setSpawnPointforLoco(Square square) {
		spawnPointForLoco = square;
	}

	public Square getSpawnPointforLoco() {
		return spawnPointForLoco;
	}

	private void notifyCreatedTrainstation() {
		MessageInformation messageInfo = new MessageInformation("CreateTrainstation");
		messageInfo.putValue("trainstationId", getId());
		messageInfo.putValue("alignment", alignment);
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		List<JsonObject> rails = new ArrayList<JsonObject>();
		for (UUID railId : getTrainstationRailIds()) {
			JsonObject json = new JsonObject();
			json.addProperty("railId", railId.toString());
			rails.add(json);
		}
		messageInfo.putValue("trainstationRails", rails);
		notifyChange(messageInfo);
	}

	public List<UUID> getTrainstationRailIds() {
		return trainstationRailIds;
	}

	/**
	 * Gibt die Liste von Rails der Trainstation zurück
	 * 
	 * @return
	 */
	public List<Rail> getTrainstationRails() {
		List<Rail> trainstationRails = new ArrayList<Rail>();
		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(getName());
		for (UUID railId : trainstationRailIds) {
			trainstationRails.add((Rail) editorSession.getMap().getPlaceableOnSquareById(railId));
		}
		return trainstationRails;
	}

	/**
	 * Gibt die Liste von Rails der Trainstation umgedreht zurück
	 * 
	 * @return
	 */
	public List<Rail> getReverseTrainstationRails() {
		List<Rail> trainstationRails = getTrainstationRails();
		List<Rail> shallowCopy = trainstationRails.subList(0, trainstationRails.size());
		Collections.reverse(shallowCopy);
		return shallowCopy;
	}

	private void notifyTrainstationAlignmentUpdated() {
		MessageInformation messageInformation = new MessageInformation("UpdateAlignmentOfTrainstation");
		messageInformation.putValue("id", this.getId());
		messageInformation.putValue("alignment", this.alignment.toString());
		notifyChange(messageInformation);
	}

	/**
	 * Rotiert die zugehörigen Rails einer Trainstation
	 * 
	 * @param trainstationRail
	 *            Ein Rail der Trainstation
	 * @param oldRailSquare
	 *            Square worauf die Rail vorher PlaceableOnSquare war
	 * @param newRailSquare
	 *            Square worauf die Rail PlaceableOnSquare werden soll
	 * @param right
	 *            Uhrzeigersinn/Gegen Uhrzeigersinn
	 */
	private void rotateTrainstationRails(List<Rail> trainstationRails, int pivotXPos, int pivotYPos, boolean right) {
		HashMap<Coordinate, Rail> tempRailMap = new HashMap<Coordinate, Rail>();
		for (Rail trainstationRail : trainstationRails) {
			int railXpos = trainstationRail.getXPos();
			int railYpos = trainstationRail.getYPos();

			// rotiert die koordinaten
			Geometry.Coordinate newCoordinate;
			if (right)
				newCoordinate = Geometry.rotate(railXpos, railYpos, CLOCKWISE, pivotXPos, pivotYPos);
			else
				newCoordinate = Geometry.rotate(railXpos, railYpos, COUNTER_CLOCKWISE, pivotXPos, pivotYPos);

			Square oldRailSquare = (Square) editorSession.getMap().getSquareById(trainstationRail.getSquareId());

			// rotiere und adde trainstationRail der tempList
			trainstationRail.rotate(right, right);
			tempRailMap.put(newCoordinate, trainstationRail);

			// lösche das Rail aus dem alten Square
			oldRailSquare.deletePlaceable();
		}

		for (Coordinate railCoordinate : tempRailMap.keySet()) {

			Rail tempRail = tempRailMap.get(railCoordinate);
			// bekomme sessionname für neue Rail
			String sessionName = editorSession.getName();

			// nehme section1 von RailSection
			RailSection sectionOne = tempRail.getFirstSection();

			// bekomme newSquare
			Square newRailSquare = (Square) editorSession.getMap().getSquare(railCoordinate.x, railCoordinate.y);

			// erzeuge neue Rail und setze intern das Square.PlacableOnSquare
			Rail newRail = new Rail(sessionName, newRailSquare,
					Arrays.asList(sectionOne.getNode1(), sectionOne.getNode2()), tempRail.getTrainstationId(),
					tempRail.getId());
			newRailSquare.setPlaceableOnSquare(newRail);
		}
	}

	/**
	 * Rotiert das Alignment der Trainstation
	 * 
	 * @param right
	 *            Uhrzeigersinn/Gegen Uhrzeigersinn
	 */
	private void rotateTrainstation(boolean right) {
		int newIndex;

		if (right) {
			newIndex = ((this.alignment.ordinal() + 1) % Compass.values().length);
		} else {
			newIndex = ((this.alignment.ordinal() - 1) % Compass.values().length);
			if (newIndex < 0) {
				newIndex += Compass.values().length;
			}
		}

		alignment = Compass.values()[newIndex];
		notifyTrainstationAlignmentUpdated();
	}

	/**
	 * Rotiert die Trainstation und alle zugehörigen Rails
	 * 
	 * @param right
	 *            Uhrzeigersinn/Gegen Uhrzeigersinn
	 */
	public void rotate(boolean right) {
		// rotiert die Trainstation
		rotateTrainstation(right);

		// X und Y der Trainstation
		int pivotXPos = this.getXPos();
		int pivotYPos = this.getYPos();

		List<Rail> trainstationRails = getTrainstationRails();
		// wenn im Uhzeigersinn gedreht werden soll, dann muss die liste der rails
		// umgedreht werden
		if (right)
			trainstationRails = getReverseTrainstationRails();

		rotateTrainstationRails(trainstationRails, pivotXPos, pivotYPos, right);
	}

	/**
	 * Validiert ob man die Rotation umsetzen kann
	 * 
	 * @param right
	 *            Uhrzeigersinn/Gegen Uhrzeigersinn
	 * @return (True)Validiert oder (False)nicht validiert
	 */
	public boolean validateRotation(boolean right) {
		int pivotXPos = this.getXPos();
		int pivotYPos = this.getYPos();

		List<Rail> trainstationRails = getTrainstationRails();
		if (right)
			trainstationRails = getReverseTrainstationRails();

		for (Rail trainstationRail : trainstationRails) {
			int railXpos = trainstationRail.getXPos();
			int railYpos = trainstationRail.getYPos();

			Geometry.Coordinate newCoordinate;
			if (right)
				newCoordinate = Geometry.rotate(railXpos, railYpos, CLOCKWISE, pivotXPos, pivotYPos);
			else
				newCoordinate = Geometry.rotate(railXpos, railYpos, COUNTER_CLOCKWISE, pivotXPos, pivotYPos);

			Square newRailSquare = (Square) editorSession.getMap().getSquare(newCoordinate.x, newCoordinate.y);
			if (newRailSquare == null)
				return false;
			if (newRailSquare.getPlaceableOnSquare() != null)
				if (!trainstationRailIds.contains(newRailSquare.getPlaceableOnSquare().getId()))
					return false;
		}
		return true;
	}

	@Override
	public Trainstation loadFromMap(Square square, RoRSession session) {
		Trainstation trainStation = (Trainstation) square.getPlaceableOnSquare();
		Trainstation newTrainStation = new Trainstation(session.getName(), square,
				trainStation.getTrainstationRailIds(), trainStation.getId(), trainStation.alignment);
		
		// der sessionName muss neu gesetzt werden, damit der Observer Änderungen dieses Objekts mitbekommen kann
		newTrainStation.setName(session.getName());

		log.info("TrainStation erstellt: " + newTrainStation.toString());
		return newTrainStation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CLOCKWISE;
		result = prime * result + COUNTER_CLOCKWISE;
		result = prime * result + ((alignment == null) ? 0 : alignment.hashCode());
		result = prime * result + ((spawnPointForLoco == null) ? 0 : spawnPointForLoco.hashCode());
		result = prime * result + ((trainstationRailIds == null) ? 0 : trainstationRailIds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trainstation other = (Trainstation) obj;
		if (CLOCKWISE != other.CLOCKWISE)
			return false;
		if (COUNTER_CLOCKWISE != other.COUNTER_CLOCKWISE)
			return false;
		if (alignment != other.alignment)
			return false;
		if (spawnPointForLoco == null) {
			if (other.spawnPointForLoco != null)
				return false;
		} else if (!spawnPointForLoco.equals(other.spawnPointForLoco))
			return false;
		if (trainstationRailIds == null) {
			if (other.trainstationRailIds != null)
				return false;
		} else if (!trainstationRailIds.equals(other.trainstationRailIds))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trainstation [trainstationRailIds=" + trainstationRailIds + ", alignment=" + alignment
				+ ", spawnPointForLoco=" + spawnPointForLoco + "]";
	}

	@Override
	public void specificUpdate() {
		// TODO Auto-generated method stub
		
	}
}
