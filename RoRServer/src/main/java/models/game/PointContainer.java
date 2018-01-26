package models.game;

public class PointContainer extends Resource {
	protected PointContainer(String sessionName, Square square) {
		super(sessionName, square, "PointContainer");
	}
	
	protected PointContainer(String sessionName) {
		super(sessionName, "PointContainer");
	}
}
