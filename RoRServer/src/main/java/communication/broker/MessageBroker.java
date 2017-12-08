package communication.broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;
import communication.dispatcher.FromClientRequestQueueDispatcher;
import communication.queue.receiver.QueueReceiver;
import resources.PropertyManager;


// Singleton
public class MessageBroker {
	private static MessageBroker messageBroker = null;	
	private BrokerService broker = null;
	static Logger log = Logger.getLogger(MessageBroker.class.getName());
	private static String CLIENT_REQUEST_QUEUE_NAME = "ClientRequestQueue";
	private QueueReceiver fromClientRequestQueueReceiver;

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
			fromClientRequestQueueReceiver = new QueueReceiver(CLIENT_REQUEST_QUEUE_NAME, dispatcher);
			log.info("MessageBroker.startBroker(): " + PropertyManager.getProperty("broker_url"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getClientRequestQueueName() {
		return CLIENT_REQUEST_QUEUE_NAME;
	}
}
