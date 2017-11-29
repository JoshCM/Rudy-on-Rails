package communication.session;
import javax.jms.Message;

import communication.queue.receiver.QueueReceiver;
import models.Game.DummyGame;

// Queue für das Spiel/Editor
public class SessionQueueReceiver extends QueueReceiver {
	
	DummyGame game;

	public SessionQueueReceiver(String queueName) {
		super(queueName);
	}
	
	@Override
	public void onMessage(Message message) {
		super.onMessage(message);
		game.sendAction();
	}
	
	public void setGame(DummyGame game) {
		this.game = game;
	}

}
