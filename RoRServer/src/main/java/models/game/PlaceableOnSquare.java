package models.game;

import models.session.RoRSession;

/**
 * Interface fuer Objekte, die auf einer Rail platziert werden
 */
public interface PlaceableOnSquare extends Placeable {

	void loadFromMap(Square square, RoRSession session);
	
}
