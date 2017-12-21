package models.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import communication.MessageInformation;
import communication.queue.receiver.QueueReceiver;
import communication.topic.MessageQueue;
import models.base.ModelBase;
import models.base.ObservableModel;
import models.game.Map;
import models.game.Player;
import persistent.MapManager;

/**
 * Oberklasse von EditorSession und GameSession
 * 
 */
public abstract class RoRSession extends ModelBase {
	private ArrayList<Player> players = new ArrayList<>();
	private Map map;
	protected boolean started;
	
	protected QueueReceiver queueReceiver;
	
	public RoRSession(String name) {
		super(name);
		map = new Map(name);
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
	
	public Player getHost() {
		return players.stream().filter(x -> x.getIsHost()).findFirst().get();
	}

	public Map getMap() {
		return map;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}

	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}
	
	public boolean isStarted() {
		return started;
	}
}
