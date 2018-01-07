package models.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import communication.MessageInformation;
import models.session.GameSessionManager;

/**
 * 
 * @author Isabel Rott, Michelle Le Klasse fuer eine Lok, zu der eine Liste von
 *         Carts gehoert
 */
public class Loco extends TickableGameObject {
	private final long SEC_IN_NANO = 1000000000;
	private ArrayList<Cart> carts;
	private Rail rail;
	private UUID playerId;
	private long timeDeltaCounter = 0;// Summe der Zeit zwischen den Ticks
	private long speed;
	private Compass drivingDirection;
	private boolean reversed = true;
	private Map map;
	private Square square;

	/**
	 * Konstruktor einer Lok
	 * 
	 * @param square
	 *            auf dem die Lok steht wird mitgegeben
	 */
	public Loco(String sessionName, Square square, UUID playerId) {
		super(sessionName, square);
		this.setCarts(new ArrayList<Cart>());
		// TODO: Wenn Zug Richtung implementiert ist, muss der Wagon so initialisiert
		// werden, dass er ein Feld hinter der Lok steht
		this.square = square;// Das hier muss noch raus? Sollte man nicht an das InteractivGameoObject das Square packen?
		this.rail = (Rail) square.getPlaceableOnSquare();
		this.map = GameSessionManager.getInstance().getGameSessionByName(sessionName).getMap();
		this.drivingDirection = rail.getFirstSection().getNode1();
		this.speed = 0; 
		this.playerId = playerId;
		NotifyLocoCreated();
		this.addInitialCart();
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
				if(reversed) {
					reversedDrive();
				}else {
					drive();
				}
			}
		}

	}

	/**
	 * Ueberfuehrt die Lok in das naechste moegliche Feld in Fahrtrichtung
	 */
	public void drive() {
		Rail nextRail = getNextRail();
		moveCarts(this.rail,this.drivingDirection);
		this.drivingDirection = nextRail.getExitDirection(getDirectionNegation());
		this.rail = nextRail;
		this.setXPos(this.rail.getXPos());
		this.setYPos(this.rail.getYPos());
		NotifyLocoPositionChanged();
	}

	/**
	 * lok und ihre Wagons fahren "rueckwaerts" d.h. ein Feld zurueck
	 */
	public void reversedDrive() {

		
		Cart actCart = carts.get(carts.size()-1);

		Compass back = actCart.getRail().getExitDirection(actCart.getCompass());

		Rail newCartRail = getPreviousRail(back,this.map.getSquare(actCart.getXPos(), actCart.getYPos()));

		Square newSquare = this.map.getSquare(newCartRail.getXPos(),newCartRail.getYPos());
		Square tempSquare = this.map.getSquare(actCart.getXPos(), actCart.getYPos());
		
		actCart.updateSquare(newSquare);
		actCart.setRail(newCartRail);
		actCart.setCompass(getCompassNegation(back));
		actCart.SendUpdateCartMessage();
		newSquare = tempSquare;
		
		//ist noch nicht getestet f�r mehrere Carts
		for(int i = carts.size()-2 ; i>=0 ; i--) {
			actCart = carts.get(i);
			back = actCart.getRail().getExitDirection(actCart.getCompass());
			tempSquare = this.map.getSquare(actCart.getXPos(), actCart.getYPos());
			actCart.updateSquare(newSquare);
			actCart.setRail((Rail)newSquare.getPlaceableOnSquare());
			actCart.setCompass(getCompassNegation(back));
			actCart.SendUpdateCartMessage();
			newSquare = tempSquare;
			
		
		}
		

		this.updateSquare(newSquare);
		this.rail = (Rail)newSquare.getPlaceableOnSquare();
		drivingDirection = this.rail.getExitDirection(getCompassNegation(actCart.getCompass()));
		NotifyLocoPositionChanged();
		
		
	}
	
	/**
	 * f�gt der Lok initial ein Cart hinzu auf das vorige Feld 
	 */
	public void addInitialCart() {
		if(carts.isEmpty()) {
			Compass back = this.rail.getExitDirection(this.drivingDirection);
			Rail prevRail = getPreviousRail(back);
			Square cartSquare = this.map.getSquare(prevRail.getXPos(), prevRail.getYPos());
			Cart cart = new Cart(this.sessionName,cartSquare,getCompassNegation(back),playerId,prevRail);
			carts.add(cart);
			SendAddCartMessage(cartSquare,cart.getId());
		}
		
	}
	/**
	 * bewegt alle Wagons, die an einer Lok h�ngen, sobald sich eine Lok um ein Feld weiter bewegt hat
	 * 
	 * @param forward
	 */
	public void moveCarts(Rail forward,Compass compass) {
		Square newSquare = this.map.getSquare(forward.getXPos(), forward.getYPos());
		Square tempSquare = null;
		Compass newCompass = compass;
		Compass tempCompass = null;
		for(Cart c: carts) {
			tempSquare = this.map.getSquareById(c.getSquareId());
			tempCompass = c.getCompass();
			c.updateSquare(newSquare);
			c.setCompass(newCompass);
			c.SendUpdateCartMessage();
			newCompass = tempCompass;
			newSquare = tempSquare;
		}
	}

	/**
	 * Gibt die nächste Rail in Fahrtrichtung zurück
	 * 
	 * @return nextRail
	 */
	public Rail getNextRail() {
		switch (this.drivingDirection) {
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
	 * gibt das Rail zur�ck, dass in angegebener Richtung an das Feld, auf der die Lok steht, angekoppelt ist
	 * @param compass Himmelsrichtung in die man nach dem Feld schauen soll
	 * @return die Rail, die in angegebener Richtung steht
	 */
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
	 * gibt das Rail zur�ck, dass in angegebener Richtung an das Feld, das mitgegeben wird, angekoppelt ist
	 * @param compass Himmelsrichtung in die man nach dem Feld schauen soll
	 * @return die Rail, die in angegebener Richtung steht
	 */
	public Rail getPreviousRail(Compass compass, Square square) {
		Square retSquare;
		switch (compass) {
		case NORTH:
			retSquare = this.map.getSquare(square.getXIndex(), square.getYIndex() - 1);
			return (Rail) retSquare.getPlaceableOnSquare();
		case EAST:
			retSquare = this.map.getSquare(square.getXIndex() + 1, square.getYIndex());
			return (Rail) retSquare.getPlaceableOnSquare();
		case SOUTH:
			retSquare = this.map.getSquare(square.getXIndex(),square.getYIndex() + 1);
			return (Rail) retSquare.getPlaceableOnSquare();
		case WEST:
			retSquare = this.map.getSquare(square.getXIndex() - 1, square.getYIndex());
			return (Rail) retSquare.getPlaceableOnSquare();
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
		switch (this.drivingDirection) {
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
	
	public Compass getCompassNegation(Compass compass) {
		switch (compass) {
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

	private void NotifyLocoCreated() {
		MessageInformation messageInfo = new MessageInformation("CreateLoco");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("drivingDirection", drivingDirection.toString());
		messageInfo.putValue("playerId", this.playerId);
		notifyChange(messageInfo);
	}

	private void NotifyLocoPositionChanged() {
		MessageInformation messageInfo = new MessageInformation("UpdateLocoPosition");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("drivingDirection", drivingDirection.toString());
		messageInfo.putValue("playerId", this.playerId);
		notifyChange(messageInfo);
	}
	
	private void SendAddCartMessage(Square square, UUID cartId) {
		MessageInformation messageInfo = new MessageInformation("CreateCart");
		messageInfo.putValue("playerId", this.playerId);
		messageInfo.putValue("cartId", cartId);
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
