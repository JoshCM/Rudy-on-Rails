package models.editor;

import java.util.ArrayList;
import communication.dispatcher.EditorSessionDispatcher;
import communication.queue.receiver.QueueReceiver;
import models.game.Map;
import models.game.Player;

public class EditorSession extends RoRSession {
	private ArrayList<Player> players = new ArrayList<>();
	private Map map;
	
	private QueueReceiver queueReceiver;

	public EditorSession(String name) {
		super(name);
		
		EditorSessionDispatcher dispatcher = new EditorSessionDispatcher(this);
		queueReceiver = new QueueReceiver(name, dispatcher);
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

	// ToDo: Unmodifiable List zurückgeben
	public ArrayList<Player> getPlayers() {
		return players;
	}
}
