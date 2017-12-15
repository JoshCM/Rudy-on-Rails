package models.session;

import communication.dispatcher.GameSessionDispatcher;
import communication.queue.receiver.QueueReceiver;
import persistent.MapManager;

/**
 * Oberklasse vom Game-Modus. 
 * Haelt die Map und die Liste von verbundenen Playern
 * Erhaelt ueber einen QueueReceiver Anfragen von Clients, die mit der GameSession verbunden sind
 */
public class GameSession extends RoRSession{
	public GameSession(String name) {
		super(name);
		GameSessionDispatcher dispatcher = new GameSessionDispatcher(this);
		this.queueReceiver = new QueueReceiver(name, dispatcher);
		// DefaultMap laden
		loadGameDefaultMap();
	}
	
	/**
	 * Lädt die DefaultMap in die GameSession
	 */
	public void loadGameDefaultMap() {
		setMap(MapManager.loadMap("/maps/GameDefaultMap.map"));
	}
}
