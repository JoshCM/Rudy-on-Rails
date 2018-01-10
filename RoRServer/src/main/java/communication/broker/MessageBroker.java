package communication.broker;
import communication.queue.receiver.ReceiverQueue;
import communication.topic.SenderTopic;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;
import communication.dispatcher.FromClientRequestQueueDispatcher;
import resources.PropertyManager;

/**
 * Hier wird der BrokerService gestartet, an den sich die Clients verbinden können
 * Dazu wird hier der Receiver für die ClientRequestQueue gehalten
 */
public class MessageBroker {
	private static MessageBroker messageBroker = null;	
	private BrokerService broker = null;
	static Logger log = Logger.getLogger(MessageBroker.class.getName());
	private final static String CLIENT_REQUEST_QUEUE_NAME = PropertyManager.getProperty("client_request_queue_name");
	private ReceiverQueue fromClientRequestReceiverQueue;
	private SenderTopic toClientSenderTopic;

	private MessageBroker(){
		broker = new BrokerService();
	}

	public static MessageBroker getInstance() {
		if (messageBroker == null) {
			messageBroker = new MessageBroker();
			messageBroker.startBroker();
		}
		
		return messageBroker;	
	}
	
	public void startBroker() {
		try {
			broker.addConnector(PropertyManager.getProperty("broker_url"));
			broker.start();
			FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
			fromClientRequestReceiverQueue = new ReceiverQueue(CLIENT_REQUEST_QUEUE_NAME, dispatcher);
			fromClientRequestReceiverQueue.setup();
			log.info("MessageBroker.startBroker(): " + PropertyManager.getProperty("broker_url"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getClientRequestQueueName() {
		return CLIENT_REQUEST_QUEUE_NAME;
	}
}
