package communication.topic;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

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
	private Topic topic;
	private String topicName;
	
	public TopicSender(String topicName) {
		this.topicName = topicName;
	}
	
	public void sendMessage(String messageType, MessageInformation messageInformation) {
		try {
			RequestSerializer requestSerializer = RequestSerializer.getInstance();
			String content = requestSerializer.serialize(messageInformation);
			TextMessage textMessage = session.createTextMessage(content);
			textMessage.setJMSType(messageType);
			publisher.send(textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void setup() {
		try {
			session = ServerConnection.getInstance().getSession();
			topic = session.createTopic(topicName);
			publisher = session.createProducer(topic);
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
