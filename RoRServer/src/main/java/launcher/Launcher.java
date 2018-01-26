package launcher;

import org.apache.log4j.Logger;

import communication.broker.MessageBroker;
import communication.queue.QueueMessageQueue;
import communication.topic.TopicMessageQueue;
import persistent.MapManager;

/**
 * Der Launcher startet den BrokerService und somit die gesamte Anwendung
 */
public class Launcher {
	static Logger log = Logger.getLogger(MessageBroker.class.getName());
	public static void main(String[] args) {
		MessageBroker.start();
		TopicMessageQueue.getInstance().setup();
		QueueMessageQueue.getInstance().setup();
		MapManager.initAvailablePlayerSlots();
		log.info("MapManager: initAvailablePlayerSlots - finished");
	}
}