package models.session;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import communication.MessageInformation;
import communication.dispatcher.GameSessionDispatcher;
import communication.queue.receiver.QueueReceiver;
import models.game.GhostLoco;
import models.game.Loco;
import models.game.Player;
import models.game.PlayerLoco;
import models.game.TickableGameObject;
import models.game.Trainstation;

/**
 * Oberklasse vom Game-Modus. 
 * Haelt die Map und die Liste von verbundenen Playern
 * Erhaelt ueber einen QueueReceiver Anfragen von Clients, die mit der GameSession verbunden sind
 */
public class GameSession extends RoRSession{
	private final static int TIME_BETWEEN_TICKS_IN_MILLISECONDS = 100;
	
	private Thread tickingThread;
	private boolean stopped;
	private long lastTimeUpdatedInNanoSeconds;
	private Ticker ticker;
	private ArrayList<Loco> locos = new ArrayList<>();

	public GameSession(String name, UUID hostPlayerId, String hostPlayerName) {
		super(name, hostPlayerId, hostPlayerName);
		GameSessionDispatcher dispatcher = new GameSessionDispatcher(this);
		this.queueReceiver = new QueueReceiver(name, dispatcher);
		this.ticker = new Ticker();
		this.stopped = false;
		this.startTicking();
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
		if (getMap().getAvailablePlayerSlots() == getPlayers().size()) {
			isFull = true;
		}
		return isFull;
	}
	
	/**
	 * stoppt den TickingThread
	 */
	public void stop() {
		this.stopped = true;
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
	public Loco getLocomotiveByPlayerId(UUID playerId) {
		for (Loco loc : locos) {
			if (loc.getPlayerId().toString().equals(playerId.toString())) {
				return loc;
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

	public void removePlayers() {
		for(int i = getPlayers().size() - 1; i >= 0; i--) {
			Player player = getPlayers().get(i);
			removePlayer(player);
		}
	}
	
	public List<Loco> getLocos() {
		return locos;
	}
}









