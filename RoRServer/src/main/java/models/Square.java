package models;

public class Square {

	private PlaceableOnSquare placeableOnSquare = null;
	int id;
	
	public Square(int id) {
		this.id = id;
		this.setPlaceable(placeableOnSquare);
	}
	
	public void setPlaceable(PlaceableOnSquare placeable) {
		if (placeableOnSquare == null ) {
			this.placeableOnSquare = placeable;
		}
		
	}
	
	public int getSquareId() {
		return id;
	}
	
	public void setSquareId(int id) {
		this.id = id;
	}
	

}
