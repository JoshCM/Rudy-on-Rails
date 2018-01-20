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
public class Sensor extends InteractiveGameObject {
	
	private UUID railId;
	private boolean active;
	private ScriptableObject scriptableObject;

	public Sensor(String sessionName, Square square, UUID railId) {
		super(sessionName, square);
		this.railId = railId;
		this.active = false;
		
		// Vorbereitung für Skripting
		ProxyObject sensorProxy = new SensorProxy(this, square);
		scriptableObject = new ScriptableObject(sensorProxy);
		GameSessionManager.getInstance().getGameSessionByName(sessionName).addScriptableObject(scriptableObject);
		
		notifySensorPlaced();		
	}

	@Override
	public void specificUpdate() {
				
	}
	
	/**
	 * Schickt eine Nachricht an den Client, um den Sensor auf einer Rail zu platzieren
	 */
	private void notifySensorPlaced() {
		MessageInformation message = new MessageInformation("PlaceSensor");
		message.putValue("railId", railId);
		notifyChange(message);		
	}
	
	public void activate() {
		active = true;
	}
	
	public void deactivate() {
		active = false;
	}
	
	public boolean isActive() {
		return active ? true : false;
	}
	
	/**
	 * Änderrt und Führt das mitgegebene Script aus
	 * @param currentScriptName
	 */
	public void changeCurrentScriptFilename(String currentScriptName) {
		scriptableObject.changeCurrentScriptFilename(currentScriptName);
		notifySensorActivated();
	}
	
	/**
	 * Schickt eine Nachricht an den Client, um den Sensor zu aktivieren
	 */
	private void notifySensorActivated() {
		MessageInformation message = new MessageInformation("ActivateSensor");
		message.putValue("railId", railId);
		message.putValue("active", active);
		notifyChange(message);
	}
}
