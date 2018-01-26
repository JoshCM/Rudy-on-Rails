package models.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import communication.MessageInformation;
import communication.queue.receiver.QueueReceiver;
import exceptions.MapNotFoundException;
import models.base.ModelBase;
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
	private String mapName;

	protected QueueReceiver queueReceiver;
	
	public RoRSession(String name) {
		super(name);
	}
		
	public void setup() {
		queueReceiver.setup();
	}

	public void removePlayer(Player player) {
		this.players.remove(player);
		notifyPlayerLeft(player);
	}

	public void removePlayers() {
		for (int i = getPlayers().size() - 1; i >= 0; i--) {
			Player player = getPlayers().get(i);
			removePlayer(player);
		}
	}

	protected abstract void notifyPlayerLeft(Player player);

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
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public Player getPlayerById(UUID id) {
		for (Player player : getPlayers()) {
			if (player.getId().equals(id))
				return player;
		}
		return null;
	}

	public boolean isStarted() {
		return started;
	}

	public void start() {
		started = true;
		MessageInformation messageInfo = new MessageInformation("Start");
		notifyChange(messageInfo);
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
		notifyChangedMapName();
	}

	/**
	 * Schickt eine Message mit dem neuen MapName, über den Topic der GameSession,
	 * an alle angemeldeten Clients 
	 */
	private void notifyChangedMapName() {
		MessageInformation messageInfo = new MessageInformation("ChangeMapSelection");
		messageInfo.putValue("mapName", getMapName());
		if(this instanceof GameSession) {
			GameSession gameSession = (GameSession) this;	
			int availablePlayers = MapManager.getAvailablePlayerSlots(getMapName());
			gameSession.setAvailablePlayerSlots(availablePlayers);
			messageInfo.putValue("availablePlayerSlots", gameSession.getAvailablePlayerSlots());
		
		}
		notifyChange(messageInfo);
	}
}
