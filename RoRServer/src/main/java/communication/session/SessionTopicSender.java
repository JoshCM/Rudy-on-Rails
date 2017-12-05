package communication.session;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import communication.connection.ServerConnection;

public class SessionTopicSender {

	private Session session;
	private MessageProducer publisher;
	private Topic topic;
	private String topicName;
	
	public SessionTopicSender(String topicName) {
		this.topicName = topicName;
		createTopic();
	}
	
	public void sendMessage(String message) {
		try {
			TextMessage textMessage;
			textMessage = session.createTextMessage(message);
			publisher.send(textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void createTopic() {
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
