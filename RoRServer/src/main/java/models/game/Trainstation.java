package models.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import communication.MessageInformation;
import communication.queue.receiver.QueueReceiver;
import helper.Geometry;
import helper.Geometry.Coordinate;
import models.session.EditorSession;
import models.session.EditorSessionManager;

public abstract class Trainstation extends InteractiveGameObject implements PlaceableOnSquare {
	static Logger log = Logger.getLogger(QueueReceiver.class.getName());

	public static final int RAIL_COUNT_RIGHT = 8;
	public static final int RAIL_COUNT_LEFT = 6;
	protected List<UUID> trainstationRailIds;
	protected Stock stock;
	private Crane crane;
	protected Compass alignment;
	protected UUID playerId;

	protected static final int CLOCKWISE = 90;
	protected static final int COUNTER_CLOCKWISE = -90;

	transient EditorSession editorSession;

	public Trainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id, Compass alignment,
			Stock stock) {
		super(sessionName, square, id);
		this.stock = stock;
		this.trainstationRailIds = trainstationRailIds;
		this.alignment = alignment;
		editorSession = EditorSessionManager.getInstance().getEditorSessionByName(getDescription());
	}

	/**
	 * Gibt die Ausrichtung der Trainstation zurück
	 * 
	 * @return Ausrichtung als Compass
	 */
	public Compass getAlignment() {
		return alignment;
	}

	/**
	 * Gibt alle IDs der Rails der Trainstation zurück
	 * 
	 * @return IDs der trainstationRails
	 */
	public List<UUID> getTrainstationRailIds() {
		return trainstationRailIds;
	}

	/**
	 * Gibt den Stock als Object zurück
	 * 
	 * @return Den Stock
	 */
	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock newStock) {
		this.stock = newStock;
	}

	public Crane getCrane() {
		return crane;
	}

	public void setCrane(Crane crane) {
		this.crane = crane;
	}

	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}

	public UUID getPlayerId() {
		if (this.playerId == null)
			return new UUID(0L, 0L);
		return this.playerId;
	}

	/**
	 * Gibt die Liste von Rails der Trainstation zurück
	 * 
	 * @return trainstationRails
	 */
	public List<Rail> getTrainstationRails() {
		List<Rail> trainstationRails = new ArrayList<Rail>();
		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(getDescription());
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
	private void rotateTrainstationInteractiveGameObjects(List<InteractiveGameObject> trainstationGameObjects,
			int pivotXPos, int pivotYPos, boolean right) {
		HashMap<Coordinate, InteractiveGameObject> temptrainstationGameObjectMap = new HashMap<Coordinate, InteractiveGameObject>();
		for (InteractiveGameObject trainstationGameObject : trainstationGameObjects) {
			int objectXpos = trainstationGameObject.getXPos();
			int objectYpos = trainstationGameObject.getYPos();

			// rotiert die koordinaten
			Geometry.Coordinate newCoordinate;
			if (right)
				newCoordinate = Geometry.rotate(objectXpos, objectYpos, CLOCKWISE, pivotXPos, pivotYPos);
			else
				newCoordinate = Geometry.rotate(objectXpos, objectYpos, COUNTER_CLOCKWISE, pivotXPos, pivotYPos);

			Square oldSquare = (Square) editorSession.getMap().getSquareById(trainstationGameObject.getSquareId());

			// rotiere und adde der tempList
			if (trainstationGameObject instanceof Rail) {
				Rail tmpRail = (Rail) trainstationGameObject;
				tmpRail.rotate(right, right);
			} else if (trainstationGameObject instanceof Stock) {
				Stock tmpStock = (Stock) trainstationGameObject;
				tmpStock.rotate(right);
			}

			temptrainstationGameObjectMap.put(newCoordinate, trainstationGameObject);

			// lösche das Rail aus dem alten Square
			oldSquare.deletePlaceable();
		}

		for (Coordinate coordinate : temptrainstationGameObjectMap.keySet()) {

			InteractiveGameObject tmpTrainstationGameObject = temptrainstationGameObjectMap.get(coordinate);
			// bekomme sessionname für neue Rail
			String sessionName = editorSession.getDescription();
			// bekomme newSquare
			Square newSquare = (Square) editorSession.getMap().getSquare(coordinate.x, coordinate.y);

			if (tmpTrainstationGameObject instanceof Rail) {
				Rail tmpRail = (Rail) tmpTrainstationGameObject;

				// nehme RailSections und erzeuge eine Liste von Compass daraus
				List<RailSection> railSections = tmpRail.getRailSectionList();
				List<Compass> railSectionsCompass = new ArrayList<Compass>();
				for (RailSection railsection : railSections) {
					railSectionsCompass.addAll(railsection.getNodes());
				}

				Rail newRail;
				// wenn die Rail ein Switch ist
				if (tmpRail instanceof Switch) {
					newRail = new Switch(sessionName, newSquare, railSectionsCompass, tmpRail.getTrainstationId(),
							tmpRail.getId());
				} else {
					// erzeuge neue Rail und setze intern das Square.PlacableOnSquare
					newRail = new Rail(sessionName, newSquare, railSectionsCompass, false, tmpRail.getTrainstationId(),
							tmpRail.getId(), tmpRail.placeableOnRail);
				}
				newSquare.setPlaceableOnSquare(newRail);
			} else if (tmpTrainstationGameObject instanceof Stock) {
				Stock tmpStock = (Stock) tmpTrainstationGameObject;

				// erzeuge neuen Stock und setze intern das Square.PlacableOnSquare
				Stock newStock = new Stock(sessionName, newSquare, tmpStock.getTrainstationId(), tmpStock.getId(),
						tmpStock.getAlignment());
				newSquare.setPlaceableOnSquare(newStock);
				this.stock = newStock;
			}
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

		List<InteractiveGameObject> trainstationInteractiveGameObjects = new ArrayList<InteractiveGameObject>();
		trainstationInteractiveGameObjects.addAll(trainstationRails);
		trainstationInteractiveGameObjects.add(stock);

		rotateTrainstationInteractiveGameObjects(trainstationInteractiveGameObjects, pivotXPos, pivotYPos, right);

		// wir sagen dem Crane das sich alles gedreht hat und wir jetzt auf nem anderen
		// Square stehen
		Rail craneRail = getRailbyId(this.crane.getRailId());
		Square newSquare = EditorSessionManager.getInstance().getEditorSessionByName(sessionName).getMap()
				.getSquareById(craneRail.getSquareId());
		// crane.rotateCrane(newSquare, this.alignment);
		this.crane = new Crane(this.sessionName, newSquare, this.getId(),
				Crane.getCraneAlignmentbyTrainstationAlignment(this.alignment), craneRail.getId());
	}

	private Rail getRailbyId(UUID railId) {
		// TODO Auto-generated method stub
		for (Rail rail : getTrainstationRails()) {
			if (rail.getId().equals(railId)) {
				return rail;
			}
		}
		return null;
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
					if (!stock.getId().equals(newRailSquare.getPlaceableOnSquare().getId()))
						return false;
		}

		// validiert die rotation des Stocks
		Geometry.Coordinate newStockCoordinate;
		if (right)
			newStockCoordinate = Geometry.rotate(stock.getXPos(), stock.getYPos(), CLOCKWISE, pivotXPos, pivotYPos);
		else
			newStockCoordinate = Geometry.rotate(stock.getXPos(), stock.getYPos(), COUNTER_CLOCKWISE, pivotXPos,
					pivotYPos);
		Square newStockSquare = (Square) editorSession.getMap().getSquare(newStockCoordinate.x, newStockCoordinate.y);
		if (newStockSquare.getPlaceableOnSquare() != null)
			if (!trainstationRailIds.contains(newStockSquare.getPlaceableOnSquare().getId()))
				return false;

		return true;
	}
}
