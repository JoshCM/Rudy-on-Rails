package models.game;

import models.base.ModelBase;
import models.session.RoRSession;

import java.util.UUID;

public class Player extends ModelBase {
    private String name;

    public Player(String sessionName, String name, UUID id) {
    	super(sessionName);
        this.name = name;
        this.setId(id);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
