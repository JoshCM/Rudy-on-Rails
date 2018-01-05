package models.game;

import models.session.RoRSession;

/**
 * @author Andreas Pöhler, Isabell Rott, Juliane Lies
 * Klasse für Ressource Kohle
 */
public class Coal extends Resource {

	public Coal(String sessionName, Square square) {
		super(sessionName, square, "Coal");
	}

	@Override
	public PlaceableOnSquare loadFromMap(Square square, RoRSession session) {
		// TODO Auto-generated method stub
		return null;
	}
}
