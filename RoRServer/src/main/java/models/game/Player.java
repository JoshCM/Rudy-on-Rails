package models.game;

import communication.MessageInformation;
import models.base.ModelBase;

public class Player extends ModelBase {
    private String name;
    private boolean isHost;

    public Player(String sessionName, String name) {
    	super(sessionName);
        this.name = name;
        
        notifyCreated();
    }
    
    public Player(String sessionName, String name, boolean isHost) {
    	this(sessionName, name);
    	this.isHost = isHost;
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
