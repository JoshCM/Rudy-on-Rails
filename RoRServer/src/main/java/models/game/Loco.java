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
	private Direction direction;
	private Map map;
	private Square square;

	/**
	 * Konstruktor einer Lok
	 * 
	 * @param square
	 *            auf dem die Lok steht wird mitgegeben
	 */
	public Loco(String sessionName, Square square, Map map, UUID playerId) {
		super(sessionName, square);
		this.setCarts(new ArrayList<Cart>());
		// TODO: Wenn Zug Richtung implementiert ist, muss der Wagon so initialisiert
		// werden, dass er ein Feld hinter der Lok steht
		this.addCart(new Cart(sessionName, square));
		this.square = square;// Das hier muss noch raus, aber erst testen
		this.rail = (Rail) square.getPlaceableOnSquare();
		this.map = map;
		this.direction = rail.getFirstSection().getNode1();
		this.speed = 1; // Nur zu testzwecken
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
		this.direction = nextRail.getExitDirection(getDirectionNegation());
		this.rail = nextRail;
		this.setXPos(this.rail.getXPos());
		this.setYPos(this.rail.getYPos());
		System.out.println("PosX " + getXPos() + "PosY" + getYPos());
		SendUpdateLocoMessage();

	}

	/**
	 * Gibt die nächste Rail in Fahrtrichtung zurück
	 * 
	 * @return nextRail
	 */
	public Rail getNextRail() {
		switch (this.direction) {
		case NORTH:
			this.square = this.map.getSquare(this.square.getXIndex(), this.square.getYIndex() - 1);
			return (Rail) this.square.getPlaceableOnSquare();
		case EAST:
			this.square = this.map.getSquare(this.square.getXIndex() + 1, this.square.getYIndex());
			return (Rail) this.square.getPlaceableOnSquare();
		case SOUTH:
			this.square = this.map.getSquare(this.square.getXIndex(), this.square.getYIndex() + 1);
			return (Rail) this.square.getPlaceableOnSquare();
		case WEST:
			this.square = this.map.getSquare(this.square.getXIndex() - 1, this.square.getYIndex());
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
	public Direction getDirectionNegation() {
		switch (this.direction) {
		case NORTH:
			return Direction.SOUTH;
		case EAST:
			return Direction.WEST;
		case SOUTH:
			return Direction.NORTH;
		case WEST:
			return Direction.EAST;
		}
		return null;
	}

	private void SendCreatedLocoMessage() {
		MessageInformation messageInfo = new MessageInformation("CreateLoco");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("playerId", this.playerId);
		System.out.println("LOCO WURDE ERSTELLT; NACHRICHT WIRD ZUR�CKGESCHICKT");
		notifyChange(messageInfo);
	}

	private void SendUpdateLocoMessage() {
		MessageInformation messageInfo = new MessageInformation("UpdateLoco");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("playerId", this.playerId);
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
