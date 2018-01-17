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
	public Crane(String sessionName, Square square, UUID trainstationId, UUID craneId, Compass alignment, UUID railId) {
		super(sessionName, square, craneId);
		this.railId = railId;
		this.trainstationId = trainstationId;
		this.alignment = alignment;
		NotifyCraneCreated();
	}
	

	@Override
	public PlaceableOnRail loadFromMap(Square square, RoRSession session) {
		Rail rail = (Rail) square.getPlaceableOnSquare();
		Crane crane = (Crane) rail.getPlaceableOnrail();
		Crane newCrane = new Crane(session.getName(), square, crane.getTrainstationId(), crane.getId(), crane.getAlignment(), crane.getRailId());
		
		// der sessionName muss neu gesetzt werden, damit der Observer Änderungen dieses Objekts mitbekommen kann
		newCrane.setName(session.getName());

		//log.info("Stock erstellt: " + newStock.toString());
		
		// die Trainstation die den Stock beinhaltet muss den neuen Stock gesetzt bekommen,#
		// sonst hat der Stock der Trainstation keine Observer
		((Trainstation)session.getMap().getPlaceableOnSquareById(getTrainstationId())).setCrane(newCrane);
		
		return newCrane;
	}
	
	
	private UUID getRailId() {
		return railId;
	}
	private Compass getAlignment() {
		return alignment;		
	}
	private UUID getTrainstationId() {
		return trainstationId;
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
	
	public void NotifyCraneMoved(Square newSquare) {
		MessageInformation messageInfo = new MessageInformation("MoveCrane");
		messageInfo.putValue("newXPos", newSquare.getXIndex());
		messageInfo.putValue("newYPos", newSquare.getYIndex());
		notifyChange(messageInfo);
	}
	
	public void changeSquare(Square newSquare) {
		setSquareId(newSquare.getId());
		setXPos(newSquare.getXIndex());
		setYPos(newSquare.getYIndex());
	}



	@Override
	public void specificUpdate() {
		// TODO Auto-generated method stub
		
	}


}
