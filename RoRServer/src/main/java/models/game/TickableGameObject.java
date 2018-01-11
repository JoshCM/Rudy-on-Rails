package models.game;

import java.util.UUID;
import models.base.ModelBase;
import models.base.ModelObserver;
import models.base.ObservableModel;

public abstract class TickableGameObject extends ModelBase implements ModelObserver{
	protected static final long SEC_IN_NANO = 1000000000;
	protected long timeDeltaInNanoSeconds;//Zeit zwischen den Ticks
	
	public TickableGameObject(String sessionName) {
		super(sessionName);
	}
	
	public TickableGameObject(String sessionName, UUID id) {
		super(sessionName, id);
	}
	
	abstract public void specificUpdate();
	
	
	@Override
	public void update(ObservableModel o, Object arg) {
		// reagiert auf die Tickänderung und ruft die specificUpdate-Methode auf
		timeDeltaInNanoSeconds = (long)arg;
		this.specificUpdate();
	}
}
