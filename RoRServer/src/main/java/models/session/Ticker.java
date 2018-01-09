package models.session;

import models.base.InterActiveGameModel;

public class Ticker extends InterActiveGameModel {
	/**
	 * Benachrichtigt alle angemeldete TickableGameObjects, 
	 * wenn sich das timeDelta geändert hat
	 * @param timeDelta
	 */
	public void tick(long timeDelta){
		setChanged();
		notifyObservers(timeDelta);
	}
}
