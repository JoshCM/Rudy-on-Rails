package models.game;

import java.util.UUID;

import communication.MessageInformation;

public class PlayerLoco extends Loco {
	public PlayerLoco(String sessionName, Square square, UUID playerId, Compass drivingDirection) {
		super(sessionName, square, playerId, drivingDirection);
		
		NotifyLocoCreated();
		this.addInitialCart();
	}
	
	/**
	 * notifiziert wenn eine Lok erstellt wurde
	 */
	private void NotifyLocoCreated() {
		MessageInformation messageInfo = new MessageInformation("CreatePlayerLoco");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("drivingDirection", getDrivingDirection().toString());
		messageInfo.putValue("playerId", getPlayerId());
		notifyChange(messageInfo);
	}
}
