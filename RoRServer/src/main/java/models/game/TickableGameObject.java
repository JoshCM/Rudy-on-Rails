package models.game;

import java.util.Observable;
import java.util.Observer;

public abstract class TickableGameObject extends InteractiveGameObject implements Observer{
	
	private long timeDeltaInNanoSeconds;
	
	public TickableGameObject(String sessionName, Square square) {
		super(sessionName, square);
	}
	
	
	abstract public void specificUpdate();
	
	
	@Override
	public void update(Observable o, Object arg) {
		// reagiert auf die Tick Änderung und ruft die specificUpdate-Methode auf
		timeDeltaInNanoSeconds = (long)arg;
		this.specificUpdate();
	}
}
