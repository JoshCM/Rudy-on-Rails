package models.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import communication.queue.receiver.QueueReceiver;
import models.game.Map;
import models.game.Player;

/**
 * Oberklasse von EditorSession und GameSession
 * 
 */
public abstract class RoRSession {
	private String name;
	private ArrayList<Player> players = new ArrayList<>();
	private Map map;
	
	protected QueueReceiver queueReceiver;
	
	public RoRSession(String name) {
		this.name = name;
		map = new Map(name);
	}
	
	public String getName() {
		return name;
	}
		
	public void setup() {
		queueReceiver.setup();
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public void removePlayer(Player player) {
		this.players.remove(player);
	}

	public Map getMap() {
		return map;
	}

	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}
}
