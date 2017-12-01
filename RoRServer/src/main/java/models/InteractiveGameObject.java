package models;

/**
 * Abstrakte Klasse für alle Objekte, mit denen interagiert werden kann
 *
 */
public abstract class InteractiveGameObject {
	transient protected Square square;
	
	public InteractiveGameObject(Square square) {
		this.square = square;
		
	}
	
	

}
