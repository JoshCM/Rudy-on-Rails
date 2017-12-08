package models.game;

import models.base.ModelBase;
import models.editor.RoRSession;

public class Player extends ModelBase {
    private String name;

    public Player(RoRSession session, String name) {
    	super(session);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
