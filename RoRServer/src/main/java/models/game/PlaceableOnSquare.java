package models.game;

/**
 * Interface fuer Objekte, die auf einer Rail platziert werden
 */
public interface PlaceableOnSquare extends Placeable {

	void setPlaceableOnRail(PlaceableOnRail placeableOnRail);
}
