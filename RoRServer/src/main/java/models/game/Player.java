package models.game;

import models.base.ModelBase;

public class Player extends ModelBase {
    private String name;
    private boolean isHost;

    public Player(String sessionName, String name) {
    	super(sessionName);
        this.name = name;
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
}
