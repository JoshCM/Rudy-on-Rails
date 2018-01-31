package models.game;

import java.util.UUID;

import communication.MessageInformation;
import models.scripts.ScriptableObject;
import models.session.GameSessionManager;
import resources.PropertyManager;

/**
 * Model für Sensor
 * @author Andreas Pöhler
 *
 */
public class Sensor extends InteractiveGameObject {
	
	public final static int DISTANCE_BETWEEN_LOCO_AND_SENSOR = Integer.valueOf(PropertyManager.getProperty("distance_between_loco_and_sensor"));
	public final static int DEFAULT_ACTIME_TIME = Integer.valueOf(PropertyManager.getProperty("default_actime_time"));
	public final static int DEFAULT_DELETE_TIME = Integer.valueOf(PropertyManager.getProperty("default_delete_time"));
	public final static int SENSOR_COST = Integer.valueOf(PropertyManager.getProperty("sensor_cost"));
	public final static int SECOND = 1000;
	
	private UUID railId;
	private UUID playerId;
	private boolean active;
	private boolean wasActive;
	private ScriptableObject scriptableObject;
	private String currentScriptName;
	private SensorProxy sensorProxy;
	private int activeTime = DEFAULT_ACTIME_TIME;

	public Sensor(String sessionName, Square square, UUID railId, UUID playerId) {
		super(sessionName, square);
		this.railId = railId;
		this.playerId = playerId;
		this.active = false;
		this.wasActive = false;
		
		// Vorbereitung für Skripting
		sensorProxy = new SensorProxy(this, square);
		scriptableObject = new ScriptableObject(sensorProxy);
		
		notifySensorPlaced();		
	}
	
	public UUID getPlayerId() {
		return playerId;
	}
	
	/**
	 * Schickt eine Nachricht an den Client, um den Sensor auf einer Rail zu platzieren
	 */
	private void notifySensorPlaced() {
		MessageInformation message = new MessageInformation("PlaceSensor");
		message.putValue("railId", railId);
		message.putValue("playerId", playerId);
		notifyChange(message);		
	}
	
	/**
	 * Aktiviert den Sensor, sendet eine Nachricht an den Client und startet 
	 * den Countdown (in der Zeit ist der Sensor aktiv)
	 */
	private void activate() {
		active = true;
		notifySwitchSensor();
		startCountDownForActiveSensor();
	}
	
	/**
	 * Wird ausgeführt, sobald der Countdown für den aktiven Sensor abgelaufen ist: Deaktiviert den Sensor, 
	 * sendet eine Nachricht an den Client und startet einen Countdown zum Löschen den Sensors
	 */
	private void deactivateAndDelete() {
		active = false;
		wasActive = true;
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
	
	/**
	 * Prüft, ob eine gegebene Position mit der des Sensors übereinstimmt
	 * @param x: x-Koordinate
	 * @param y: y-Koordinate
	 * @return Gibt true zurück, wenn die Position übereinstimmen, false sonst
	 */
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
		message.putValue("wasActive", wasActive);
		notifyChange(message);
	}
	
	/**
	 * Schickt eine Nachricht an den Client, um den Sensor zu löschen
	 */
	private void notifyDeleteSensor() {
		MessageInformation message = new MessageInformation("DeleteSensor");
		message.putValue("railId", railId);
		message.putValue("active", active);
		message.putValue("wasActive", wasActive);
		notifyChange(message);
	}
}
