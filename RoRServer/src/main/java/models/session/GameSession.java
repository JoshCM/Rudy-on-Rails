package models.session;

import communication.dispatcher.GameSessionDispatcher;
import communication.queue.receiver.QueueReceiver;
import models.game.TickableGameObject;

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
	
	public void stop() {
		this.stopped = true;
	}
	
	public void add(TickableGameObject tgo) {
		ticker.addObserver(tgo);
	}
	
	public void addAll(TickableGameObject...tgos ) {
		for(TickableGameObject tgo : tgos) {
			ticker.addObserver(tgo);
		}
	}
	
	public void remove(TickableGameObject tgo) {
		ticker.deleteObserver(tgo);
	}
	
}








