package models.game;

import models.base.ModelBase;

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

		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
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

	public void movePlaceableOnSquare(Square oldSquareOfPlaceable, Square newSquareOfPlaceable) {
		if (validateMovePlaceableOnSquare(oldSquareOfPlaceable, newSquareOfPlaceable)) {
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
			}
			notifyMovedPlaceableOnSquare(oldSquareOfPlaceable, newSquareOfPlaceable, placeableOnSquare);
		} else {
			throw new NotMoveableException(String.format("PlaceableOnSquare von %s ist nicht auf %s verschiebbar",
					oldSquareOfPlaceable.toString(), newSquareOfPlaceable.toString()));
		}
	}

	private void notifyMovedPlaceableOnSquare(Square oldSquare, Square newSquare,
			InteractiveGameObject movedPlaceableOnSquare) {
		switch (movedPlaceableOnSquare.getClass().getSimpleName()) {
		case "Rail":
			notifyMovedRail(oldSquare, newSquare);
			break;
		case "Trainstation":
			notifyMovedTrainstation(oldSquare, newSquare, (Trainstation) movedPlaceableOnSquare);
			break;
		}
	}

	private void notifyMovedTrainstation(Square oldSquare, Square newSquare, Trainstation trainstation) {
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

	private boolean validateMovePlaceableOnSquare(Square oldSquare, Square newSquare) {
		InteractiveGameObject placeableOnSquare = (InteractiveGameObject) oldSquare.getPlaceableOnSquare();
		if (placeableOnSquare instanceof Rail) {
			return validateSetRailRailOnMap(newSquare);
		} else if (placeableOnSquare instanceof Trainstation) {
			Trainstation possibleTrainstation = (Trainstation) placeableOnSquare;
			return validateSetTrainstationOnMap(newSquare, possibleTrainstation.getAlignment());
		}
		return false;
	}

	private boolean validateSetRailRailOnMap(Square newSquare) {
		if (newSquare.getPlaceableOnSquare() != null)
			return false;
		return true;
	}

	public boolean validateSetTrainstationOnMap(Square square, Compass alignment) {
		// TODO: ist noch zu konfus das height auch width sein kann und andersherum
		int trainstationRailsHeight = 3;
		int trainstationRailsWidth = 1;

		int trainstationHeight = 0;
		int trainstationWidth = 0;

		if (alignment.equals(Compass.EAST) || alignment.equals(Compass.WEST)) {
			trainstationHeight = trainstationRailsHeight;
			trainstationWidth = trainstationRailsWidth + 1; // +1 für das trainstation feld
		} else if (alignment.equals(Compass.NORTH) || alignment.equals(Compass.SOUTH)) {
			trainstationHeight = trainstationRailsWidth + 1; // +1 für das trainstation feld
			trainstationWidth = trainstationRailsHeight;
		}

		if (!validatePossibleTrainstation(this, square))
			return false;
		if (!validateWindowEdges(this, square, trainstationHeight, trainstationWidth))
			return false;
		if (!validatePossibleRails(this, square, alignment))
			return false;
		return true;
	}

	private boolean validatePossibleTrainstation(Map map, Square square) {
		if (square != null) {
			if (square.getPlaceableOnSquare() != null) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean validateWindowEdges(Map map, Square square, int trainstationHeight, int trainstationWidth) {
		// TODO: muss noch an die ränder optimal angepasst werden

		// wenn y kleiner ist als die höhe minus das trainstation-feld
		if (square.getYIndex() < (trainstationHeight - 1))
			return false;
		// wenn y größer ist als höhe der map minus die höhe minus das trainstation-feld
		if (square.getYIndex() > (map.getSquares().length - 1) - (trainstationHeight + 1))
			return false;
		// wenn x kleiner ist als breite der map minus die breite minus das
		// trainstation-feld
		if (square.getXIndex() < (trainstationWidth - 1))
			return false;
		// wenn x kleiner ist als anzahl der horizontalen rails
		if (square.getXIndex() > (map.getSquares().length - 1) - (trainstationWidth + 1))
			return false;
		return true;
	}

	private boolean validatePossibleRails(Map map, Square square, Compass alignment) {

		// Iteriert über die Squares für die möglichen Rails der Trainstation
		for (int i = 0; i <= 2; i++) {

			Square possibleRailSquare = null;
			switch (alignment) {
			case EAST:
				possibleRailSquare = map.getSquare(square.getXIndex() + 1, square.getYIndex() + (i - 1));
				break;
			case SOUTH:
				possibleRailSquare = map.getSquare(square.getXIndex() + (i - 1), square.getYIndex() + 1);
				break;
			case WEST:
				possibleRailSquare = map.getSquare(square.getXIndex() - 1, square.getYIndex() + (i - 1));
				break;
			case NORTH:
				possibleRailSquare = map.getSquare(square.getXIndex() + (i - 1), square.getYIndex() - 1);
				break;
			}

			// Square für Rail ist vorhanden
			if (possibleRailSquare != null) {
				// Square für Rail ist belegt
				if (possibleRailSquare.getPlaceableOnSquare() != null) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
}
