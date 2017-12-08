package models.game;

import models.base.ModelBase;
import models.session.RoRSession;

/**
 * Abstrakte Klasse fuer alle Objekte, mit denen interagiert werden kann
 *
 */
public abstract class InteractiveGameObject extends ModelBase {
	protected final String className;
	transient protected Square square;
    
	public InteractiveGameObject(Square square) {
		super(square.getRoRSession());
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
