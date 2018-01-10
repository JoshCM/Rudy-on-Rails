package communication.queue.sender;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import communication.MessageEnvelope;
import communication.ServerConnection;
import communication.dispatcher.RequestSerializer;

/**
 * Über den QueueSender können Nachrichten mit messageType und einer MessageInformation an eine (ActiveMQ)Queue gesendet werden.
 */
public class QueueSender {
	private Session session;
	private MessageProducer publisher;
	static Logger log = Logger.getLogger(QueueSender.class.getName());

	public QueueSender() {
		this.setup();
	}

	public void sendMessage(MessageEnvelope messageEnvelope) {
		try {
			RequestSerializer requestSerializer = RequestSerializer.getInstance();
			String content = requestSerializer.serialize(messageEnvelope.getMessageInformation());
			TextMessage textMessage;
			textMessage = session.createTextMessage(content);
			textMessage.setJMSType(messageEnvelope.getMessageType());
			Queue queue = session.createQueue(messageEnvelope.getDestinationName());
			publisher = session.createProducer(queue);
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			publisher.send(queue, textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void setup() {
		try {
			session = ServerConnection.getInstance().getDefaultSession();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
