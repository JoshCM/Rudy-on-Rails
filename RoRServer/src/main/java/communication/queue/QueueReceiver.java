package communication.queue;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import communication.helper.Command;
import communication.helper.Serializer;

import java.util.Date;


// Receiver für Client Requests und Spiel/Editor
public abstract class QueueReceiver extends QueueBase implements MessageListener {
	
	static Logger log = Logger.getLogger(QueueReceiver.class.getName());
	private MessageConsumer consumer;

	public QueueReceiver(String queueName) {
		super.queueName = queueName;
		this.createQueue();
	}
	

	@Override
	public void onMessage(Message message) {
		System.out.println("Message incoming ...");
		TextMessage textMessage = (TextMessage)message;
		Command command = null;
		try {
			command = Serializer.deserialize(textMessage.getText());
			System.out.println(command);
		} catch (JMSException e) {
			e.printStackTrace();
		}		
	}
	
	protected void createQueue() {
		
		super.createQueue();
		
		try {
			consumer = session.createConsumer(queue);
			consumer.setMessageListener(this);
			
			System.out.println("Waiting for Messages on Queue " + queueName + " :");
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public QueueSender createNewQueueSender(String queueName) {
		return new QueueSender(queueName);
	}
	
	

}
