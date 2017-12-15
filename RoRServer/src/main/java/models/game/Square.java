package models.game;

import communication.MessageInformation;
import models.base.ModelBase;

/**
 * Klasse fuer ein Feld (Square), dass auf der Map liegt und eine Position hat.
 * Auf dem Feld platzierte Objekte koennen auf die jeweilige Position zugreifen.
 */
public class Square extends ModelBase {
	private PlaceableOnSquare placeableOnSquare = null;
	private int xIndex;
	private int yIndex;
	private Map map;
	
	public Square(String sessionName, Map map, int xIndex, int yIndex) {
		super(sessionName);
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.map = map;
	}
	
	public void deletePlaceable() {
		this.placeableOnSquare = null;
		notifyDeletePlaceable();
	}
	
	private void notifyDeletePlaceable() {
		MessageInformation messageInfo = new MessageInformation("DeletePlaceable");
		// TODO: Später haben wir die richtigen SquareIds im Client, im Moment noch nicht!!!
		messageInfo.putValue("xPos", this.getXIndex());
		messageInfo.putValue("yPos", this.getYIndex());
		
		notifyChange(messageInfo);
	}
	
	public void setPlaceable(PlaceableOnSquare placeable) {
		this.placeableOnSquare = placeable;
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

	public Map getMap(){
		return this.map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((placeableOnSquare == null) ? 0 : placeableOnSquare.hashCode());
		result = prime * result + xIndex;
		result = prime * result + yIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (placeableOnSquare == null) {
			if (other.placeableOnSquare != null)
				return false;
		} else if (!placeableOnSquare.equals(other.placeableOnSquare))
			return false;
		if (xIndex != other.xIndex)
			return false;
		if (yIndex != other.yIndex)
			return false;
		return true;
	}
}
