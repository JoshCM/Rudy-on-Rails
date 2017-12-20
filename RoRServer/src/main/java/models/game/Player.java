package models.game;

import communication.MessageInformation;
import models.base.ModelBase;

import java.util.UUID;

public class Player extends ModelBase {
    private String name;
    private boolean isHost;

    public Player(String sessionName, String name, UUID id, boolean isHost) {
    	super(sessionName);
        this.name = name;
        this.setId(id);
        this.isHost = isHost;
        
        notifyCreated();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean getIsHost() {
    	return isHost;
    }
    
    private void notifyCreated() {
    	MessageInformation messageInfo = new MessageInformation("CreatePlayer");
    	messageInfo.putValue("playerId", getId());
    	messageInfo.putValue("playerName", name);
    	messageInfo.putValue("isHost", isHost);
    	notifyChange(messageInfo);
    }
}
