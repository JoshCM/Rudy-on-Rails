package launcher;

import communication.broker.MessageBroker;
import communication.queue.QueueMessageQueue;
import communication.topic.TopicMessageQueue;

/**
 * Der Launcher startet den BrokerService und somit die gesamte Anwendung
 */
public class Launcher {
	public static void main(String[] args) {
		MessageBroker.getInstance();
		TopicMessageQueue.getInstance().setup();
		QueueMessageQueue.getInstance().setup();
	}
}