package models.game;

import java.util.Observable;
import java.util.Observer;

import models.base.ModelObserver;
import models.base.ObservableModel;

public abstract class TickableGameObject extends InteractiveGameObject implements ModelObserver{
	
	private long timeDeltaInNanoSeconds;
	
	public TickableGameObject(String sessionName, Square square) {
		super(sessionName, square);
	}
	
	
	abstract public void specificUpdate();
	
	
	@Override
	public void update(ObservableModel o, Object arg) {
		// reagiert auf die Tick Änderung und ruft die specificUpdate-Methode auf
		timeDeltaInNanoSeconds = (long)arg;
		this.specificUpdate();
	}
}
