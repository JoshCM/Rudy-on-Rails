package models.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import communication.MessageInformation;
import communication.queue.receiver.QueueReceiver;
import communication.topic.TopicMessageQueue;
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
	
	public RoRSession(String name, UUID hostPlayerId, String hostPlayerName) {
		super(name);
		map = new Map(name);
		createHostPlayer(hostPlayerId, hostPlayerName);
	}
	
	private void createHostPlayer(UUID playerId, String playerName) {
		Player player = new Player(getSessionName(), playerName, playerId, true);
		players.add(player);
	}
	
	public Player createPlayer(UUID playerId, String playerName) {
		Player player = new Player(getSessionName(), playerName, playerId, false);
		players.add(player);
		return player;
	}
		
	public void setup() {
		queueReceiver.setup();
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
	
	public void start() {
		started = true;
		MessageInformation messageInfo = new MessageInformation("StartGame");
		notifyChange(messageInfo);
	}
}
