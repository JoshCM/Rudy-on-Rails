package communication.queue.sender;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import communication.queue.QueueBase;
import models.dataTranserObject.MessageType;

import org.apache.log4j.Logger;

// QueueSender für Client Queues
public class QueueSender extends QueueBase {

	private MessageProducer publisher;
	static Logger log = Logger.getLogger(QueueSender.class.getName());

	public QueueSender(String queueName) {
		super.queueName = queueName;
		this.createQueue();
	}

	protected void createQueue() {

		super.createQueue();

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
