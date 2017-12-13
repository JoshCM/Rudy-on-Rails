package models.session;

import java.util.Observable;

public class Ticker extends Observable{
	/**
	 * Benachrichtigt alle angemeldete TickableGameObjects, 
	 * wenn sich das timeDelta ge�ndert hat
	 * @param timeDelta
	 */
	public void tick(long timeDelta){
		setChanged();
		notifyObservers(timeDelta);
	}
}
