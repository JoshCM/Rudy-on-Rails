package models.game;

import models.ModelBase;

/**
 * Abstrakte Klasse fuer alle Objekte, mit denen interagiert werden kann
 *
 */
public abstract class InteractiveGameObject extends ModelBase {
	
	protected final String className;
	transient protected Square square;
    
	public InteractiveGameObject(Square square) {
		this.className = getClass().getName();
		this.square = square;
	}
	
	public String getClassName() {
		return className;
	}
	
	public Square getSquare() {
		return square;
	}
}
