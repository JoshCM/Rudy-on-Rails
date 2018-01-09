package models.game;

import models.base.InteractiveGameObject;
import models.base.ModelObserver;
import models.base.InterActiveGameModel;

public abstract class TickableGameObject extends InteractiveGameObject implements ModelObserver {
	
	protected long timeDeltaInNanoSeconds; //Zeit zwischen den Ticks
	
	public TickableGameObject() {

	}
	
	
	abstract public void specificUpdate();

	public void update(InterActiveGameModel o, Object arg) {
		// reagiert auf die Tickänderung und ruft die specificUpdate-Methode auf
		timeDeltaInNanoSeconds = (long)arg;
		this.specificUpdate();
	}

    @Override
    public void update(Object arg) {
        System.out.println("UPDATE METHODE DES TICKABLE OBJECTS - ERROR!?!?!?!");
    }
}
