package models.game;

import java.util.List;

import communication.MessageInformation;
import models.session.RoRSession;

public class Mine extends InteractiveGameObject implements PlaceableOnRail {
	
	List<Resource> resources;

	public Mine(String sessionName, Square square) {
		super(sessionName, square);
		notifyCreatedMine();
	}

	@Override
	public PlaceableOnSquare loadFromMap(Square square, RoRSession session) {
		return null;
	}
	
	private void notifyCreatedMine() {
		MessageInformation message = new MessageInformation("CreateMine");
		message.putValue("xPos", getXPos());
		message.putValue("yPos", getYPos());
		message.putValue("mineId", getId());
		message.putValue("squareId", getSquareId());
		notifyChange(message);
		
	}

}