package models.game;

import models.session.RoRSession;

/**
 * @author Andreas P�hler, Isabell Rott, Juliane Lies
 * Klasse f�r Ressource Kohle
 */
public class Gold extends Resource{
	
	public Gold(String sessionName, Square square) {
		super(sessionName, square, "Gold");
	}

	@Override
	public PlaceableOnSquare loadFromMap(Square square, RoRSession session) {
		// TODO Auto-generated method stub
		return null;
	}
}
