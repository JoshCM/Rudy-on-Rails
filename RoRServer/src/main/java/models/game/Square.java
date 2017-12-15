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
	
	public Square(String sessionName, Map map, int xIndex, int yIndex) {
		super(sessionName);
		this.xIndex = xIndex;
		this.yIndex = yIndex;
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
	
	public void movePlaceable(PlaceableOnSquare placeable, Square oldSquare) {
		this.placeableOnSquare = placeable;
		
		// hier soll nur notify aufgerufen werden, wenn das Placeable schon vorhanden war
		// und nicht neu erstellt wurde
		// im moment ist es nur durch überladung getrennt
		notifyMovePlaceable(oldSquare.getXIndex(), oldSquare.getYIndex());
		oldSquare.deletePlaceable();
	}
	
	private void notifyMovePlaceable(int oldXPos, int oldYPos) {
		MessageInformation messageInfo = new MessageInformation("MovePlaceable");
		messageInfo.putValue("oldXPos", oldXPos);
		messageInfo.putValue("oldYPos", oldYPos);
		messageInfo.putValue("newXPos", this.getXIndex());
		messageInfo.putValue("newYPos", this.getYIndex());
		messageInfo.putValue("placeableId", this.placeableOnSquare.getId().toString());
		
		notifyChange(messageInfo);
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
	
	@Override
	public String toString() {
		return "Square [placeableOnSquare=" + placeableOnSquare + ", xIndex=" + xIndex + ", yIndex=" + yIndex + "]";
	}
}
