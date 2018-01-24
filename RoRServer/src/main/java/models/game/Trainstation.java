package models.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.activemq.console.command.CreateCommand;
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

	public static final int RAIL_COUNT_RIGHT = 8;
	public static final int RAIL_COUNT_LEFT = 6;
	private List<UUID> trainstationRailIds;
	private Stock stock;
	private Crane crane;
	private Compass alignment;
	private UUID playerId;

	private final int CLOCKWISE = 90;
	private final int COUNTER_CLOCKWISE = -90;
	
	private UUID spawnPointForLoco;
	private UUID spawnPointForCart;

	transient EditorSession editorSession;

	public Trainstation(String sessionName, Square square,List<UUID> trainstationRailIds, UUID id, Compass alignment,
			Stock stock, Crane crane) {
		super(sessionName, square, id);
		this.stock = stock;
		this.trainstationRailIds = trainstationRailIds;
		this.alignment = alignment;
		this.crane = crane;
		editorSession = EditorSessionManager.getInstance().getEditorSessionByName(getDescription());
		notifyCreatedTrainstation();
	}

	/**
	 * Gibt die Ausrichtung der Trainstation zurück
	 * @return Ausrichtung als Compass
	 */
	public Compass getAlignment() {
		return alignment;
	}

	public void setSpawnPointforLoco(UUID railId) {
		spawnPointForLoco = railId;
	}

	public UUID getSpawnPointforLoco() {
		return spawnPointForLoco;
	}

	/**
	 * Gibt den SpawnPoint für neue Carts zurück
	 * @return
	 */
	public UUID getSpawnPointForCart() {
		return spawnPointForCart;
	}

	/**
	 * Setzt den SpawnPoint für neue Carts
	 * @param spawnPointForCart
	 */
	public void setSpawnPointForCart(UUID spawnPointForCart) {
		this.spawnPointForCart = spawnPointForCart;
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
		messageInfo.putValue("stockId", getStock().getId());

		notifyChange(messageInfo);
	}

	/**
	 * Gibt alle IDs der Rails der Trainstation zurück
	 * @return IDs der trainstationRails
	 */
	public List<UUID> getTrainstationRailIds() {
		return trainstationRailIds;
	}

	/**
	 * Gibt den Stock als Object zurück
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
		return this.playerId;
	}
	
	/**
	 * Gibt die Liste von Rails der Trainstation zurück
	 * 
	 * @return
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
	private void rotateTrainstationInteractiveGameObjects(List<InteractiveGameObject> trainstationGameObjects, int pivotXPos,
			int pivotYPos, boolean right) {
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
				Rail tmpRail = (Rail)tmpTrainstationGameObject;
				
				// nehme RailSections und erzeuge eine Liste von Compass daraus
				List<RailSection> railSections = tmpRail.getRailSectionList();
				List<Compass> railSectionsCompass = new ArrayList<Compass>();
				for(RailSection railsection : railSections) {
					railSectionsCompass.addAll(railsection.getNodes());
				}
				
				// erzeuge neue Rail und setze intern das Square.PlacableOnSquare
				Rail newRail = new Rail(sessionName, newSquare, railSectionsCompass, false,
						tmpRail.getTrainstationId(), tmpRail.getId(),tmpRail.placeableOnRail);
				newSquare.setPlaceableOnSquare(newRail);
			}else if(tmpTrainstationGameObject instanceof Stock) {
				Stock tmpStock = (Stock)tmpTrainstationGameObject;

				// erzeuge neuen Stock und setze intern das Square.PlacableOnSquare
				Stock newStock = new Stock(sessionName, newSquare, tmpStock.getTrainstationId(), tmpStock.getId(), tmpStock.getAlignment());
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
		
		//wir sagen dem Crane das sich alles gedreht hat und wir jetzt auf nem anderen Square stehen
		Rail craneRail = getRailbyId(this.crane.getRailId());
		Square newSquare = EditorSessionManager.getInstance().getEditorSessionByName(sessionName).getMap().getSquareById(craneRail.getSquareId());
//		crane.rotateCrane(newSquare, this.alignment);
		this.crane = new Crane(this.sessionName, newSquare, this.getId(), Crane.getCraneAlignmentbyTrainstationAlignment(this.alignment), craneRail.getId());
	}

	private Rail getRailbyId(UUID railId) {
		// TODO Auto-generated method stub
		for(Rail rail: getTrainstationRails()) {
			if(rail.getId().equals(railId)) {
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
					if(!stock.getId().equals(newRailSquare.getPlaceableOnSquare().getId()))
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

	@Override
	public Trainstation loadFromMap(Square square, RoRSession session) {
		Trainstation oldTrainStation = (Trainstation) square.getPlaceableOnSquare();
		Trainstation newTrainStation = new Trainstation(session.getDescription(), square,
				oldTrainStation.getTrainstationRailIds(), oldTrainStation.getId(), oldTrainStation.alignment, oldTrainStation.getStock(), oldTrainStation.getCrane());
		
		// der sessionName muss neu gesetzt werden, damit der Observer Änderungen dieses Objekts mitbekommen kann
		newTrainStation.setSessionName(session.getDescription());
		
		// setze den alten SpawnPoint für die neue Trainstation
		newTrainStation.setSpawnPointforLoco(oldTrainStation.getSpawnPointforLoco());

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
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
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
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
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



}
