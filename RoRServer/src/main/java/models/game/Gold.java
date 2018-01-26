package models.game;

import models.session.RoRSession;

/**
 * @author Andreas Pöhler, Isabell Rott, Juliane Lies
 * Klasse für Ressource Kohle
 */
public class Gold extends Resource{
	public Gold(String sessionName, Square square, int quantity) {
		super(sessionName, square, "Gold", quantity);
	}
	
	public Gold(String sessionName, int quantity) {
		super(sessionName, "Gold", quantity);
	}
}
