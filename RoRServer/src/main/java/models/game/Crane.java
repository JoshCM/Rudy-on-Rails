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
		Crane newCrane = new Crane(session.getSessionName(), square, crane.getTrainstationId(), crane.getId(), crane.getAlignment(), crane.getRailId());
		
		// der sessionName muss neu gesetzt werden, damit der Observer Änderungen dieses Objekts mitbekommen kann
		newCrane.setSessionName(session.getSessionName());

		//log.info("Stock erstellt: " + newStock.toString());
		
		// die Trainstation die den Stock beinhaltet muss den neuen Crane gesetzt bekommen,#
		// sonst hat der Crane der Trainstation keine Observer
		((Trainstation)session.getMap().getPlaceableOnSquareById(getTrainstationId())).setCrane(newCrane);
		
		return newCrane;
	}
	
	public void moveCrane(Square newSquare) {
		changeSquare(newSquare);
		NotifyCraneMoved();
	}
	
	public void rotateCrane(Square newSquare, Compass trainstationAlignment) {
		moveCrane(newSquare);
		this.alignment = getCraneAlignmentbyTrainstationAlignment(alignment);
		NotifyCraneRotated();
	}
	
	public static Compass getCraneAlignmentbyTrainstationAlignment(Compass trainstationAlignment) {
		switch(trainstationAlignment) {
		case EAST:
			return Compass.NORTH;
		case NORTH:
			return Compass.WEST;
		case SOUTH:
			return Compass.EAST;
		case WEST:
			return Compass.SOUTH;
		default:
			return Compass.EAST;
		}
	}
	
	
	public UUID getRailId() {
		return railId;
	}
	public Compass getAlignment() {
		return alignment;		
	}
	public UUID getTrainstationId() {
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
	
	private void NotifyCraneMoved() {
		MessageInformation messageInfo = new MessageInformation("MoveCrane");
		messageInfo.putValue("newXPos", getXPos());
		messageInfo.putValue("newYPos", getYPos());
		notifyChange(messageInfo);
	}
	
	private void NotifyCraneRotated() {
		MessageInformation messageInfo = new MessageInformation("UpdateAlignmentOfCrane");
		messageInfo.putValue("XPos", getXPos());
		messageInfo.putValue("YPos", getYPos());
		messageInfo.putValue("newAlignment", this.alignment.toString());
		notifyChange(messageInfo);
	}
	
	public void changeSquare(Square newSquare) {
		setSquareId(newSquare.getId());
		setXPos(newSquare.getXIndex());
		setYPos(newSquare.getYIndex());
	}





}
