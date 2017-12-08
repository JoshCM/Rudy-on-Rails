package models.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import communication.dispatcher.EditorSessionDispatcher;
import communication.queue.receiver.QueueReceiver;
import models.game.Map;
import models.game.Player;

/**
 * Oberklasse vom Editor-Modus. 
 * Hält die Map und die Liste von verbundenen Playern
 * Erhält über einen QueueReceiver Anfragen von Clients, die mit der EditorSession verbunden sind
 */
public class EditorSession extends RoRSession {
	private ArrayList<Player> players = new ArrayList<>();
	private Map map;

	public EditorSession(String name) {
		super(name);
		
		EditorSessionDispatcher dispatcher = new EditorSessionDispatcher(this);
		this.queueReceiver = new QueueReceiver(name, dispatcher);
		map = new Map(this);
	}
	
	public void setup() {
		super.setup();
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
