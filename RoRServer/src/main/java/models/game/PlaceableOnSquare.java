package models.game;

import java.util.UUID;

import models.session.RoRSession;

/**
 * Interface fuer Objekte, die auf einer Rail platziert werden
 */
public interface PlaceableOnSquare extends Placeable {
	PlaceableOnSquare loadFromMap(Square square, RoRSession session);
}
