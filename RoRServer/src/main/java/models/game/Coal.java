package models.game;

/**
 * @author Andreas Pöhler, Isabell Rott, Juliane Lies
 * Klasse für Ressource Kohle
 */
public class Coal extends Resource {

	public Coal(String sessionName, Square square, int quantity) {
		super(sessionName, square, "Coal", quantity);
	}
	
	public Coal(String sessionName, int quantity) {
		super(sessionName, "Coal", quantity);
	}
}
