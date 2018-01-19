package models.game;

import exceptions.InvalidModelOperationException;
import models.session.RoRSession;

import java.util.UUID;

import communication.MessageInformation;

/**
 * 
 * @author Isabel Rott, Michelle Le
 * Klasse fuer ein Cart (Waggon), das an einer Loco haengt
 * Auf einem Cart koennen Container geladen werden
 */
public class Cart extends InteractiveGameObject implements PlaceableOnRail {
	private Resource resource;
	private Compass drivingDirection;
	private UUID playerId;
	private Rail rail;
	private UUID currentLocoId;
	
	/**
	 * Konstruktor eines Carts
	 * @param square auf dem der Wagon steht wird mitgegeben
	 */
	public Cart(String sessionName, Square square, Compass drivingDirection, UUID playerId, boolean addToLoco, UUID currentLocoId) {
		super(sessionName, square);
		this.setDrivingDirection(drivingDirection);
		this.playerId = playerId;
		Rail rail = (Rail)square.getPlaceableOnSquare();
		this.setRail(rail);
		this.currentLocoId = currentLocoId;
		notifyAddedCart(addToLoco);
	}
	
	/**
	 * notifiziert, wenn ein Wagon erstellt wurde
	 * 
	 * @param square
	 *            Feld auf dem der Wagon steht
	 * @param cartId
	 *            Id des Wagons
	 */
	private void notifyAddedCart(boolean addToLoco) {
		MessageInformation messageInfo = new MessageInformation("CreateCart");
		messageInfo.putValue("playerId", this.playerId);
		messageInfo.putValue("cartId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("addToLoco", addToLoco);
		messageInfo.putValue("currentLocoId", currentLocoId);
		messageInfo.putValue("drivingDirection", drivingDirection);
		notifyChange(messageInfo);
	}
	
	/**
	 * Cart mit einer Ressource beladen
	 * @param resource zu beladene Resource
	 */
	public void loadResourceOntoCart(Resource resource) {
		if (resource != null) {
			this.resource = resource;
		} else {
			throw new InvalidModelOperationException("Cart bereits beladen");
		}
	}
	
	/**
	 * Cart wird geleert
	 */
	public void removeResourceFromCart() {
		resource = null;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	/**
	 * Entfernt die Ressource auf dem Cart und gibt diese zurück
	 * @return Ressource auf auf dem Cart
	 */
	public Resource unloadResourceFromCart() {
		Resource unloadedResource = resource;
		resource = null;
		return unloadedResource;
	}

	@Override
	public void specificUpdate() {
		// TODO Auto-generated method stub
	}
	
	public PlaceableOnRail loadFromMap(Square square, RoRSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	public Compass getDrivingDirection() {
		return drivingDirection;
	}

	public void setDrivingDirection(Compass compass) {
		this.drivingDirection = compass;
	}
	
	/**
	 * notifiziert wenn die Position des Wagons ge�ndert wurde
	 */
	public void notifyUpdatedCart() {
		MessageInformation messageInfo = new MessageInformation("UpdateCartPosition");
		messageInfo.putValue("cartId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("playerId", this.playerId);
		messageInfo.putValue("currentLocoId", currentLocoId);
		messageInfo.putValue("drivingDirection", drivingDirection.toString());
		notifyChange(messageInfo);
	}



	public Rail getRail() {
		return rail;
	}

	public void setRail(Rail rail) {
		this.rail = rail;
	}
	
	public UUID getCurrentLocoId() {
		return currentLocoId;
	}
}
