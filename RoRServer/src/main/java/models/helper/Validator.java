package models.helper;

import models.game.Compass;
import models.game.InteractiveGameObject;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
import models.game.Trainstation;

/**
 * Validiert mögliche Positionen von PlaceableOnSquares
 *
 */
public class Validator {
	
	/**
	 * Validiert ob das PlaceableOnSquare von oldSquare auf newSquare gesetzt werden kann
	 * @param oldSquare
	 * @param newSquare
	 * @return boolean
	 */
	public static boolean validateMovePlaceableOnSquare(Square oldSquare, Square newSquare, Map map) {
		InteractiveGameObject placeableOnSquare = (InteractiveGameObject) oldSquare.getPlaceableOnSquare();
		if (placeableOnSquare instanceof Rail) {
			return validateRailOnMap(newSquare);
		} else if (placeableOnSquare instanceof Trainstation) {
			Trainstation possibleTrainstation = (Trainstation) placeableOnSquare;
			return validateTrainstationOnMap(newSquare, possibleTrainstation.getAlignment(), map);
		}
		return false;
	}

	/**
	 * Validiert ob eine Rail auf newSquare gesetzt werden kann
	 * @param newSquare
	 * @return boolean
	 */
	public static boolean validateRailOnMap(Square newSquare) {
		if (newSquare.getPlaceableOnSquare() != null)
			return false;
		return true;
	}

	/**
	 * Validiert ob eine Trainstation auf newSquare gesetzt werden kann
	 * @param newSquare Neues Square 
	 * @param possibleTrainstation Umzusetzende Trainstation
	 * @return boolean
	 */
	public static boolean validateTrainstationOnMap(Square newSquare, Compass alignment, Map map) {
		int railCount = Trainstation.RAIL_COUNT;

		if (!validatePossibleTrainstation(newSquare))
			return false;
		if (!validatePossibleRails(newSquare, alignment, railCount, map))
			return false;
		return true;
	}

	/**
	 * Validiert ob das Trainstation-Gebäude auf das newSquare gesetzt werden kann
	 * @param newSquare
	 * @return boolean
	 */
	private static boolean validatePossibleTrainstation(Square newSquare) {
		if (newSquare != null) {
			if (newSquare.getPlaceableOnSquare() != null) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Gibt an ob die neuen Squares der TrainstationRails einer neuen Trainstation platzierbar sind
	 * @param newSquare
	 * @param alignment Ausrichtung der Trainstation
	 * @return boolean
	 */
	private static boolean validatePossibleRails(Square newSquare, Compass alignment, int railCount, Map map) {
		// Anzahl der Rails über- oder unterhalb der Trainstation
		int railSpan = railCount / 2;
		
		// Map Größe
		int mapSize = map.getSquares().length;
		
		// X und Y der möglichen Trainstation
		int trainstationX = newSquare.getXIndex();
		int trainstationY = newSquare.getYIndex();
		
		// je nach Richtung der Trainstation werden ihre Rails oberhalb, unterhalb, rechts oder links davon angelegt
		// der jeweilige wert(x oder y) muss dann daran angepasst werden
		int xShifting = 0;
		int yShifting = 0;
		switch(alignment) {
		case NORTH:
			yShifting = -1;
			break;
		case EAST:
			xShifting = +1;
			break;
		case SOUTH:
			yShifting = +1;
			break;
		case WEST:
			xShifting = -1;
		}
		
		// iteriert über die Anzahl der Rails der Trainstation
		for(int i = -railSpan; i <= railSpan; i++) {
			int possibleSquareX = trainstationX + xShifting;
			int possibleSquareY = trainstationY + yShifting;
			
			// Wenn die Rails vertikal oder horizontal angeordnet sind
			if(xShifting != 0)
				possibleSquareY += i;
			else if(yShifting != 0)
				possibleSquareX += i;
			
			// überprüfung ob SquarePosition kleiner ist als die Map
			if(possibleSquareX < 0 || possibleSquareY < 0)
				return false;
			// überprüfung ob SquarePosition größer ist als die Map
			if(possibleSquareX > mapSize - 1 || possibleSquareY > mapSize -1)
				return false;
			// überprüfung ob SquarePosition schon belegt ist
			if(map.getSquare(possibleSquareX, possibleSquareY).getPlaceableOnSquare() != null)
				return false;
		}
		
		return true;
	}
}
