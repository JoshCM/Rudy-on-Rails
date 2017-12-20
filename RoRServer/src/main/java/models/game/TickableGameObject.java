package models.game;

import java.util.Observable;
import java.util.Observer;

import models.base.ModelObserver;
import models.base.ObservableModel;

public abstract class TickableGameObject extends InteractiveGameObject implements ModelObserver{
	
	protected long timeDeltaInNanoSeconds;//Zeit zwischen den Ticks
	
	public TickableGameObject(String sessionName, Square square) {
		super(sessionName, square);
	}
	
	
	abstract public void specificUpdate();
	
	
	@Override
	public void update(ObservableModel o, Object arg) {
		// reagiert auf die Tick �nderung und ruft die specificUpdate-Methode auf
		timeDeltaInNanoSeconds = (long)arg;
		this.specificUpdate();
	}
}
