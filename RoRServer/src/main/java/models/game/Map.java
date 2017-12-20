package models.game;

import models.base.ModelBase;
import java.util.Arrays;
import java.util.UUID;

import communication.MessageInformation;


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
		
		for(int x= 0; x < mapSize; x++) {
			for(int y = 0; y < mapSize; y++) {
				Square s = new Square(sessionName, x, y);
				squares[x][y] = s;
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
	
	public Square getSquare(int x, int y) {
		return squares[x][y];
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

	public void notifyGameStarted() {
		MessageInformation messageInfo = new MessageInformation("StartGame");
		notifyChange(messageInfo);
	}
	
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
}
