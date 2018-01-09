package models.base;

/**
 * Abstrakte Klasse fuer alle Objekte, mit denen interagiert werden kann.
 * Sind Observer und Observable
 */
public abstract class InteractiveGameObject extends InterActiveGameModel {
	protected final String className;
    
	public InteractiveGameObject() {
		super();
		this.className = getClass().getName();
	}
	
	public String getClassName() {
		return className;
	}
}
