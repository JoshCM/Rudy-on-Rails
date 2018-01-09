package models.game;

import models.base.PlaceableModel;

/**
 * Interface fuer Objekte, die auf einer Rail platziert werden
 */
public abstract class PlaceableOnSquare extends PlaceableModel {

	public PlaceableOnSquare(int x, int y) {
		super(x, y);
	}
}
