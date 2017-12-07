package communication.queue.receiver;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import communication.dispatcher.FromClientRequestQueueDispatcher;

/**
 *  Allgemeine Queue für Clients, die eine GameSession oder EditorSession erstellen wollen
 */
public class FromClientRequestQueueReceiver extends QueueReceiver {
	private Logger log = Logger.getLogger(FromClientRequestQueueReceiver.class.getName());
	private FromClientRequestQueueDispatcher requestDispatcher;

	public FromClientRequestQueueReceiver(String queueName) {
		super(queueName);
		setup();
		requestDispatcher = new FromClientRequestQueueDispatcher();
	}

	@Override
	public void onMessage(Message message) {
		log.info("ClientRequestReceiver.onMessage(): Message incoming ...");
		// Hier wird der Queue-Name des Clients empfangen, um den Namen einer neuen
		// GameQueue zurück zuschicken
		System.out.println("Message incoming ...");

		TextMessage textMessage = (TextMessage) message;
		try {
			String request = message.getJMSType();

			log.info("ClientRequestReceiver.onMessage(): ... Message received [" + new Date().toString() + "]: "
					+ textMessage.getText());
			requestDispatcher.dispatch(request, textMessage.getText());
		} catch (JMSException e) {
			log.error("FromClientRequestQueue.onMessage() : QueueSender konnte Nachricht nicht verschicken");
			e.printStackTrace();
		}
	}
}
