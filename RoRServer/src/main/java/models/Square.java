package models;

public class Square {

	int id;
	private PlaceableOnSquare placeableOnSquare;
	
	public Square(int id) {
		this.id = id;
		this.setPlaceable(placeableOnSquare);
	}
	
	public void setPlaceable(PlaceableOnSquare placeable) {
		this.placeableOnSquare = placeable;
	}
	
	public int getSquareId() {
		return id;
	}
	
	public void setSquareId(int id) {
		this.id = id;
	}
	

}
