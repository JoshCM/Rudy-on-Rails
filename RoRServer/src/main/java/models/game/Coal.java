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
	
	public Coal(String sessionName) {
		super(sessionName, "Coal");
	}
}
