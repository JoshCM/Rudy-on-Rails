package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;

import communication.MessageInformation;
import models.helper.CompassHelper;
import models.session.EditorSessionManager;
import models.session.GameSession;
import models.session.GameSessionManager;
import models.session.RoRSession;
import resources.PropertyManager;

public class Mine extends TickableGameObject implements PlaceableOnRail {
	private final static int AMOUNT_OF_COAL_TO_LOAD = Integer.valueOf(PropertyManager.getProperty("amount_of_coal_load"));
	private final static int AMOUNT_OF_GOLD_TO_LOAD = Integer.valueOf(PropertyManager.getProperty("amount_of_gold_load"));
	private final static int TIME_TO_PRODUCE_RESOURCE_IN_SECONDS = Integer.valueOf(PropertyManager.getProperty("time_to_produce_resource_in_seconds"));
	private final static int MAX_NUMBER_OF_RESOURCES = Integer.valueOf(PropertyManager.getProperty("max_number_of_resources"));
	private final long SEC_IN_NANO = 1000000000;
	
	private List<Resource> resources = new ArrayList<Resource>();
	private UUID railId;
	private Compass alignment;
	private final int maxNumberOfResource = MAX_NUMBER_OF_RESOURCES;
	private long timeDeltaCounter = 0;// Summe der Zeit zwischen den Ticks
	private Resource res = null;
	protected RoRSession session;
	protected GameSession gameSession;
	
	// was zur hölle ist i?
	int i = 1;

	public Mine(String sessionName, Square square, Compass alignment, UUID railId) {
		super(sessionName, square);
		this.alignment = alignment;
		this.railId = railId;
		notifyCreatedMine();
	}

	public void setRailId(UUID id) {
		this.railId = id;
	}

	public Compass getAlignment() {
		return alignment;
	}

	public void setAlignment(Compass alignment) {
		this.alignment = alignment;
	}

	@Override
	public PlaceableOnRail loadFromMap(Square square, RoRSession session) {

		// Neue Mine erstellen und damit an den Client schicken
		Mine newMine = new Mine(session.getSessionName(), square, alignment, railId);
		// mitbekommen kann
		// sessionName neu setzen, damit Observer Änderung dieses Objekts
		newMine.setSessionName(session.getSessionName());
		if (session instanceof GameSession) {
			((GameSession) session).addMine(newMine);
		}
		Rail rail = (Rail) session.getMap().getPlaceableOnSquareById(railId);
		rail.setPlaceableOnRail(newMine);

		return newMine;
	}

	/**
	 * Methode zum Drehen der Mine nach Links (Dreht die Rail darunter gleich mit)
	 */
	public void rotateLeft() {
		// Das Rail wird direkt mit verschoben
		Square square = EditorSessionManager.getInstance().getEditorSessionByName(sessionName).getMap()
				.getSquareById(getSquareId());
		Rail rail = (Rail) square.getPlaceableOnSquare();
		rail.rotate(false);
		alignment = CompassHelper.rotateCompass(false, alignment);
		notifyAlignmentUpdated();
	}

	/**
	 * Methode zum Drehen der Mine nach Rechts (Dreht die Rail darunter gleich mit)
	 */
	public void rotateRight() {
		// Das Rail wird direkt mit verschoben
		Square square = EditorSessionManager.getInstance().getEditorSessionByName(sessionName).getMap()
				.getSquareById(getSquareId());
		Rail rail = (Rail) square.getPlaceableOnSquare();
		rail.rotate(true);
		alignment = CompassHelper.rotateCompass(true, alignment);
		notifyAlignmentUpdated();
	}

	/**
	 * Methode zum Erstellen einer Nachrichtm wenn eine neue Mine erstellt wurde
	 */
	private void notifyCreatedMine() {
		MessageInformation message = new MessageInformation("CreateMine");
		message.putValue("xPos", getXPos());
		message.putValue("yPos", getYPos());
		message.putValue("mineId", getId());
		message.putValue("squareId", getSquareId());
		message.putValue("alignment", alignment.toString());
		notifyChange(message);
	}

	/**
	 * Schickt eine Nachricht an den Client, wenn sich die Richtung der Mine
	 * ge�ndert hat
	 */
	private void notifyAlignmentUpdated() {
		MessageInformation message = new MessageInformation("UpdateAlignmentOfMine");
		message.putValue("alignment", alignment);
		message.putValue("mineId", getId());
		message.putValue("railId", railId.toString());
		notifyChange(message);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alignment == null) ? 0 : alignment.hashCode());
		result = prime * result + ((railId == null) ? 0 : railId.hashCode());
		result = prime * result + ((resources == null) ? 0 : resources.hashCode());
		return result;
	}

	@Override
	public void specificUpdate() {
		this.timeDeltaCounter += timeDeltaInNanoSeconds;
		if (this.timeDeltaCounter >= SEC_IN_NANO) {
			gameSession = GameSessionManager.getInstance().getGameSessionByName(sessionName);
			List<Loco> locos = gameSession.getLocos();
			for (Loco loco : locos) {
				if (!(loco instanceof GhostLoco)) {
					List<Cart> carts = loco.getCarts();

					for (Cart cart : carts) {
						if (cart.getXPos() == this.getXPos() && cart.getYPos() == this.getYPos()
								&& cart.getResource() == null && resources.size() != 0) {

							cart.loadResourceOntoCart(resources.get(0));
							removeResource();
							break;
						}
					}
				}
			}
		}
		if (this.timeDeltaCounter >= SEC_IN_NANO * TIME_TO_PRODUCE_RESOURCE_IN_SECONDS) {
			this.timeDeltaCounter = 0;
			if (resources.size() < maxNumberOfResource) {
				res = minedResource();
				resources.add(res);
				i += 1;
			}
		}
	}

	private void removeResource() {
		String resourceType = resources.get(0).getDescription();
		resources.remove(0);
		notifyResourceRemoved(resourceType);
	}
	
	private void notifyResourceRemoved(String resourceType) {
		MessageInformation messageInfo = new MessageInformation("RemoveResourceFromMine");
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("resourceType", resourceType);
		notifyChange(messageInfo);
	}

	public Resource minedResource() {
		Square square = GameSessionManager.getInstance().getGameSessionByName(sessionName).getMap()
				.getSquareById(getSquareId());
		Random random = new Random();
		if (random.nextFloat() <= 0.7) {
			res = new Coal(this.sessionName, square, AMOUNT_OF_COAL_TO_LOAD);
		} else {
			res = new Gold(this.sessionName, square, AMOUNT_OF_GOLD_TO_LOAD);

		}
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mine other = (Mine) obj;
		if (alignment != other.alignment)
			return false;
		if (railId == null) {
			if (other.railId != null)
				return false;
		} else if (!railId.equals(other.railId))
			return false;
		if (resources == null) {
			if (other.resources != null)
				return false;
		} else if (!resources.equals(other.resources))
			return false;
		return true;
	}

	public UUID getRailId() {
		return railId;
	}
}