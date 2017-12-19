package models.game;

import models.base.ModelBase;

public class Player extends ModelBase {
    private String name;

    public Player(String sessionName, String name) {
    	super(sessionName);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
