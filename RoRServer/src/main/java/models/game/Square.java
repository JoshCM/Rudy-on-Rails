package models.game;

public class Square {

	private PlaceableOnSquare placeableOnSquare = null;
	private int xIndex;
	private int yIndex;
	
	public Square(int xIndex, int yIndex) {
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}
	
	public void setPlaceable(PlaceableOnSquare placeable) {
		if (placeableOnSquare == null ) {
			this.placeableOnSquare = placeable;
		}
		
	}
}
