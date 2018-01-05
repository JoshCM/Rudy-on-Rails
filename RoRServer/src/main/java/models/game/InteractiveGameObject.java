package models.game;

import models.base.ModelBase;

/**
 * Abstrakte Klasse fuer alle Objekte, mit denen interagiert werden kann.
 * TODO: Sollten Observable sein.
 * TODO: Sollten evtl. auch Observer sein!?
 */
public abstract class InteractiveGameObject extends ModelBase {
	protected final String className;
    
	public InteractiveGameObject() {
		this.className = getClass().getName();
	}
	
	public String getClassName() {
		return className;
	}
}
