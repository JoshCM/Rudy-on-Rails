package models.game;

import java.util.UUID;

import communication.MessageInformation;
import models.helper.SensorCountdown;
import models.scripts.ProxyObject;
import models.scripts.Script;
import models.scripts.ScriptableObject;
import models.session.GameSession;
import models.session.GameSessionManager;

/**
 * Model für Sensor
 * @author Andreas Pöhler
 *
 */
public class Sensor extends InteractiveGameObject {
	
	public final static int DISTANCE_BETWEEN_LOCO_AND_SENSOR = 5;
	public final static int DEFAULT_ACTIME_TIME = 10;
	public final static int DEFAULT_DELETE_TIME = 5;
	public final static int SECOND = 1000;
	
	private UUID railId;
	private boolean active;
	private ScriptableObject scriptableObject;
	private String currentScriptName;
	private SensorProxy sensorProxy;
	private int activeTime = DEFAULT_ACTIME_TIME;

	public Sensor(String sessionName, Square square, UUID railId) {
		super(sessionName, square);
		this.railId = railId;
		this.active = false;
		
		// Vorbereitung für Skripting
		sensorProxy = new SensorProxy(this, square);
		scriptableObject = new ScriptableObject(sensorProxy);
		// GameSessionManager.getInstance().getGameSessionByName(sessionName).addScriptableObject(scriptableObject);
		
		notifySensorPlaced();		
	}
	
	/**
	 * Schickt eine Nachricht an den Client, um den Sensor auf einer Rail zu platzieren
	 */
	private void notifySensorPlaced() {
		MessageInformation message = new MessageInformation("PlaceSensor");
		message.putValue("railId", railId);
		notifyChange(message);		
	}
	
	private void activate() {
		active = true;
		notifySwitchSensor();
		startCountDownForActiveSensor();
	}
	
	private void deactivateAndDelete() {
		active = false;
		notifySwitchSensor();
		startCountDownToDeleteSensor();
	}
	
	public boolean isActive() {
		return active ? true : false;
	}
	
	/**
	 * Ändert das aktuelle Script und merkt es sich für die Ausführung
	 * @param currentScriptName
	 */
	public void changeCurrentScriptFilename(String currentScriptName) {
		this.currentScriptName = currentScriptName;
		activate();
	}
	
	/**
	 * Führt das gesetzte Script aus, sobald ein Zug auf den Sensor fährt
	 */
	public void runScriptOnTrainArrived(Loco loco) {
		sensorProxy.setLoco(loco);
		scriptableObject.changeCurrentScriptFilename(currentScriptName);
		scriptableObject.callUpdateOnPythonScript();
	}
	
	public boolean checkPosition(int x, int y) {
		return getXPos() == x && getYPos() == y;
	}
	
	/**
	 * Setzt die Zeit in Sekunden, in welcher der Sensor aktiv ist, sobald ein Script ausgewählt wurde
	 * @param seconds
	 */
	public void setAtiveTimeForSensor(int seconds) {
		activeTime = seconds;
	}
	
	/**
	 * Startet einen Countdown für den Sensor, nachdem dieser aktiviert wurde
	 */
	private void startCountDownForActiveSensor() {
		Thread countdown = new Thread(new Runnable() {

			@Override
			public void run() {
				int seconds = activeTime;
				while(seconds > 0) {
					try {
						Thread.sleep(Sensor.SECOND);
						seconds--;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				deactivateAndDelete();
			}
		});
		countdown.start();
	}
	
	/**
	 * Startet einen Countdown zum Löschen des Sensors und löscht diesen nach Ablauf
	 */
	private void startCountDownToDeleteSensor() {
		Thread countdown = new Thread(new Runnable() {

			@Override
			public void run() {
				int seconds = DEFAULT_DELETE_TIME;
				while(seconds > 0) {
					try {
						Thread.sleep(Sensor.SECOND);
						seconds--;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				deleteSensor();
			}	
		});
		countdown.start();
	}
	
	/**
	 * Löscht den Sensor (also sich selbst) -> ist noch sehr unschön gelöst, weiß aber gerade nicht wie es beser ginge
	 */
	private void deleteSensor() {
		notifyDeleteSensor();
		Rail rail = (Rail)GameSessionManager.getInstance().getGameSession().getMap().getPlaceableOnSquareById(railId);
		rail.removeSensor();
	}
	
	/**
	 * Schickt eine Nachricht an den Client, um den Sensor zu aktivieren/deaktivieren
	 */
	private void notifySwitchSensor() {
		MessageInformation message = new MessageInformation("SwitchSensor");
		message.putValue("railId", railId);
		message.putValue("active", active);
		notifyChange(message);
	}
	
	/**
	 * Schickt eine Nachricht an den Client, um den Sensor zu löschen
	 */
	private void notifyDeleteSensor() {
		MessageInformation message = new MessageInformation("DeleteSensor");
		message.putValue("railId", railId);
		message.putValue("active", active);
		notifyChange(message);
	}
}
