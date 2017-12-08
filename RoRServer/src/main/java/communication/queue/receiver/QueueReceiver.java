package communication.queue.receiver;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import communication.dispatcher.DispatcherBase;
import communication.queue.QueueBase;
import org.apache.log4j.Logger;

/**
 * Hier werden Nachrichten aus einer (ActiveMQ)Queue entgegengenommen und an den entsprechenden Dispatcher weitergeleitet
 */
public class QueueReceiver extends QueueBase implements MessageListener {
	
	static Logger log = Logger.getLogger(QueueReceiver.class.getName());
	
	private MessageConsumer consumer;
	private DispatcherBase dispatcher;

	public QueueReceiver(String queueName, DispatcherBase dispatcher) {
		super.queueName = queueName;
	}

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			String request = message.getJMSType();

			log.info("ClientRequestReceiver.onMessage(): ... Message received [" + new Date().toString() + "]: "
					+ textMessage.getText());
			dispatcher.dispatch(request, textMessage.getText());
		} catch (JMSException e) {
			log.error("FromClientRequestQueue.onMessage() : QueueSender konnte Nachricht nicht verschicken");
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
