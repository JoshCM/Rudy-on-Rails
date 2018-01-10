package models.game;

import models.session.RoRSession;

/**
 * Interface fuer Objekte, die auf einer Rail platziert werden
 */
public interface PlaceableOnRail extends Placeable {
	PlaceableOnRail loadFromMap(Square square, RoRSession session);
}
