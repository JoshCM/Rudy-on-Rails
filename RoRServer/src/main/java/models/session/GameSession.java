package models.session;

import java.util.ArrayList;
import java.util.UUID;
import communication.MessageInformation;
import communication.dispatcher.GameSessionDispatcher;
import communication.queue.receiver.QueueReceiver;
import models.game.Loco;
import models.game.TickableGameObject;

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
	private ArrayList<Loco> locomotives = new ArrayList<>();
	private String mapName;

	public GameSession(String name, UUID hostPlayerId, String hostPlayerName) {
		super(name, hostPlayerId, hostPlayerName);
		GameSessionDispatcher dispatcher = new GameSessionDispatcher(this);
		this.queueReceiver = new QueueReceiver(name, dispatcher);
		this.ticker = new Ticker();
		this.stopped = false;
		this.startTicking();
	}
	
	/**
	 * startet den Thread der f�r das Ticking verantwortlich ist 
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
	 * stoppt den TickingThread
	 */
	public void stop() {
		this.stopped = true;
	}
	

	/**
	 * F�gt dem Ticker eine Collection von TickableGameObjects hinzu
	 * @param tgos
	 */
	public void addAll(TickableGameObject...tgos ) {
		for(TickableGameObject tgo : tgos) {
			ticker.addObserver(tgo);
		}
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
		for (Loco loc : locomotives) {
			if (loc.getPlayerId().toString().equals(playerId.toString())) {
				return loc;
			}
		}
		return null;
	}

	/**
	 * Fügt Locotmotive der ArrayList hinzu
	 * @param locomotive
	 */
	public void addLocomotive(Loco locomotive) {

		if(locomotive != null) {
			this.locomotives.add(locomotive);
			ticker.addObserver(locomotive);
		}
	}
	
	public void startGame() {
		started = true;
		MessageInformation messageInfo = new MessageInformation("StartGame");
		notifyChange(messageInfo);
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
}









