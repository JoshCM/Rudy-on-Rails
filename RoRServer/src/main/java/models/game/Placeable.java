package models.game;

import models.base.Model;
import models.session.RoRSession;

/**
 * Interface für platzierbare Objekte
 *
 */
public interface Placeable extends Model {
	
	PlaceableOnSquare loadFromMap(Square square, RoRSession session);
	
}