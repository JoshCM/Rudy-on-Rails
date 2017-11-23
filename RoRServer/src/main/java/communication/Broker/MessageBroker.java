package communication.Broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.log4j.Logger;

import communication.queue.ClientRequestQueueReceiver;
import communication.queue.QueueReceiver;

// Singleton
public class MessageBroker {
	
	private static MessageBroker messageBroker = null;	
	private BrokerService broker = null;
	static Logger log = Logger.getLogger(MessageBroker.class.getName());
	private static String clientRequestQueueName = "ClientRequestQueue";


	private MessageBroker() {
		broker = new BrokerService();
	}
	
	public static MessageBroker getInstance() {
		if (messageBroker == null) {
			messageBroker = new MessageBroker();
			messageBroker.startBroker();
			
			QueueReceiver clientRequestQueue = new ClientRequestQueueReceiver(clientRequestQueueName); 
		}
		
		return messageBroker;	
	}
	
	public void startBroker() {
		try {
			broker.addConnector("tcp://0.0.0.0:61616");
			broker.start();
			log.info("MessageBroker.startBroker(): tcp://0.0.0.0:61616");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getClientRequestQueueName() {
		return clientRequestQueueName;
	}

	public void setClientRequestQueueName(String clientRequestQueueName) {
		this.clientRequestQueueName = clientRequestQueueName;
	}
}
