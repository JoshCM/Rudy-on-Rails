package models;

public class Square {
	private PlaceableOnSquare placeableOnSquare = null;
	int id;
	
	public Square(int id) {
		this.id = id;
	}
	
	public void setPlaceable(PlaceableOnSquare placeable) {
		if (placeableOnSquare == null ) {
			this.placeableOnSquare = placeable;
		}
		
	}
	

}
