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
		super(sessionName, square);
		this.setCarts(new ArrayList<Cart>());
		// TODO: Wenn Zug Richtung implementiert ist, muss der Wagon so initialisiert
		// werden, dass er ein Feld hinter der Lok steht
		this.square = square;// Das hier muss noch raus? Sollte man nicht an das InteractivGameoObject das Square packen?
		this.rail = (Rail) square.getPlaceableOnSquare();
		this.map = map;
		this.compass = rail.getFirstSection().getNode1();
		this.speed = 0; // Nur zu testzwecken
		this.playerId = playerId;
		SendCreatedLocoMessage();
		this.addCart();
	}

	/**
	 * Fuege Carts zur Liste der Loco hinzu
	 * 
	 * @param cart
	 */
	public void addCart() {
		Rail prevRail = null;
		Square cartSquare = null;
		if(carts.isEmpty()) {
			Compass compass = this.getCompassNegation();
			prevRail = getPreviousRail(compass);
			cartSquare = map.getSquare(prevRail.getXPos(), prevRail.getYPos());
		}
		else {
			
		}
		carts.add(new Cart(this.sessionName, cartSquare));
		SendAddCartMessage(cartSquare);
		
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
		moveCarts(this.rail);
		this.compass = nextRail.getExitDirection(getCompassNegation());
		this.rail = nextRail;
		this.setXPos(this.rail.getXPos());
		this.setYPos(this.rail.getYPos());
		SendUpdateLocoMessage();

	}
	
	/**
	 * bewegt alle Wagons, die an einer Lok h�ngen, sobald sich eine Lok um ein Feld weiter bewegt hat
	 * 
	 * @param forward
	 */
	public void moveCarts(Rail forward) {
		Square newSquare = this.map.getSquare(forward.getXPos(), forward.getYPos());
		Square tempSquare = null;
		for(Cart c: carts) {
			tempSquare = this.map.getSquareById(c.getSquareId());
			c.updateSquare(newSquare);
			newSquare = tempSquare;
		}
	}

	/**
	 * Gibt die nächste Rail in Fahrtrichtung zurück
	 * 
	 * @return nextRail
	 */
	public Rail getNextRail() {
		switch (this.compass) {
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

	
	public Rail getPreviousRail(Compass compass) {
		Square square;
		switch (compass) {
		case NORTH:
			square = this.map.getSquare(this.square.getXIndex(), this.square.getYIndex() - 1);
			return (Rail) square.getPlaceableOnSquare();
		case EAST:
			square = this.map.getSquare(this.square.getXIndex() + 1, this.square.getYIndex());
			return (Rail) square.getPlaceableOnSquare();
		case SOUTH:
			square = this.map.getSquare(this.square.getXIndex(), this.square.getYIndex() + 1);
			return (Rail) square.getPlaceableOnSquare();
		case WEST:
			square = this.map.getSquare(this.square.getXIndex() - 1, this.square.getYIndex());
			return (Rail) square.getPlaceableOnSquare();
		}
		return null;
	}
	
	/**
	 * Negiert die aktuelle Fahrtrichtung Ist benötigt um einen U-Turn zu
	 * verhindern.
	 * 
	 * @return negated Direction
	 */
	public Compass getCompassNegation() {
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
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("playerId", this.playerId);
		notifyChange(messageInfo);
	}

	private void SendUpdateLocoMessage() {
		MessageInformation messageInfo = new MessageInformation("UpdateLocoPosition");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("playerId", this.playerId);
		notifyChange(messageInfo);
	}
	
	private void SendAddCartMessage(Square square) {
		MessageInformation messageInfo = new MessageInformation("CreateCart");
		messageInfo.putValue("playerId", this.playerId);
		messageInfo.putValue("xPos", square.getXIndex());
		messageInfo.putValue("yPos", square.getYIndex());
		notifyChange(messageInfo);
	}
	
	public void changeSpeed(int speed) {
		this.speed = speed;
		notifySpeedChanged();
	}
	
	private void notifySpeedChanged() {
		MessageInformation messageInfo = new MessageInformation("UpdateLocoSpeed");
		messageInfo.putValue("locoId", getId());
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
