package communication.queue.sender;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;
import communication.queue.QueueBase;

/**
 * Hiermit können Nachrichten an eine (ActiveMQ)Queue geschickt werden
 */
public class QueueSender extends QueueBase {

	private MessageProducer publisher;
	static Logger log = Logger.getLogger(QueueSender.class.getName());

	public QueueSender(String queueName) {
		super.queueName = queueName;
		this.setup();
	}

	public void setup() {
		super.setup();

		try {
			publisher = session.createProducer(queue);
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (JMSException e) {
			log.error("QueueSender.createQueue(): kann keinen Producer erstellen");
			e.printStackTrace();
		}
	}

	public void sendMessage(String messageType, String message) {
		try {
			TextMessage textMessage;
			textMessage = session.createTextMessage(message);
			textMessage.setJMSType(messageType);
			publisher.send(textMessage);
		} catch (JMSException e) {
			log.error("QueueSender.sendMessage(String message) : message : "+message);
			e.printStackTrace();
		}
	}
}
