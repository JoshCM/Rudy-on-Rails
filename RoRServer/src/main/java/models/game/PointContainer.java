package models.game;

public class PointContainer extends Resource {
	protected PointContainer(String sessionName, Square square, int quantity) {
		super(sessionName, square, "PointContainer", quantity);
	}
	
	protected PointContainer(String sessionName, int quantity) {
		super(sessionName, "PointContainer", quantity);
	}
}
