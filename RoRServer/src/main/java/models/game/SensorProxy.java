package models.game;
import java.util.List;

import models.scripts.ProxyObject;
import models.session.GameSessionManager;

public class SensorProxy implements ProxyObject {

	private Sensor sensor;
	private Square square;
	
	public SensorProxy (Sensor sensor, Square square) {
		this.sensor = sensor;
		this.square = square;
	}
	
	// Methoden für Script...
	public void funny() {
		System.out.println("Funny");
	}
	
	public void stopTrain() {
		
	}
	
	public void destroyTrain() {
		List<Loco> locos = GameSessionManager.getInstance().getGameSession().getLocos();
		for (Loco loco : locos) {
			int xpos = loco.getXPos();
			int ypos = loco.getYPos();
			if (xpos == square.getXIndex() && ypos == square.getYIndex()) {
				System.out.println("Tschüss " + loco.toString());
				locos.remove(loco);
			}
		}
	}
	
}
