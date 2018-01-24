package models.game;

import java.util.ArrayList;
import java.util.List;
import models.helper.CompassHelper;
import models.scripts.ProxyObject;
import models.session.GameSession;
import models.session.GameSessionManager;

/**
 * Wird in das aktuelle PythonScript der GhostLoco reingereicht Das Script hat
 * dann Zugriff auf alle public-Methoden dieser Klasse
 */
public class GhostLocoProxy implements ProxyObject {
	private GhostLoco ghostLoco;
	private GameSession gameSession;
	private Map map;

	private int VISIBLE_SQUARE_AMOUNT_SIDEWAYS = 2;
	private int VISIBLE_SQUARE_AMOUNT_FORWARD = 5;

	public GhostLocoProxy(GhostLoco ghostLoco) {
		this.ghostLoco = ghostLoco;
		this.gameSession = GameSessionManager.getInstance().getGameSession();
		this.map = gameSession.getMap();
	};

	/**
	 * Setzt die Geschwindigkeit der GhostLoco, sofern es sich um einen Wert
	 * zwischen -1 und 5 handelt
	 * 
	 * @param speed
	 */
	public void changeSpeed(int speed) {
		if (speed >= -1 && speed <= 5) {
			ghostLoco.changeSpeed(speed);
		}
	}

	/**
	 * Gibt eine Liste von Listen<String> zurück, in denen Objekte auf der Map als
	 * String abfragbar sind Die Reihenfolge der Felder ist von unten links nach
	 * oben rechts, wobei immer erst die x-Achse durchlaufen wird
	 * 
	 * @return
	 */
	public List<List<String>> getObjectsOnVisibleSquares() {
		List<List<String>> result = new ArrayList<List<String>>();

		for (int y = 1; y <= VISIBLE_SQUARE_AMOUNT_FORWARD; y++) {
			for (int x = -VISIBLE_SQUARE_AMOUNT_SIDEWAYS; x <= VISIBLE_SQUARE_AMOUNT_SIDEWAYS; x++) {
				result.add(getObjectsOnSquare(x, y));
			}
		}

		return result;
	}

	/**
	 * Gibt eine Liste von Objekten, die sich auf diesem Feld befinden als String
	 * zurück
	 * 
	 * @param sideways
	 *            links: negativ; rechts: positiv
	 * @param forward
	 *            1 = erstes Feld vor der GhostLoco
	 * @return
	 */
	public List<String> getObjectsOnSquare(int sideways, int forward) {
		List<String> result = new ArrayList<String>();

		if (isSquareVisibleForProxy(sideways, forward)) {
			int squarePosX = CompassHelper.getRealXForDirection(ghostLoco.getDrivingDirection(), ghostLoco.getXPos(),
					ghostLoco.getYPos(), sideways, forward);
			int squarePosY = CompassHelper.getRealYForDirection(ghostLoco.getDrivingDirection(), ghostLoco.getXPos(),
					ghostLoco.getYPos(), sideways, forward);

			if (squarePosX <= map.getMapSize() && squarePosY <= map.getMapSize()) {
				result = collectObjectsFromSquareAsStrings(squarePosX, squarePosY);
			} else {
				result.add("NotOnMap");
			}
		} else {
			result.add("NotVisible");
		}

		return result;
	}

	private List<String> collectObjectsFromSquareAsStrings(int squarePosX, int squarePosY) {
		List<String> result = new ArrayList<String>();
		Square square = map.getSquare(squarePosX, squarePosY);
		PlaceableOnSquare placeableOnSquare = square.getPlaceableOnSquare();

		if (placeableOnSquare != null) {
			if (placeableOnSquare instanceof Trainstation) {
				result.add("Trainstation");
			} else if (placeableOnSquare instanceof Rail) {
				fillObjectStringListWithValuesFromRail(result, placeableOnSquare);
			} else if (placeableOnSquare instanceof Resource) {
				Resource resource = (Resource) placeableOnSquare;
				result.add(resource.getDescription());
			}
		}
		return result;
	}

	private void fillObjectStringListWithValuesFromRail(List<String> result, PlaceableOnSquare placeableOnSquare) {
		result.add("Rail");

		Rail rail = (Rail) placeableOnSquare;

		// Füge Signal-Zustand der Liste hinzu, falls es aktive Signale gibt
		// Dabei wird nur das Signal in der Richtung betrachtet, in die der Zug
		// unterwegs ist
		if (rail.getSignals() != null) {
			boolean activeSignal = rail.getSignals()
					.isSignalActive(ghostLoco.getDirectionNegation(ghostLoco.getDrivingDirection()));
			if (activeSignal) {
				result.add("ActiveSignal");
			} else {
				result.add("InactiveSignal");
			}
		}

		if (rail.getPlaceableOnrail() != null && rail.getPlaceableOnrail() instanceof Mine) {
			result.add("Mine");
		}

		for (Loco loco : gameSession.getLocos()) {
			if (loco.getRail().getId().equals(rail.getId())) {
				result.add("Loco");
			}
		}
	}

	private boolean isSquareVisibleForProxy(int x, int y) {
		if (x >= -VISIBLE_SQUARE_AMOUNT_SIDEWAYS && x <= VISIBLE_SQUARE_AMOUNT_SIDEWAYS) {
			if (y <= VISIBLE_SQUARE_AMOUNT_FORWARD) {
				return true;
			}
		}
		return false;
	}

	public long getSpeed() {
		return ghostLoco.getSpeed();
	}
	
	public boolean isPicksUpGoldContainerNextToRails() {
		return ghostLoco.isPicksUpGoldContainerNextToRails();
	}
	
	public void setPicksUpGoldContainerNextToRails(boolean picksUpGoldContainerNextToRails) {
		ghostLoco.setPicksUpGoldContainerNextToRails(picksUpGoldContainerNextToRails);
	}
	
	public boolean isPicksUpCoalContainerNextToRails() {
		return ghostLoco.isPicksUpCoalContainerNextToRails();
	}
	
	public void setPicksUpCoalContainerNextToRails(boolean picksUpCoalContainerNextToRails) {
		ghostLoco.setPicksUpGoldContainerNextToRails(picksUpCoalContainerNextToRails);
	}
}
