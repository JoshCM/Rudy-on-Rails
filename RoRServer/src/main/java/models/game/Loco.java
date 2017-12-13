package models.game;

import java.util.ArrayList;

import communication.MessageInformation;

/**
 * 
 * @author Isabel Rott, Michelle Le
 * Klasse für eine Lok, zu der eine Reihe von Wagons gehoert
 */
public class Loco extends InteractiveGameObject implements PlaceableOnRail  {

	private ArrayList<Cart> carts;
	private Rail rail;
	
	/**
	 * Konstruktor
	 * @param square auf dem die Lok steht wird mitgegeben
	 */

	public Loco(String sessionName, Square square) {
		super(sessionName,square);
		this.setCarts(new ArrayList<Cart>());
		this.rail = (Rail)square.getPlaceableOnSquare();
		SendCreatedLocoMessage();
	}

	public ArrayList<Cart> getCarts() {
		return carts;
	}

	public void setCarts(ArrayList<Cart> carts) {
		this.carts = carts;
	}

	private void SendCreatedLocoMessage() {
		MessageInformation messageInfo = new MessageInformation("CreateLoco");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("squareId", getSquareId());
		messageInfo.putValue("railId", rail.getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		
		addMessage(messageInfo);
	}
}
