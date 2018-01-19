package models.game;
import models.scripts.ProxyObject;

public class SensorProxy implements ProxyObject {

	private Sensor sensor;
	
	public SensorProxy (Sensor sensor) {
		this.sensor = sensor;
	}
	
	// Methoden für Script...

}
