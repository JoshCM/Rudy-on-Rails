package models.game;

import java.util.UUID;

import communication.MessageInformation;

/**
 * 
 * @author Isabel Rott, Michelle Le
 * Klasse fuer ein Cart (Waggon), das an einer Loco haengt
 * Auf einem Cart koennen Container geladen werden
 */
public class Cart extends InteractiveGameObject implements PlaceableOnRail {

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
	
	

	public Compass getCompass() {
		return compass;
	}

	public void setCompass(Compass compass) {
		this.compass = compass;
	}
	
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
