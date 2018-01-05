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
	public PlaceableOnRail loadFromMap(Square square, RoRSession session) {
		
		// Neue Mine erstellen und damit an den Client schicken
		Mine newMine = new Mine(session.getName(), square);
		
		//sessionName neu setzen, damit Observer Änderung dieses Objekts mitbekommen kann
		newMine.setName(session.getName());
		
		return newMine;
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