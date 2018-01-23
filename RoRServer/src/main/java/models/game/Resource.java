package models.game;

import communication.MessageInformation;
import models.session.GameSession;

/**
 * @author Andreas Pöhler, Juliane Lies, Isabell Rott
 * Oberklasse für Ressourcen (Aktuell: Kohle und Gold)
 */
public abstract class Resource extends InteractiveGameObject implements PlaceableOnSquare {
	
	protected int quantity;
	protected String name;
	private GameSession game;
	protected Resource(String sessionName, Square square, String name) {
		super(sessionName, square);
		this.name = name;
		notifyCreatedResource();	
	}
	
	/**
	 * Erstellen einer neuen Message an die Clients
	 */
	private void notifyCreatedResource() {
		MessageInformation message = new MessageInformation("CreateResource");
		message.putValue("resourceId", getId());
		message.putValue("quantity", getQuantity());
		message.putValue("resource", getDescription());
		message.putValue("squareId", getSquareId());
		message.putValue("xPos", getXPos());
		message.putValue("yPos", getYPos());
		notifyChange(message);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return name;
	}

	public void setSessionName(String name) {
		this.name = name;
	}
}
