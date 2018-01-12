package models.game;

import java.util.UUID;

import communication.MessageInformation;
import models.session.RoRSession;

public class Crane extends InteractiveGameObject implements PlaceableOnRail{
	private UUID railId, trainstationId;
	private Compass alignment;
	

	public Crane(String sessionName, Square square, UUID trainstationId, Compass alignment, UUID railId) {
		super(sessionName, square);
		this.railId = railId;
		this.trainstationId = trainstationId;
		this.alignment = alignment;
		NotifyCraneCreated();
	}



	@Override
	public PlaceableOnRail loadFromMap(Square square, RoRSession session) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private void NotifyCraneCreated() {
	// TODO Auto-generated method stub
		MessageInformation messageInfo = new MessageInformation("CreateCrane");
		messageInfo.putValue("craneId", getId());
		messageInfo.putValue("squareId", getSquareId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("alignment", alignment.toString());
		notifyChange(messageInfo);
	}
}
