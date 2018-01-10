package communication.topic;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;

import communication.MessageEnvelope;
import communication.ServerConnection;
import communication.dispatcher.RequestSerializer;

/**
 * Über den SenderTopic können Nachrichten an einen Topic gesendet werden mithilfe eines MessageTypes und einem
 * MessageInformation-Objekt
 */
public class SenderTopic {
	private String topicname;
	private MessageProducer publisher;
	
	public SenderTopic() {

	}

	public SenderTopic(String sessionName) {
		this.topicname = "TOPIC"+sessionName;
	}
	
	public void sendMessage(MessageEnvelope messageEnvelope) {
		try {
			RequestSerializer requestSerializer = RequestSerializer.getInstance();
			String content = requestSerializer.serialize(messageEnvelope.getMessageInformation());
			TextMessage textMessage = session.createTextMessage(content);
			textMessage.setJMSType(messageEnvelope.getMessageType());
			Topic topic = session.createTopic(messageEnvelope.getDestinationName());
			publisher = session.createProducer(topic);
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			publisher.send(topic, textMessage);
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
