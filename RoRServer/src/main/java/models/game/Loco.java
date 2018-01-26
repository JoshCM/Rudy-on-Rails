package models.game;

import java.util.ArrayList;
import java.util.UUID;

import communication.MessageInformation;
import models.session.GameSessionManager;

/**
 * 
 * @author Isabel Rott, Michelle Le Klasse fuer eine Lok, zu der eine Liste von
 *         Carts gehoert
 */
public class Loco extends TickableGameObject {
	private ArrayList<Cart> carts;
	private Rail rail;
	private UUID playerId;
	private long timeDeltaCounter = 0; // Summe der Zeit zwischen den Ticks
	private long speed;
	private Compass drivingDirection;
	private boolean reversed = false;
	protected Map map;

	/**
	 * Konstruktor einer Lok
	 * 
	 * @param square
	 *            auf dem die Lok steht wird mitgegeben
	 */
	public Loco(String sessionName, Square square, UUID playerId, Compass drivingDirection) {
		super(sessionName, square);
		this.setCarts(new ArrayList<Cart>());
		this.rail = (Rail) square.getPlaceableOnSquare();
		this.map = GameSessionManager.getInstance().getGameSessionByName(sessionName).getMap();
		this.drivingDirection = drivingDirection;
		this.speed = 0;
		this.playerId = playerId;
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
					// Wenn Der Zug auf eine Weiche trifft beim  rückwärtsfahren, dann bleibt er
					// stehen

					if (!reversed) {// Wenn das erstemal nach dem Vorw�rts fahren wieder r�ckw�rts gefahren
									// wird muss die Driving direction ge�ndert werden
						if (!isTrainOnSwitch()) {
							reversed = true;
							reversedDrive(true);
						} else {
							setSpeedAndNotifySpeedChanged(0);
						}
					} else {
						reversedDrive(false);
					}

				} else if (speed > 0) {
					if (reversed) {
						//Wenn das erstemal nach dem R�ckw�rts fahren wieder vorf�rts gefahren wird muss die Driving direction ge�ndert werden 
						this.drivingDirection = this.rail.getExitDirection(this.drivingDirection);
						for (int i = carts.size() - 1; i >= 0; i--) {
							Cart c = carts.get(i);
							c.setDrivingDirection(c.getRail().getExitDirection(c.getDrivingDirection()));
						}
						reversed = false;
					}
					drive(false);
				}
			}
		}
	}

	public void drive(boolean reversed){
	    if(!reversed){
            Rail nextRail = getNextRail(this.drivingDirection, this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
            //Wenn keine nächste Rail existiert halte den Zug an
            //Andernfalls, wenn es eine Rail gibt sollte die auch einen Eingang haben zum rein fahren andernfalls stoppe
            if(nextRail != null &&nextRail instanceof Rail && nextRail.hasExitDirection(getDirectionNegation(this.drivingDirection))){
                //Merke dir die aktuellen Loco Eigenschaften für die Carts
                Rail currentLocoRail = this.rail;
                Compass currentLocoDrivingDirection = this.drivingDirection;
                UUID currentLocoSquareId = this.getSquareId();
                moveLoco(nextRail);
                moveCarts(currentLocoRail, currentLocoDrivingDirection, currentLocoSquareId);
            }
            else{
                setSpeedAndNotifySpeedChanged(0);
            }
        }
        else{

        }
    }

    public void moveLoco(Rail nextRail){
	    //hole dir die DrivingDirection von dem nächste Rail und übergebe das Gegentei von der aktuellen Fahrrichtung
	    this.drivingDirection = nextRail.getExitDirection(getDirectionNegation(getDrivingDirection()));
	    this.rail = nextRail;
	    this.updateSquare(this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
	    notifyLocoPositionChanged();
    }

	private boolean isTrainOnSwitch() {
		if(this.rail instanceof Switch)
			return true;
		if(this.carts.get(carts.size()-1).getRail() instanceof Switch)
			return false;
		for(Cart c : this.getCarts()) {
			Cart cart = c;
			if(c.getRail() instanceof Switch)
				return true;
		}

		return false;
	}

	/**
	 * Ueberfuehrt die Lok in das naechste moegliche Feld in Fahrtrichtung
	 */
	/*public void drive() {
		Rail nextRail = getNextRail(this.drivingDirection,
				this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));

		//System.out.println(nextRail.hasExitDirection(getDirectionNegation(this.drivingDirection)));
		if(nextRail != null && nextRail.hasExitDirection(getDirectionNegation(this.drivingDirection))) {
			moveCarts(this.rail, this.drivingDirection);
			this.drivingDirection = nextRail.getExitDirection(getDirectionNegation(this.drivingDirection));
			this.rail = nextRail;
			this.updateSquare(this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
			notifyLocoPositionChanged();
		}
		else {
			setSpeedAndNotifySpeedChanged(0);
		}
	}*/
	
	/**
	 * Zug f�hrt r�ckwerts(letzter Wagon f�hrt)
	 * @param initial: Sagt ob der Zug grade von vorw�rts in R�ckw�rts 
	 */
	public void reversedDrive(boolean initial) {

		Cart actCart = null;
		Compass tempDirection;

		for (int i = carts.size() - 1; i >= 0; i--) {
			actCart = carts.get(i);
			// Wenn von Vorwärts in Rückwärts geändert wird muss die
			// Drivingdirection erstmal umgedreht werden.
			if (initial) {
				tempDirection = actCart.getRail().getExitDirection(actCart.getDrivingDirection());
			} else {
				tempDirection = actCart.getDrivingDirection();
			}

			Rail nextRail = getNextRail(tempDirection, this.map.getSquare(actCart.getXPos(), actCart.getYPos()));

			if (nextRail != null) {
				if (nextRail.getPlaceableOnrail() instanceof Cart) {// Wenn eine Cart gefunden wird, also zum andocken
					Cart cart = (Cart) nextRail.getPlaceableOnrail();
					cart.setDrivingDirection(actCart.getDrivingDirection());
					carts.add(cart);
					cart.setCurrentLocoId(this.getId());
					nextRail.setPlaceableOnRail(null);
					setSpeedAndNotifySpeedChanged(0);
					if (initial) {// Wenn noch nie Vorw�rtsgefahren wurde und direkt beim start r�ckw�rts
									// gefahren wird muss die Driving direction ge�ndert werden
						this.drivingDirection = this.rail.getExitDirection(
								getDirectionNegation(this.rail.getExitDirection(this.drivingDirection)));
					}
					notifyCartToLocoAdded(cart);
					break;
				}
				if (nextRail instanceof Rail) { //Zug soll nur fahren, wenn das nextRail auch wirklich ein Rail ist
					Compass newDrivingDirection = nextRail.getExitDirection(getDirectionNegation(tempDirection));
					if (nextRail.hasExitDirection(getDirectionNegation(tempDirection))) {
						actCart.setDrivingDirection(newDrivingDirection);
						actCart.setRail(nextRail);
						actCart.updateSquare(this.map.getSquare(nextRail.getXPos(), nextRail.getYPos()));
						actCart.notifyUpdatedCart();
					} else {
						setSpeedAndNotifySpeedChanged(0);
						break;
					}
				} else {
					setSpeedAndNotifySpeedChanged(0);
					break;
				}
			} else {
				setSpeedAndNotifySpeedChanged(0);
				break;
			}
		}
		if (this.speed != 0) {// Wenn das n�chste schienenst�ck der Cart leer ist darf der Zug nat�rlich
								// auch nicht weiter d�sen
			if (initial)
				tempDirection = this.rail.getExitDirection(this.drivingDirection);
			else
				tempDirection = this.drivingDirection;

			this.rail = getNextRail(tempDirection, this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
			this.drivingDirection = this.rail.getExitDirection(getDirectionNegation(tempDirection));
			this.updateSquare(this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
			notifyLocoPositionChanged();
		}
	}
	
	private void setSpeedAndNotifySpeedChanged(int speed) {
		this.speed = speed;
		notifySpeedChanged();
		
	}

	public void addCart() {
		if (carts.isEmpty()) {
			addInitialCart();
		} else {
			Cart lastCart = this.carts.get(carts.size()-1);
			Compass back = this.rail.getExitDirection(lastCart.getDrivingDirection());
			Rail prevRail = getNextRail(back, this.map.getSquare(lastCart.getXPos(), lastCart.getYPos()));
			Square cartSquare = this.map.getSquare(prevRail.getXPos(), prevRail.getYPos());
			Cart cart = new Cart(this.sessionName, cartSquare, getDirectionNegation(back), playerId, true, this.getId());
			carts.add(cart);
		}
	}

	/**
	 * f�gt der Lok initial ein Cart hinzu auf das vorige Feld
	 */
	private void addInitialCart() {
		if (carts.isEmpty()) {
			Compass back = this.rail.getExitDirection(this.drivingDirection);
			Rail prevRail = getNextRail(back, this.map.getSquare(this.rail.getXPos(), this.rail.getYPos()));
			Square cartSquare = this.map.getSquare(prevRail.getXPos(), prevRail.getYPos());
			Cart cart = new Cart(this.sessionName, cartSquare, getDirectionNegation(back), playerId, true, this.getId());
			carts.add(cart);
		}
	}

	/**
	 * bewegt alle Wagons, die an einer Lok h�ngen, sobald sich eine Lok um ein Feld
	 * weiter bewegt hat
	 * 
	 * @param nextRail
	 */
	public void moveCarts(Rail nextRail, Compass nextDrivingDirection, UUID nextSquareId) {
		for (Cart cart : carts) {
		    //Speichere die aktuellen Cart Eigenschaft für das nächste Cart
            Compass actDrivingDirection = cart.getDrivingDirection();
            Rail actRail = cart.getRail();
            UUID actSquareId = cart.getSquareId();
            //Setze die neuen Cart Eigenschaften
            cart.setSquareId(nextSquareId);
            cart.setRail(nextRail);
            cart.setDrivingDirection(nextDrivingDirection);
            //Setze die neuen für Cart Eigenschaften für den nächsten Cart
            nextRail = actRail;
            nextDrivingDirection = actDrivingDirection;
            nextSquareId = actSquareId;
            //Update die Cart Eigenschaften
            cart.notifyUpdatedCart();
		}
	}
	
	/**
	 * gibt das Rail zur�ck, dass in angegebener Richtung an das Feld, das
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
	 * �berladene Methode mit mitgegebenen Compass
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
	 * notifiziert wenn die Position der Lok veraendert wurde
	 */
	private void notifyLocoPositionChanged() {
		MessageInformation messageInfo = new MessageInformation("UpdateLocoPosition");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("drivingDirection", drivingDirection.toString());
		notifyChange(messageInfo);
	}
	private void notifyCartToLocoAdded(Cart cart) {
		MessageInformation messageInfo = new MessageInformation("UpdateCartToLoco");
		messageInfo.putValue("xPos", cart.getXPos());
		messageInfo.putValue("yPos", cart.getYPos());
		messageInfo.putValue("playerId", this.playerId);
		messageInfo.putValue("currentLocoId", getId());
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
	
	public Compass getDrivingDirection() {
		return drivingDirection;
	}
}
