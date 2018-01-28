package models.game;
import java.util.ArrayList;
import java.util.List;

import models.scripts.ProxyObject;
import models.session.GameSession;
import models.session.GameSessionManager;

public class SensorProxy implements ProxyObject {

	private Sensor sensor;
	private Square square;
	private Loco loco;
	private GameSession session;
	
	public SensorProxy (Sensor sensor, Square square) {
		this.sensor = sensor;
		this.square = square;
		this.session = GameSessionManager.getInstance().getGameSession();
	}
	
	public void setLoco(Loco loco) {
		this.loco = loco;
	}
	
	// Methoden für Script...
	public void funny() {
		System.out.println("Funny");
	}
	
	/**
	 * Stoppt die Loco
	 */
	public void stop() {
		loco.changeSpeed(0);
	}
	
	/**
	 * Crasht das Game
	 */
	public void destroyTrain() {
		System.out.println("Loco zerstört: " + loco.toString());
		List<Loco> locos = GameSessionManager.getInstance().getGameSession().getLocos();
		locos.remove(loco);
	}
	
	/**
	 * Gibt den aktuellen Zug aus, der darüber fährt
	 */
	public void info() {
		System.out.println("Zug angekommen: " + loco.toString());
	}
	
	/** 
	 * Der Zug dropt alle Ressourcen sofern Platz ist neben der Rail/Square
	 */
	public void dropResourcesFromTrain() {
		loco.dropResources();
	}
	
	/**
	 * Holt das Objekt auf dem Square und gibt es als String zurück
	 * @return Object als String
	 */
	public String getObjectOnSquare(){
		String object = new String();
		Placeable placeable = square.getPlaceableOnSquare();
		if (placeable instanceof Rail) {
			Rail rail = (Rail)placeable;
			if (rail.getSignals() != null) {
				object = "Signals";
			}
			if (rail instanceof Switch) {
				object = "Switch";
			}
		}
		System.out.println("OBJECT: " + object);
		return object;
	}
	
	/**
	 * Umschalten der Weiche, sofern vorhanden
	 */
	public void changeSwitch() {
		Placeable placeable = square.getPlaceableOnSquare();
		if (placeable instanceof Switch) {
			Switch changeSwitch = (Switch)placeable;
			changeSwitch.changeSwitch();
		}
	}
	
	/**
	 * Setzt die Kosten für das Umschalten der Signale
	 */
	public void changeSignalsCost(int cost) {
		
		if (cost >= 0 && cost <= 100) {
			Placeable placeable = square.getPlaceableOnSquare();
			if (placeable instanceof Rail) {
				Rail rail = (Rail)placeable;
				if (rail.getSignals() != null) {
					Signals signals = rail.getSignals();
					signals.changeSwitchCost(cost);
				}
			}
		}
	}
	
	/**
	 * Setzt die Zeit für das Umschalten der Signale
	 */
	public void changeSwitchInterval(int switchInterval) {
		
		if (switchInterval >= 0 && switchInterval <= 20) {
			Placeable placeable = square.getPlaceableOnSquare();
			if (placeable instanceof Rail) {
				Rail rail = (Rail)placeable;
				if (rail.getSignals() != null) {
					Signals signals = rail.getSignals();
					signals.changeSwitchInterval(switchInterval);
				}
			}
		}
	}
	
	/**
	 * Setzt die Strafe für das Überfahren von Signalen
	 */
	public void changeSwitchPenalty(int penalty) {
		
		if (penalty >= 0 && penalty <= 100) {
			Placeable placeable = square.getPlaceableOnSquare();
			if (placeable instanceof Rail) {
				Rail rail = (Rail)placeable;
				if (rail.getSignals() != null) {
					Signals signals = rail.getSignals();
					signals.changePenalty(penalty);
				}
			}
		}
	}
	
	/**
	 * Umschalten der Signale, wenn vorhanden
	 */
	public void switchSignals() {
		Placeable placeable = square.getPlaceableOnSquare();
		if (placeable instanceof Rail) {
			Rail rail = (Rail)placeable;
			if (rail.getSignals() != null) {
				Signals signals = rail.getSignals();
				signals.switchSignals();
			}
		}
	}
}
