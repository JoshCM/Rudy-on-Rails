package models.game;

import models.ModelBase;

/**
 * Abstrakte Klasse fuer alle Objekte, mit denen interagiert werden kann
 *
 */
public abstract class InteractiveGameObject extends ModelBase {
	protected Square square;
	
	public InteractiveGameObject(Square square) {
		this.square = square;
		
	}
	
	

}
