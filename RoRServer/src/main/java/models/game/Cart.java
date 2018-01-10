package models.game;

import exceptions.InvalidModelOperationException;

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

	private Compass compass;
	private UUID playerId;
	private Rail rail;
	/**
	 * Konstruktor eines Carts
	 * @param square auf dem der Wagon steht wird mitgegeben
	 */
	public Cart(String sessionName, Square square, Compass compass, UUID playerId, Rail rail) {
		super(sessionName,square);
		this.setCompass(compass);
		this.playerId = playerId;
		this.setRail(rail);
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

	public Compass getCompass() {
		return compass;
	}

	public void setCompass(Compass compass) {
		this.compass = compass;
	}
	
	/**
	 * notifiziert wenn die Position des Wagons ge�ndert wurde
	 */
	public void SendUpdateCartMessage() {
		MessageInformation messageInfo = new MessageInformation("UpdateCartPosition");
		messageInfo.putValue("cartId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("playerId", this.playerId);
		notifyChange(messageInfo);
	}



	public Rail getRail() {
		return rail;
	}



	public void setRail(Rail rail) {
		this.rail = rail;
	}
	

}
