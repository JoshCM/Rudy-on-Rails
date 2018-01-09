package models.game;

import java.util.ArrayList;
import java.util.UUID;

import communication.MessageInformation;

/**
 * 
 * @author Isabel Rott, Michelle Le Klasse fuer eine Lok, zu der eine Liste von
 *         Carts gehoert
 */
public class Loco extends TickableGameObject implements PlaceableOnRail {

	private final long SEC_IN_NANO = 1000000000;
	private ArrayList<Cart> carts;
	private Rail rail;
	private UUID playerId;
	private long timeDeltaCounter = 0;// Summe der Zeit zwischen den Ticks
	private long speed;
	private Compass compass;
	private Map map;
	private Square square;

	/**
	 * Konstruktor einer Lok
	 * 
	 * @param square
	 *            auf dem die Lok steht wird mitgegeben
	 */
	public Loco(String sessionName, Square square, Map map, UUID playerId) {
		// super(sessionName, square);
		this.setCarts(new ArrayList<Cart>());
		// TODO: Wenn Zug Richtung implementiert ist, muss der Wagon so initialisiert
		// werden, dass er ein Feld hinter der Lok steht
		this.addCart(new Cart());
		this.square = square;// Das hier muss noch raus, aber erst testen
		this.rail = (Rail) square.getPlaceableOnSquare();
		this.map = map;
		this.compass = rail.getFirstSection().getNode1();
		this.speed = 0; // Nur zu testzwecken
		this.playerId = playerId;
		SendCreatedLocoMessage();
	}

	/**
	 * Fuege Carts zur Liste der Loco hinzu
	 * 
	 * @param cart
	 */
	public void addCart(Cart cart) {
		this.carts.add(cart);

	}

	/**
	 * steuert das spezifische verhalten der Lok beim eintreten eines Ticks
	 */
	@Override
	public void specificUpdate() {
		if (speed != 0) {
			this.timeDeltaCounter += timeDeltaInNanoSeconds;
			if (this.timeDeltaCounter >= SEC_IN_NANO / speed) {
				timeDeltaCounter = 0;
				drive();
			}
		}

	}

	/**
	 * Überführt die Lok in das nächste mögliche Feld in Fahrtrichtung
	 */
	public void drive() {
		Rail nextRail = getNextRail();
		this.compass = nextRail.getExitDirection(getDirectionNegation());
		this.rail = nextRail;
		// this.setXPos(this.rail.getX());
		// this.setYPos(this.rail.getY);
		SendUpdateLocoMessage();

	}

	/**
	 * Gibt die nächste Rail in Fahrtrichtung zurück
	 * 
	 * @return nextRail
	 */
	public Rail getNextRail() {
		switch (this.compass) {
		case NORTH:
			this.square = this.map.getSquare(this.square.getX(), this.square.getY() - 1);
			return (Rail) this.square.getPlaceableOnSquare();
		case EAST:
			this.square = this.map.getSquare(this.square.getX() + 1, this.square.getY());
			return (Rail) this.square.getPlaceableOnSquare();
		case SOUTH:
			this.square = this.map.getSquare(this.square.getX(), this.square.getY() + 1);
			return (Rail) this.square.getPlaceableOnSquare();
		case WEST:
			this.square = this.map.getSquare(this.square.getX() - 1, this.square.getY());
			return (Rail) this.square.getPlaceableOnSquare();
		}
		return null;
	}

	/**
	 * Negiert die aktuelle Fahrtrichtung Ist benötigt um einen U-Turn zu
	 * verhindern.
	 * 
	 * @return negated Direction
	 */
	public Compass getDirectionNegation() {
		switch (this.compass) {
		case NORTH:
			return Compass.SOUTH;
		case EAST:
			return Compass.WEST;
		case SOUTH:
			return Compass.NORTH;
		case WEST:
			return Compass.EAST;
		}
		return null;
	}

	private void SendCreatedLocoMessage() {
		MessageInformation messageInfo = new MessageInformation("CreateLoco");
		messageInfo.putValue("locoId", getID());
		messageInfo.putValue("xPos", getX());
		messageInfo.putValue("yPos", getY);
		messageInfo.putValue("playerId", this.playerId);
		notifyChange(messageInfo);
	}

	private void SendUpdateLocoMessage() {
		MessageInformation messageInfo = new MessageInformation("UpdateLocoPosition");
		messageInfo.putValue("locoId", getID());
		messageInfo.putValue("xPos", getX());
		messageInfo.putValue("yPos", getY);
		messageInfo.putValue("playerId", this.playerId);
		notifyChange(messageInfo);
	}
	
	public void changeSpeed(int speed) {
		this.speed = speed;
		notifySpeedChanged();
	}
	
	private void notifySpeedChanged() {
		MessageInformation messageInfo = new MessageInformation("UpdateLocoSpeed");
		messageInfo.putValue("locoId", getID());
		messageInfo.putValue("speed", speed);
		notifyChange(messageInfo);
	}

	// Getter und Setter
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public ArrayList<Cart> getCarts() {
		return carts;
	}

	public void setCarts(ArrayList<Cart> carts) {
		this.carts = carts;
	}

	/**
	 * Finde die Rail, auf der die Loco steht
	 * 
	 * @return rail
	 */
	public Rail getRail() {
		return this.rail;
	}

	public long getSpeed() {
		return this.speed;
	}

	public UUID getPlayerId() {
		return playerId;
	}
}
