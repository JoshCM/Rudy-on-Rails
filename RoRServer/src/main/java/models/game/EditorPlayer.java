package models.game;

import java.util.UUID;

import communication.MessageInformation;

public class EditorPlayer extends Player{

	public EditorPlayer(String sessionName, String name, UUID id, boolean isHost) {
		super(sessionName, name, id, isHost);
		notifyCreated();
	}

	private void notifyCreated() {
    	MessageInformation messageInfo = new MessageInformation("CreatePlayer");
    	messageInfo.putValue("playerId", getId());
    	messageInfo.putValue("playerName", getName());
    	messageInfo.putValue("isHost", getIsHost());
    	notifyChange(messageInfo);
    }
}
