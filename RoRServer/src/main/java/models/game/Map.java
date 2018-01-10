package models.game;

import models.base.ModelBase;
import models.helper.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import communication.MessageInformation;
import exceptions.NotMoveableException;

/**
 * Klasse, die das Spielfeld darstellt und aus Feldern (Squares) besteht
 */
public class Map extends ModelBase {
	private String name;
	private Square squares[][];
	private final int mapSize = 50;

	/**
	 * Jedes Square auf der Map braucht einen Index, um jedem Objekt, das auf einem
	 * Square platziert wird, ein eindeutiges Objekt zuzuordnen
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
	
	public int getMapSize() {
		return mapSize;
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
		if(x >= 0 && x < squares.length && y >= 0 && y < squares.length)
			return squares[x][y];
		return null;
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

	public PlaceableOnSquare getPlaceableOnSquareById(UUID id) {
		for (Square[] squares : getSquares()) {
			for (Square square : squares) {
				PlaceableOnSquare placeableOnSquare = square.getPlaceableOnSquare();
				if (placeableOnSquare != null) {
					if (placeableOnSquare.getId().equals(id)) {
						return placeableOnSquare;
					}
				}
			}
		}
		return null;
	}
	
	public PlaceableOnRail getPlaceableOnRailById(UUID id) {
		for (Square[] squares : getSquares()) {
			for (Square square : squares) {
				PlaceableOnSquare placeableOnSquare = square.getPlaceableOnSquare();
				if (placeableOnSquare != null) {
					if (placeableOnSquare instanceof Rail) {
						Rail rail = (Rail)placeableOnSquare;
						if (rail.getPlaceableOnrail() instanceof Mine) {
							Mine mine = (Mine)rail.getPlaceableOnrail();
							if (mine.getId().equals(id)) {
								return mine;
							}
						}
					}	
				}
			}
		}
		return null;
	}

	public Square getSquareById(UUID id) {
		for (Square[] squares : getSquares()) {
			for (Square square : squares) {
				if (square.getId().equals(id)) {
					return square;
				}
			}
		}
		return null;
	}
	
	/**
	 * Setzt den Session Name für die Map und jedes Square
	 * @param sessionName
	 */
	public void setSessionNameForMapAndSquares(String sessionName) {
		this.sessionName = sessionName;
		
		for (Square[] squares : getSquares()) {
			for (Square square : squares) {
				square.setSessionName(sessionName);
			}
		}
	}

	/**
	 * Gibt ein zugehöriges neuse Square, nach dem Moven der Trainstation, für eine
	 * Rail zurück
	 * 
	 * @param trainstationSquare
	 * @param alignment
	 * @return Zugehöriges Square einer Rail
	 */
	private Square getTrainstationRailSquare(Rail trainstationRail, Trainstation newTrainstation,
			int oldPlaceableOnSquareXPos, int oldPlaceableOnSquareYPos) {
		int trainstationRailXSpan = trainstationRail.getXPos() - oldPlaceableOnSquareXPos;
		int trainstationRailYSpan = trainstationRail.getYPos() - oldPlaceableOnSquareYPos;

		int newSquareX = newTrainstation.getXPos() + trainstationRailXSpan;
		int newSquareY = newTrainstation.getYPos() + trainstationRailYSpan;
		return getSquare(newSquareX, newSquareY);
	}
	
	public void movePlaceableOnRail(Square oldSquare, Square newSquare) {
		
		notifyMovedMine(oldSquare, newSquare);
	}
	
	public void notifyMovedMine(Square oldSquare, Square newSquare) {
		MessageInformation message = new MessageInformation("MoveMine");
		message.putValue("oldXPos", oldSquare.getXIndex());
		message.putValue("oldYPos", oldSquare.getYIndex());
		message.putValue("newXPos", newSquare.getXIndex());
		message.putValue("newYPos", newSquare.getYIndex());
		Rail rail = (Rail) newSquare.getPlaceableOnSquare();
		message.putValue("alignment", rail.getAlignment().toString());
		notifyChange(message);
	}

	/**
	 * Verschiebt ein PlaceableOnSquare von oldSquareOfPlaceable auf newSquareOfPlaceable
	 * @param oldSquareOfPlaceable Altes Square von dem das PlaceableOnSquare verschoben werden soll
	 * @param newSquareOfPlaceable Neues Square auf den das PlaceableOnSquare verschoben werden soll
	 */
	public void movePlaceableOnSquare(Square oldSquareOfPlaceable, Square newSquareOfPlaceable) {
		if (Validator.validateMovePlaceableOnSquare(oldSquareOfPlaceable, newSquareOfPlaceable, this)) {
			InteractiveGameObject placeableOnSquare = (InteractiveGameObject) oldSquareOfPlaceable.getPlaceableOnSquare();
			int oldPlaceableOnSquareXPos = placeableOnSquare.getXPos();
			int oldPlaceableOnSquareYPos = placeableOnSquare.getYPos();
			oldSquareOfPlaceable.setPlaceableOnSquare(null);
			newSquareOfPlaceable.setPlaceableOnSquare((PlaceableOnSquare) placeableOnSquare);

			// entweder nur ids oder nur x und y, wir müssen uns entscheiden
			placeableOnSquare.setSquareId(newSquareOfPlaceable.getId());
			placeableOnSquare.setXPos(newSquareOfPlaceable.getXIndex());
			placeableOnSquare.setYPos(newSquareOfPlaceable.getYIndex());

			if (placeableOnSquare instanceof Rail) {
				Rail rail = (Rail) placeableOnSquare;
				// sections müssen auch die square-Änderung mitbekommen
				for (RailSection railSection : rail.getRailSectionList()) {
					railSection.changeSquare(newSquareOfPlaceable);
				}
				notifyMovedRail(oldSquareOfPlaceable, newSquareOfPlaceable);
			} else if (placeableOnSquare instanceof Trainstation) {
				Trainstation trainstation = (Trainstation) placeableOnSquare;
				// trainstationRails müssen auch die square-Änderung mitbekommen
				for (Rail trainstationRail : trainstation.getTrainstationRails()) {
					Square oldSquareOfRail = getSquare(trainstationRail.getXPos(), trainstationRail.getYPos());
					oldSquareOfRail.setPlaceableOnSquare(null);
					Square newSquareOfRail = getTrainstationRailSquare(trainstationRail, trainstation,
							oldPlaceableOnSquareXPos, oldPlaceableOnSquareYPos);
					trainstationRail.changeSquare(newSquareOfRail);
					newSquareOfRail.setPlaceableOnSquare(trainstationRail);
				}
				notifyMovedTrainstation(oldSquareOfPlaceable, newSquareOfPlaceable, trainstation);
			}
		} else {
			throw new NotMoveableException(String.format("PlaceableOnSquare von %s ist nicht auf %s verschiebbar",
					oldSquareOfPlaceable.toString(), newSquareOfPlaceable.toString()));
		}
	}

	private void notifyMovedTrainstation(Square oldSquare, Square newSquare, Trainstation trainstation) {
		// TODO: Trainstation und Rails einzeln moven als Command
		MessageInformation messageInformation = new MessageInformation("MoveTrainstation");
		messageInformation.putValue("oldXPos", oldSquare.getXIndex());
		messageInformation.putValue("oldYPos", oldSquare.getYIndex());
		messageInformation.putValue("newXPos", newSquare.getXIndex());
		messageInformation.putValue("newYPos", newSquare.getYIndex());

		List<List<String>> trainstationRailsCoordinateList = new ArrayList<List<String>>();
		for (Rail trainstationRail : trainstation.getTrainstationRails()) {
			List<String> trainstationRailCoordinates = new ArrayList<String>();
			Square newTrainstationRailSquare = this.getSquareById(trainstationRail.getSquareId());
			trainstationRailCoordinates.add(trainstationRail.getId().toString());
			trainstationRailCoordinates.add(String.valueOf(newTrainstationRailSquare.getXIndex()));
			trainstationRailCoordinates.add(String.valueOf(newTrainstationRailSquare.getYIndex()));
			trainstationRailsCoordinateList.add(trainstationRailCoordinates);
		}
		messageInformation.putValue("trainstationRailsCoordinates", new Gson().toJson(trainstationRailsCoordinateList));
		notifyChange(messageInformation);
	}

	private void notifyMovedRail(Square oldSquare, Square newSquare) {
		MessageInformation messageInformation = new MessageInformation("MoveRail");
		messageInformation.putValue("oldXPos", oldSquare.getXIndex());
		messageInformation.putValue("oldYPos", oldSquare.getYIndex());
		messageInformation.putValue("newXPos", newSquare.getXIndex());
		messageInformation.putValue("newYPos", newSquare.getYIndex());
		notifyChange(messageInformation);
	}
}