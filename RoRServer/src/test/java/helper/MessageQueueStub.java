package helper;

import java.util.ArrayList;
import java.util.List;
import communication.MessageEnvelope;
import models.base.ModelObserver;
import models.base.InterActiveGameModel;

public class MessageQueueStub implements ModelObserver {
	public List<MessageEnvelope> messages = new ArrayList<MessageEnvelope>();
	
	@Override
	public void update(InterActiveGameModel observable, Object arg) {
		MessageEnvelope messageEnvelope = (MessageEnvelope)arg;
		messages.add(messageEnvelope);
	}
}