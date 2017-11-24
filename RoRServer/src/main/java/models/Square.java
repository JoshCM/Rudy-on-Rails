package models;

public class Square {
	private PlaceableOnSquare placeableOnSquare;
	int id;
	
	public Square(int id) {
		this.id = id;
	}
	
	public void setPlaceable(PlaceableOnSquare placeable) {
		this.placeableOnSquare = placeable;
	}
	

}
