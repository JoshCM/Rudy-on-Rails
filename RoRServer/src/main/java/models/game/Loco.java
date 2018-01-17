package models.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import communication.MessageInformation;
import models.session.GameSessionManager;
import models.session.RoRSession;

/**
 * 
 * @author Isabel Rott, Michelle Le Klasse fuer eine Lok, zu der eine Liste von
 *         Carts gehoert
 */
public class Loco extends InteractiveGameObject {
	private ArrayList<Cart> carts;
	private Rail rail;
	private UUID playerId;
	private long timeDeltaCounter = 0; // Summe der Zeit zwischen den Ticks
	private long speed;
	private Compass drivingDirection;
	private boolean reversed = false;
	private Map map;

	/**
	 * Konstruktor einer Lok
	 * 
	 * @param square
	 *            auf dem die Lok steht wird mitgegeben
	 */
	public Loco(String sessionName, Square square, UUID playerId) {
		super(sessionName, square);
		this.setCarts(new ArrayList<Cart>());
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
			int absoluteSpeed = (int) Math.abs(speed);
			if (this.timeDeltaCounter >= SEC_IN_NANO / absoluteSpeed) {
				timeDeltaCounter = 0;
				if (speed < 0) {
					if (!reversed) {
						reversed = true;
						reversedDrive(true);
						//initialReversedDrive();
					} else {
						reversedDrive(false);
					}
				} else if (speed > 0) {
					if (reversed) {
						this.drivingDirection = this.rail.getExitDirection(this.drivingDirection);
						for (int i = carts.size() - 1; i >= 0; i--) {
							Cart c = carts.get(i);
							c.setDrivingDirection(c.getRail().getExitDirection(c.getDrivingDirection()));
						}
						reversed = false;
					}
					drive();
				}
			}
		}
	}

	/**
	 * Ueberfuehrt die Lok in das naechste moegliche Feld in Fahrtrichtung
	 */
	public void drive() {
		Rail nextRail = getNextRail(this.drivingDirection,
				this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
		if(nextRail != null) {
		moveCarts(this.rail, this.drivingDirection);
		this.drivingDirection = nextRail.getExitDirection(getDirectionNegation(this.drivingDirection));
		this.rail = nextRail;
		this.updateSquare(this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
		NotifyLocoPositionChanged();
		}
		else {
			this.speed = 0;
		}
	}
	
	/**
	 * Zug fährt rückwerts(letzter Wagon führt)
	 * @param initial: Sagt ob der Zug grade von vorwärts in Rückwärts 
	 */
	public void reversedDrive(boolean initial) {

		Cart actCart = null;
		Compass tempDirection;
		//beim Rückwärtsfahren ist natürlich die letzte Cart vorne, also bewegen wir erst die Carts und dann nach der for-Schleife den Zuch
		for (int i = carts.size() - 1; i >= 0; i--) {
			actCart = carts.get(i);
			//Wenn von Vorwärts in Rückwärts geändert wird muss die Drivingdirection erstmal umgedreht werden.
			if(initial) {
				tempDirection = actCart.getRail().getExitDirection(actCart.getDrivingDirection());
			}
			else {
				tempDirection = actCart.getDrivingDirection();
			}
			
			Rail newRail = getNextRail(tempDirection, this.map.getSquare(actCart.getXPos(), actCart.getYPos()));
			
			if(newRail!=null) {//Wenn das Nächste Schienenstück leer ist soll der zu anhalten
				Compass newDrivingDirection = newRail.getExitDirection(getDirectionNegation(tempDirection));

				actCart.setDrivingDirection(newDrivingDirection);
				actCart.setRail(newRail);
				actCart.updateSquare(this.map.getSquare(newRail.getXPos(), newRail.getYPos()));
				actCart.notifyUpdatedCart();
			}
			else {
				this.speed = 0; 
				break;
			}
		}
		if(this.speed != 0) {//Wenn das nächste schienenstück der Cart leer ist darf der Zug natürlich auch nicht weiter düsen
			if(initial)
				tempDirection = this.rail.getExitDirection(this.drivingDirection);
			else
				tempDirection = this.drivingDirection;
			
			this.rail = getNextRail(tempDirection, this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
			this.drivingDirection = this.rail.getExitDirection(getDirectionNegation(tempDirection));
			this.updateSquare(this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
			NotifyLocoPositionChanged();
		}
	}

	/**
	 * wenn der Zug das erste mal rückwerts fahren soll(alle Fahrtrichtungen sollen umgedreht werden)
	 */
	public void initialReversedDrive() {

		Cart actCart = null;
		for (int i = carts.size() - 1; i >= 0; i--) {
			actCart = carts.get(i);
			Compass tempDirection = actCart.getRail().getExitDirection(actCart.getDrivingDirection());
			Rail newRail = getNextRail(tempDirection, this.map.getSquare(actCart.getXPos(), actCart.getYPos()));
			Compass newDrivingDirection = newRail.getExitDirection(getDirectionNegation(tempDirection));

			actCart.setDrivingDirection(newDrivingDirection);
			actCart.setRail(newRail);
			actCart.updateSquare(this.map.getSquare(newRail.getXPos(), newRail.getYPos()));
			actCart.notifyUpdatedCart();

		}

		Compass tempDirection = this.rail.getExitDirection(this.drivingDirection);
		this.rail = getNextRail(tempDirection, this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
		this.drivingDirection = this.rail.getExitDirection(getDirectionNegation(tempDirection));

		this.updateSquare(this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));

		NotifyLocoPositionChanged();
	}

	/**
	 * fï¿½gt der Lok initial ein Cart hinzu auf das vorige Feld
	 */
	public void addInitialCart() {
		if (carts.isEmpty()) {
			Compass back = this.rail.getExitDirection(this.drivingDirection);
			Rail prevRail = getNextRail(back, this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
			Square cartSquare = this.map.getSquare(prevRail.getXPos(), prevRail.getYPos());
			Cart cart = new Cart(this.sessionName, cartSquare, getDirectionNegation(back), playerId, prevRail);
			carts.add(cart);
			NotifyAddedCart(cartSquare, cart.getId());
		}
	}

	/**
	 * bewegt alle Wagons, die an einer Lok hï¿½ngen, sobald sich eine Lok um ein Feld
	 * weiter bewegt hat
	 * 
	 * @param forward
	 */
	public void moveCarts(Rail forward, Compass actDirectionOfLoco) {
		Square nextSquare = this.map.getSquare(forward.getXPos(), forward.getYPos());
		Square actSquare = null;
		Compass nextDirection = actDirectionOfLoco;
		Compass actDirection = null;

		for (Cart cart : carts) {
			actSquare = this.map.getSquareById(cart.getSquareId());
			actDirection = cart.getDrivingDirection();
			cart.updateSquare(nextSquare);
			cart.setRail((Rail) nextSquare.getPlaceableOnSquare());
			cart.setDrivingDirection(nextDirection);
			cart.notifyUpdatedCart();
			nextDirection = actDirection;
			nextSquare = actSquare;

		}
	}

	/**
	 * gibt das Rail zurï¿½ck, dass in angegebener Richtung an das Feld, das
	 * mitgegeben wird, angekoppelt ist
	 * 
	 * @param compass
	 *            Himmelsrichtung in die man nach dem Feld schauen soll
	 * @return die Rail, die in angegebener Richtung steht
	 */
	public Rail getNextRail(Compass compass, Square square) {
		Square retSquare;
		switch (compass) {
		case NORTH:
			retSquare = this.map.getSquare(square.getXIndex(), square.getYIndex() - 1);
			if(retSquare.getPlaceableOnSquare() instanceof Rail)
				return (Rail) retSquare.getPlaceableOnSquare();
			break;
		case EAST:
			retSquare = this.map.getSquare(square.getXIndex() + 1, square.getYIndex());
			if(retSquare.getPlaceableOnSquare() instanceof Rail)
				return (Rail) retSquare.getPlaceableOnSquare();
			break;
		case SOUTH:
			retSquare = this.map.getSquare(square.getXIndex(), square.getYIndex() + 1);
			if(retSquare.getPlaceableOnSquare() instanceof Rail)
				return (Rail) retSquare.getPlaceableOnSquare();
			break;
		case WEST:
			retSquare = this.map.getSquare(square.getXIndex() - 1, square.getYIndex());
			if(retSquare.getPlaceableOnSquare() instanceof Rail)
				return (Rail) retSquare.getPlaceableOnSquare();
			break;
		}
		return null;
	}

	/**
	 * ï¿½berladene Methode mit mitgegebenen Compass
	 * 
	 * @param compass
	 *            der negiert werden soll
	 * @return negierter Compass
	 */
	public Compass getDirectionNegation(Compass compass) {
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

	/**
	 * notifiziert wenn eine Lok erstellt wurde
	 */
	private void NotifyLocoCreated() {
		MessageInformation messageInfo = new MessageInformation("CreateLoco");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("drivingDirection", drivingDirection.toString());
		messageInfo.putValue("playerId", this.playerId);
		notifyChange(messageInfo);
	}

	/**
	 * notifiziert wenn die Position der Lok veraendert wurde
	 */
	private void NotifyLocoPositionChanged() {
		MessageInformation messageInfo = new MessageInformation("UpdateLocoPosition");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("drivingDirection", drivingDirection.toString());
		messageInfo.putValue("playerId", this.playerId);
		notifyChange(messageInfo);
	}

	/**
	 * notifiziert, wenn ein Wagon erstellt wurde
	 * 
	 * @param square
	 *            Feld auf dem der Wagon steht
	 * @param cartId
	 *            Id des Wagons
	 */
	private void NotifyAddedCart(Square square, UUID cartId) {
		MessageInformation messageInfo = new MessageInformation("CreateCart");
		messageInfo.putValue("playerId", this.playerId);
		messageInfo.putValue("cartId", cartId);
		messageInfo.putValue("xPos", square.getXIndex());
		messageInfo.putValue("yPos", square.getYIndex());
		messageInfo.putValue("drivingDirection", getCartById(cartId).getDrivingDirection());
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

	public Cart getCartById(UUID cartID) {
		for (Cart c : carts) {
			if (c.getId().equals(cartID))
				return c;
		}
		return null;
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
