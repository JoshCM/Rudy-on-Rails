package models.session;

import communication.dispatcher.GameSessionDispatcher;
import communication.queue.receiver.QueueReceiver;
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
	
	public GameSession(String name) {
		super(name);
		
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
	 * stoppt den TickingThread
	 */
	public void stop() {
		this.stopped = true;
	}
	
	/**
	 * Fügt dem Ticker neue TickableGameObject hinzu
	 * @param tgo
	 */
	public void add(TickableGameObject tgo) {
		ticker.addObserver(tgo);
	}
	
	/**
	 * Fügt dem Ticker eine Collection von TickableGameObjects hinzu
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
	
}








