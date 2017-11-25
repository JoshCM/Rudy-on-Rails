package communication.queue;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import models.DummyGame;

import org.apache.log4j.Logger;

import communication.session.SessionTopicSender;
import requestHandler.RequestHandler;

// Allgemeine Queue für Clients, die ein Spiel oder Editor erstellen wollen
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
			String command = message.getJMSType();
			log.info("ClientRequestReceiver.onMessage(): ... Message received [" + new Date().toString() + "]: "
					+ textMessage.getText());
			RequestHandler requestHandler = RequestHandler.getInstance();
			requestHandler.handleRequest(command, textMessage.getText());
			QueueSender sender = new QueueSender(textMessage.getText());
			String tT = "testTopic";
			SessionTopicSender testTopic = new SessionTopicSender(tT);
			sender.sendMessage(tT);
			testTopic.sendMessage("es hat hoffentlich geklappt!");
		} catch (JMSException e) {
			log.error(
					"FromClientRequestQueue.onMessage(Message message) : QueueSender konnte Nachricht nicht verschicken");
			e.printStackTrace();
		}

	}
}
