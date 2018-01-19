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
    }
    
    public String getDescription() {
        return name;
    }

    public void setSessionName(String name) {
        this.name = name;
    }
    
    public boolean getIsHost() {
    	return isHost;
    }
}
