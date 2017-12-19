package models.game;

import models.base.ModelBase;
import java.util.Arrays;
import java.util.UUID;

import org.hamcrest.core.IsInstanceOf;

import communication.MessageInformation;
import exceptions.NotMoveableException;


/**
 * Klasse, die das Spielfeld darstellt und aus Feldern (Squares) besteht
 */
public class Map extends ModelBase {
	private String name;
	private Square squares [][];
	private final int mapSize = 50;
	
	/**
	 * Jedes Square auf der Map braucht einen Index,
	 * um jedem Objekt, das auf einem Square platziert wird, ein eindeutiges Objekt zuzuordnen
	 */
	public Map(String sessionName) {
		super(sessionName);
		squares = new Square[mapSize][mapSize];
		
		for(int i= 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				Square s = new Square(sessionName, this, i, j);
				squares[i][j] = s;
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void ChangeName(String name) {
		this.name = name;
		notifyChangedName();
	}
	
	private void notifyChangedName() {
		MessageInformation messageInformation = new MessageInformation("UpdateNameOfMap");
		messageInformation.putValue("mapName", name);
		notifyChange(messageInformation);
	}
	
	public Square getSquare(int i, int j) {
		return squares[i][j];
	}
	
	public Square[][] getSquares() {
		return squares;
	}
	

	public void setSquares(Square squares[][]) {
		this.squares = squares;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Map other = (Map) obj;
		if (mapSize != other.mapSize)
			return false;
		if (!Arrays.deepEquals(squares, other.squares))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mapSize;
		result = prime * result + Arrays.deepHashCode(squares);
		return result;
	}

	public PlaceableOnSquare getPlaceableById(UUID id) {
		for(Square[] squares : getSquares())
        {
			for(Square square : squares) {
				PlaceableOnSquare placeableOnSquare = square.getPlaceableOnSquare();
	            if (placeableOnSquare != null)
	            {
	                if (placeableOnSquare.getId().equals(id))
	                {
	                    return placeableOnSquare;
	                }
	            }
			}
        }
        return null;
	}
	
	public Square getSquareById(UUID id) {
		for(Square[] squares : getSquares())
        {
			for(Square square : squares) {
				if(square.getId().equals(id)) {
					return square;
				}
			}
        }
        return null;
	}

	public void movePlaceableOnSquare(Square oldSquare, Square newSquare) {
		if(validateMovePlaceableOnSquare(oldSquare, newSquare)) {
			InteractiveGameObject tempPlaceableOnSquare = (InteractiveGameObject)oldSquare.getPlaceableOnSquare();
			oldSquare.setPlaceable(null);
			newSquare.setPlaceable((PlaceableOnSquare)tempPlaceableOnSquare);
			
			// entweder nur ids oder nur x und y, wir müssen uns entscheiden
			tempPlaceableOnSquare.setSquareId(newSquare.getId());
			tempPlaceableOnSquare.setXPos(newSquare.getXIndex());
			tempPlaceableOnSquare.setYPos(newSquare.getYIndex());
			
			// sections müssen auch die square-Änderung mitbekommen
			if(tempPlaceableOnSquare instanceof Rail) {
				for(RailSection railRection : ((Rail) tempPlaceableOnSquare).getRailSectionList()) {
					railRection.changeSquare(newSquare);
				}
			}
			
			notifyMovedPlaceableOnSquare(oldSquare, newSquare);
		}else {
			throw new NotMoveableException(String.format("PlaceableOnSquare von %s ist nicht auf %s verschiebar", oldSquare.toString(), newSquare.toString()));
		}
	}
	
	private void notifyMovedPlaceableOnSquare(Square oldSquare, Square newSquare) {
		MessageInformation messageInformation = new MessageInformation("MovePlaceableOnSquare");
		messageInformation.putValue("oldXPos", oldSquare.getXIndex());
		messageInformation.putValue("oldYPos", oldSquare.getYIndex());
		messageInformation.putValue("newXPos", newSquare.getXIndex());
		messageInformation.putValue("newYPos", newSquare.getYIndex());
		notifyChange(messageInformation);
	}

	private boolean validateMovePlaceableOnSquare(Square oldSquare, Square newSquare) {
		if(newSquare.getPlaceableOnSquare() != null)
			return false;
		return true;
	}
}
