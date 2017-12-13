package models.game;

import java.util.List;

import communication.MessageInformation;

public class Trainstation extends InteractiveGameObject implements PlaceableOnSquare {

	private List<Rail> trainstationRails;
	public Trainstation(String sessionName, Square square, List<Rail> trainstationRails) {
		super(sessionName, square);
		this.trainstationRails = trainstationRails;
		
		notifyCreatedTrainstation();
	}
	
	private void notifyCreatedTrainstation() {
		MessageInformation messageInfo = new MessageInformation("CreateTrainstation");
		messageInfo.putValue("trainstationId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());

		notifyChange(messageInfo);
	}

	public List<Rail> getTrainstationRails() {
		return trainstationRails;
	}
}
