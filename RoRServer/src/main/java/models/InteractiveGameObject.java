package models;

public abstract class InteractiveGameObject {
	transient protected Square square;
	
	public InteractiveGameObject(Square square) {
		this.square = square;
		
	}
	
	

}
