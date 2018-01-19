package models.game;

import java.util.UUID;

import communication.MessageInformation;
import models.scripts.ProxyObject;
import models.scripts.ScriptableObject;
import models.session.GameSession;
import models.session.GameSessionManager;

/**
 * Model für Sensor
 * @author Andreas Pöhler
 *
 */
public class Sensor extends TickableGameObject {
	
	private UUID railId;
	private ScriptableObject scriptableObject;

	public Sensor(String sessionName, UUID railId) {
		super(sessionName);
		this.railId = railId;
		
		// Vorbereitung für Skripting
		ProxyObject sensorProxy = new SensorProxy(this);
		scriptableObject = new ScriptableObject(sensorProxy);
		GameSessionManager.getInstance().getGameSessionByName(sessionName).addScriptableObject(scriptableObject);
		
		notifySensorActivated();		
	}

	@Override
	public void specificUpdate() {
				
	}
	
	public void notifySensorActivated() {
		MessageInformation message = new MessageInformation("UpdateSensor");
		message.putValue("railId", railId);
		notifyChange(message);		
	}
}
