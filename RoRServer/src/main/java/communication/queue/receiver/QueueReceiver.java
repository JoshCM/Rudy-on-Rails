package communication.queue.receiver;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import communication.queue.QueueBase;
import communication.queue.sender.QueueSender;
import org.apache.log4j.Logger;


// Receiver für Client Requests und Spiel/editor
public abstract class QueueReceiver extends QueueBase implements MessageListener {
	
	static Logger log = Logger.getLogger(QueueReceiver.class.getName());
	private MessageConsumer consumer;

	public QueueReceiver(String queueName) {
		super.queueName = queueName;
	}

	@Override
	public void onMessage(Message message) {
		System.out.println("Message incoming ...");
		TextMessage textMessage = (TextMessage)message;
	
		try {
			System.out.println(textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}		
	}
	
	public void setup() {
		super.setup();
		
		try {
			consumer = session.createConsumer(queue);
			consumer.setMessageListener(this);
			
			System.out.println("Waiting for Messages on Queue " + queueName + " :");
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
