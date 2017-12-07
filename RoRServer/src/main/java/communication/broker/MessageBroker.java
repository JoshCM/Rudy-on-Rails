package communication.broker;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;
import communication.queue.receiver.FromClientRequestQueueReceiver;
import resources.PropertyManager;


// Singleton
public class MessageBroker {
	private static MessageBroker messageBroker = null;	
	private BrokerService broker = null;
	static Logger log = Logger.getLogger(MessageBroker.class.getName());
	private static String CLIENT_REQUEST_QUEUE_NAME = "ClientRequestQueue";
	private FromClientRequestQueueReceiver fromClientRequestQueueReceiver;

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
			fromClientRequestQueueReceiver = new FromClientRequestQueueReceiver(CLIENT_REQUEST_QUEUE_NAME);
			log.info("MessageBroker.startBroker(): " + PropertyManager.getProperty("broker_url"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getClientRequestQueueName() {
		return CLIENT_REQUEST_QUEUE_NAME;
	}
}
