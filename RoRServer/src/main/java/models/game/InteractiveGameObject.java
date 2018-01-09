package models.game;

import models.base.ModelBase;
import models.base.PlaceableModel;

/**
 * Abstrakte Klasse fuer alle Objekte, mit denen interagiert werden kann.
 * TODO: Sollten Observable sein.
 * TODO: Sollten evtl. auch Observer sein!?
 */
public abstract class InteractiveGameObject {
	protected final String className;
    
	public InteractiveGameObject() {
		super();
		this.className = getClass().getName();
	}
	
	public String getClassName() {
		return className;
	}
}
