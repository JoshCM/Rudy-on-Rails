package models.session;

import communication.dispatcher.GameSessionDispatcher;
import communication.queue.receiver.QueueReceiver;

public class GameSession extends RoRSession{
	public GameSession(String name) {
		super(name);
		
		GameSessionDispatcher dispatcher = new GameSessionDispatcher(this);
		this.queueReceiver = new QueueReceiver(name, dispatcher);
	}
}
