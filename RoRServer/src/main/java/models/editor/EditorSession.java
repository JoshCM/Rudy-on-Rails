package models.editor;

import models.game.Map;
import models.game.Player;
import communication.queue.receiver.FromClientRequestsEditorQueueReceiver;
import communication.topic.TopicSender;
import communication.MessageInformation;
import communication.dispatcher.RequestSerializer;

import java.util.ArrayList;

public class EditorSession extends RoRSession {
	private ArrayList<Player> players = new ArrayList<>();
	private Map map;
	
	private FromClientRequestsEditorQueueReceiver queueReceiver;

	public EditorSession(String name) {
		super(name);
		queueReceiver = new FromClientRequestsEditorQueueReceiver(name, this);
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
