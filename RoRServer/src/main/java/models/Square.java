package models;

/**
 * Klasse für ein Feld (Square), dass auf der Map liegt und eine Position hat.
 * Auf dem Feld platzierte Objekte können auf die jeweilige Position zugreifen.
 */
public class Square {

	private PlaceableOnSquare placeableOnSquare = null;
	private int xIndex;
	private int yIndex;
	
	public Square(int xIndex, int yIndex) {
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}
	
	public void setPlaceable(PlaceableOnSquare placeable) {
		//if (placeableOnSquare == null ) {
			this.placeableOnSquare = placeable;
		//}
	}
	
	public PlaceableOnSquare getPlaceableOnSquare(){
		return placeableOnSquare;
	}
	
	public int getXIndex(){
		return xIndex;
	}
	
	public int getYIndex(){
		return yIndex;
	}
}
