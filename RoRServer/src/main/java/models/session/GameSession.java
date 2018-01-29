package models.session;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import communication.MessageInformation;
import communication.dispatcher.GameSessionDispatcher;
import communication.queue.receiver.QueueReceiver;
import models.base.ModelObserver;
import models.base.ObservableModel;
import models.game.GamePlayer;
import models.game.GhostLoco;
import models.game.Loco;
import models.game.Mine;
import models.game.PlayerLoco;
import models.game.Publictrainstation;
import models.game.Player;
import models.game.TickableGameObject;
import models.scripts.ScriptableObject;
import models.scripts.ScriptableObjectManager;
import models.scripts.Scripts;
import persistent.MapManager;
import resources.PropertyManager;

/**
 * Oberklasse vom Game-Modus. 
 * Haelt die Map und die Liste von verbundenen Playern
 * Erhaelt ueber einen QueueReceiver Anfragen von Clients, die mit der GameSession verbunden sind
 */
public class GameSession extends RoRSession implements ModelObserver {
	private final static int POINTS_TO_WIN = Integer.valueOf(PropertyManager.getProperty("points_to_win"));
	private final static int TIME_BETWEEN_TICKS_IN_MILLISECONDS = 100;
	
	private Thread tickingThread;
	private boolean stopped;
	private long lastTimeUpdatedInNanoSeconds;
	private Ticker ticker;
	private ArrayList<Mine> mines=new ArrayList<>();
	private ArrayList<Loco> locos = new ArrayList<>();
	private Scripts scripts;
	private int availablePlayerSlots;
	private ScriptableObjectManager scriptableObjectManager;

	public GameSession(String name, UUID hostPlayerId, String hostPlayerName) {
		super(name);
		
		createHostPlayer(hostPlayerId, hostPlayerName);
		
		GameSessionDispatcher dispatcher = new GameSessionDispatcher(this);
		scripts = new Scripts(name);
		scriptableObjectManager = new ScriptableObjectManager();
		this.queueReceiver = new QueueReceiver(name, dispatcher);
		this.ticker = new Ticker();
		this.stopped = false;
		this.startTicking();
	}
	
	private void createHostPlayer(UUID playerId, String playerName) {
		GamePlayer player = new GamePlayer(getSessionName(), playerName, playerId, true);
		addPlayer(player);
		player.addObserver(this);
	}
	
	public Player createPlayer(UUID playerId, String playerName) {
		GamePlayer player = new GamePlayer(getSessionName(), playerName, playerId, false);
		addPlayer(player);
		player.addObserver(this);
		return player;
	}
	
	/**
	 * startet den Thread der für das Ticking verantwortlich ist
	 * und ruft die tick()-Methode der Ticker-Klasse auf
	 */
	private void startTicking() {
		tickingThread = new Thread() {
			@Override
			public void run() {
				while(!stopped) {
					if(lastTimeUpdatedInNanoSeconds != 0)
						ticker.tick(System.nanoTime() - lastTimeUpdatedInNanoSeconds);
					lastTimeUpdatedInNanoSeconds = System.nanoTime();
					try {
						Thread.sleep(TIME_BETWEEN_TICKS_IN_MILLISECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		tickingThread.start();
		
	}
	
	/**
	 * Prüft, ob das Spiel die maximale Anzahl an verfügbaren Spielerslots erreicht wurde
	 * @return
	 */
	public boolean isFull() {
		boolean isFull = false;
		
		if (MapManager.getAvailablePlayerSlotsByMapName(getMapName()) == getPlayers().size()) {
			isFull = true;
		}
		return isFull;
	}
	
	/**
	 * stoppt den TickingThread
	 */
	public void stop() {
		this.stopped = true;
		queueReceiver.stop();
	}
	

	/**
	 * Fügt dem Ticker eine Collection von TickableGameObjects hinzu
	 * @param tgos
	 */
	public void registerTickableGameObject(TickableGameObject tickableGameObject) {
		ticker.addObserver(tickableGameObject);
	}
	
	/**
	 * Entfernt ein TickableGameObject  aus der Liste der Ticker-Observer
	 * @param tgo
	 */
	public void remove(TickableGameObject tgo) {
		ticker.deleteObserver(tgo);
	}

	/**
	 * Sucht Locomotive über die playerId heraus, null falls playerId nicht vorhanden
	 * @param playerId
	 * @return
	 */
	public PlayerLoco getPlayerLocoByPlayerId(UUID playerId) {
		for (Loco loc : locos) {
			if (loc.getPlayerId().toString().equals(playerId.toString()) && loc instanceof PlayerLoco) {
				return (PlayerLoco)loc;
			}
		}
		return null;
	}
	
	/**
	 * @param playerId
	 * @return Die GhostLoco des Spielers mit der hereingereichten playerId
	 */
	public GhostLoco getGhostLocoByPlayerId(UUID playerId) {
		for (Loco loco : locos) {
			if (loco.getPlayerId().equals(playerId) && loco instanceof GhostLoco) {
				return (GhostLoco)loco;
			}
		}
		return null;
	}

	/**
	 * Fügt Locotmotive der ArrayList hinzu
	 * @param loco
	 */
	public void addLoco(Loco loco) {
		if(loco != null) {
			locos.add(loco);
			ticker.addObserver(loco);
		}
	}
	
	@Override
	protected void notifyPlayerLeft(Player player) {
		MessageInformation message = new MessageInformation("LeaveGame");
		message.putValue("playerId", player.getId());
		notifyChange(message);
	}
	
	
	public void addMine(Mine mine) {
		if(mine!=null) {
			this.mines.add(mine);
			ticker.addObserver(mine);
		}
		
		
	}
	
	public List<Loco> getLocos() {
		return locos;
	}
	
	public Scripts getScripts() {
		return scripts;
	}
	
	@Override
	public void start() {
		super.start();
		scripts.init();
		scriptableObjectManager.init();
	}
	
	public void addScriptableObject(ScriptableObject scriptableObject) {
		scriptableObjectManager.addScriptableObject(scriptableObject);
	}

	public int getAvailablePlayerSlots() {
		return availablePlayerSlots;
	}

	public void setAvailablePlayerSlots(int availablePlayerSlots) {
		this.availablePlayerSlots = availablePlayerSlots;
	}

	@Override
	public void update(ObservableModel observable, Object arg) {
		if(observable instanceof GamePlayer) {
			GamePlayer player = (GamePlayer)observable;
			
			if(player.getPointCount() >= POINTS_TO_WIN){
				endGame(player);
			}
		}
	}

	private void endGame(GamePlayer winningPlayer) {
		MessageInformation messageInfo = new MessageInformation("EndGame");
		messageInfo.putValue("winningPlayerId", winningPlayer.getId());
		notifyChange(messageInfo);
	}
}









