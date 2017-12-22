package models.game;

import communication.MessageInformation;

public abstract class Resource extends InteractiveGameObject implements PlaceableOnSquare {
	
	protected int quantity;
	protected String name;

	protected Resource(String sessionName, Square square, String name) {
		super(sessionName, square);
		this.name = name;
		notifyCreatedResource();	
	}
	
	private void notifyCreatedResource() {
		MessageInformation message = new MessageInformation("CreateResource");
		message.putValue("resourceId", getId());
		message.putValue("quantity", getQuantity());
		message.putValue("resource", getName());
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
