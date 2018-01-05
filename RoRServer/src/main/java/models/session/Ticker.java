package models.session;

import java.util.Observable;

import models.base.ObservableModel;

public class Ticker extends ObservableModel{
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
