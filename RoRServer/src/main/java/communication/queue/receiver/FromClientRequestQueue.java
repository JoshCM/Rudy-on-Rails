package communication.queue.receiver;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import communication.queue.sender.QueueSender;
import models.dataTranserObject.MessageType;
import models.game.DummyGame;

import org.apache.log4j.Logger;

import communication.session.SessionTopicSender;
import HandleRequests.RequestDispatcher;

// Allgemeine Queue für Clients, die ein Spiel oder editor erstellen wollen
public class FromClientRequestQueue extends QueueReceiver {

	static Logger log = Logger.getLogger(FromClientRequestQueue.class.getName());
	private DummyGame game;

	public FromClientRequestQueue(String queueName) {
		super(queueName);
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
			RequestDispatcher requestDispatcher = RequestDispatcher.getInstance();
			requestDispatcher.dispatch(request, textMessage.getText());
		} catch (JMSException e) {
			log.error(
					"FromClientRequestQueue.onMessage(Message message) : QueueSender konnte Nachricht nicht verschicken");
			e.printStackTrace();
		}
	}
}
