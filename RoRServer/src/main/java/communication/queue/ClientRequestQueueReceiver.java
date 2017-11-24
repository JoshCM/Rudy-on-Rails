package communication.queue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import models.DummyGame;

import org.apache.log4j.Logger;

// Allgemeine Queue für Clients, die ein Spiel oder Editor erstellen wollen
public class ClientRequestQueueReceiver extends QueueReceiver {

	static Logger log = Logger.getLogger(ClientRequestQueueReceiver.class.getName());
	private DummyGame game;

	public ClientRequestQueueReceiver(String queueName) {
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
			log.info("ClientRequestReceiver.onMessage(): ... Message received [" + new Date().toString() + "]: "
					+ textMessage.getText());
		} catch (JMSException e) {
			log.error(
					"ClientRequestQueueReceiver.onMessage(Message message) : QueueSender konnte Nachricht nicht verschicken");
			e.printStackTrace();
		}

	}
}
