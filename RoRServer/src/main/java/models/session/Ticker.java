package models.session;

import java.util.Observable;

public class Ticker extends Observable{
	public void tick(long timeDelta){
		setChanged();
		notifyObservers(timeDelta);
	}
}
