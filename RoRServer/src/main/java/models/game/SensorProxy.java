package models.game;
import java.util.List;

import models.scripts.ProxyObject;
import models.session.GameSessionManager;

public class SensorProxy implements ProxyObject {

	private Sensor sensor;
	private Square square;
	private Loco loco;
	
	public SensorProxy (Sensor sensor, Square square) {
		this.sensor = sensor;
		this.square = square;
	}
	
	// Methoden für Script...
	public void funny() {
		System.out.println("Funny");
	}
	
	public void setLoco(Loco loco) {
		this.loco = loco;
	}
	
	public void stop() {
		loco.changeSpeed(0);
	}
	
	public void destroyTrain() {
		System.out.println("Loco zerstört: " + loco.toString());
		List<Loco> locos = GameSessionManager.getInstance().getGameSession().getLocos();
		locos.remove(loco);
	}
	
	public void info() {
		System.out.println("Zug angekommen: " + loco.toString());
	}
}
