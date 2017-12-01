package models;

/**
 * Interface fuer Objekte, die auf einer Rail platziert werden
 */
public interface PlaceableOnSquare extends Model, Placeable {

	void setPlaceableOnRail(PlaceableOnRail placeableOnRail);
}
