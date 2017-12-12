package communication.topic;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import communication.MessageEnvelope;
import communication.MessageInformation;
import communication.ServerConnection;
import communication.dispatcher.RequestSerializer;

/**
 * Über den TopicSender können Nachrichten an einen Topic gesendet werden mithilfe eines MessageTypes und einem 
 * MessageInformation-Objekt
 */
public class TopicSender {
	private Session session;
	private MessageProducer publisher;
	
	public TopicSender() {

	}
	
	public void sendMessage(MessageEnvelope messageEnvelope) {
		try {
			RequestSerializer requestSerializer = RequestSerializer.getInstance();
			String content = requestSerializer.serialize(messageEnvelope.getMessageInformation());
			TextMessage textMessage = session.createTextMessage(content);
			textMessage.setJMSType(messageEnvelope.getMessageType());
			Topic topic = session.createTopic(messageEnvelope.getTopicName());
			publisher = session.createProducer(topic);
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			publisher.send(topic, textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void setup() {
		try {
			session = ServerConnection.getInstance().getSession();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
