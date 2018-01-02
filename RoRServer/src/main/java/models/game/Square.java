package models.game;

import java.util.ArrayList;
import java.util.List;

import communication.MessageInformation;
import models.base.ModelBase;
import models.session.GameSessionManager;
import models.session.RoRSession;

/**
 * Klasse fuer ein Feld (Square), dass auf der Map liegt und eine Position hat.
 * Auf dem Feld platzierte Objekte koennen auf die jeweilige Position zugreifen.
 */
public class Square extends ModelBase {
	private PlaceableOnSquare placeableOnSquare = null;
	private int xIndex;
	private int yIndex;
	
	public Square(String sessionName, int xIndex, int yIndex) {
		super(sessionName);
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}
	
	public void deletePlaceable() {
		this.placeableOnSquare = null;
		notifyDeletePlaceable();
	}
	
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	
	private void notifyDeletePlaceable() {
		MessageInformation messageInfo = new MessageInformation("DeletePlaceable");
		// TODO: SpÃ¤ter haben wir die richtigen SquareIds im Client, im Moment noch nicht!!!
		messageInfo.putValue("xPos", this.getXIndex());
		messageInfo.putValue("yPos", this.getYIndex());
		
		notifyChange(messageInfo);
	}
	
	/**
	 * Gibt die benachbarten Squares zurück (Links, Rechts, Oben, Unten)
	 * @return Die Squares werden als Liste zurückgegeben
	 */
	public List<Square> getNeighbouringSquares(){
		
		List<Square> neighbouringSquares = new ArrayList<Square>();
		RoRSession session = GameSessionManager.getInstance().getGameSessionByName(sessionName);
		Map map = session.getMap();
		
		// Linkes Square
		if (xIndex - 1 >= 0) {
			neighbouringSquares.add(map.getSquare(xIndex - 1, yIndex));
		}
		
		// Rechtes Square
		if (xIndex + 1 < map.getMapSize()) {
			neighbouringSquares.add(map.getSquare(xIndex + 1, yIndex));
		}
		
		// Oberes Square
		if (yIndex - 1 >= 0) {
			neighbouringSquares.add(map.getSquare(xIndex, yIndex - 1));
		}
		
		// Unteres Square
		if (yIndex + 1 < map.getMapSize()) {
			neighbouringSquares.add(map.getSquare(xIndex, yIndex + 1));
		}
		
		return neighbouringSquares;
	}
	
	public void setPlaceableOnSquare(PlaceableOnSquare placeable) {
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
