package communication.queue.receiver;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import communication.queue.sender.FromServerResponseQueue;
import communication.queue.sender.QueueSender;
import models.Game.DummyGame;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import communication.session.SessionTopicSender;
import HandleRequests.RequestHandlerImpl;

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
			RequestHandlerImpl requestHandler = RequestHandlerImpl.getInstance();
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

	/**
	 * sucht nach dem passenden MessageType
	 * @param commandString welcher den JMSType als String angibt
	 * @return passender MessageType der dem commandString entspricht
	 */

	public MessageType findMessageType(String commandString) {
		MessageType command = null;
        switch(commandString) {
        case "CREATE":
            command = MessageType.CREATE;
            break;
        case "READ":
        	command = MessageType.READ;
            break;
        case "UPDATE":
        	command = MessageType.UPDATE;
            break;
        case "DELETE":
        	command = MessageType.DELETE;
            break;
        case "LEAVE":
        	command = MessageType.LEAVE;
            break;
        case "ERROR":
        	command = MessageType.ERROR;
            break;
        }

        return command;
	}
}
