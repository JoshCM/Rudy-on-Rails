package communication.queue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import communication.helper.Command;
import communication.helper.CommandEnum;
import communication.helper.Serializer;
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
		Command command = null;
		try {
			log.info("ClientRequestReceiver.onMessage(): ... Message received [" + new Date().toString() + "]: " + textMessage.getText()); 
			
			// Command aus der Textnachricht bekommen (deserialize)
			command = Serializer.deserialize(textMessage.getText());
		} catch (JMSException e) {
			log.error("ClientRequestQueueReceiver.onMessage(Message message) : QueueSender konnte Nachricht nicht verschicken"); 
			e.printStackTrace();
		}

		// vorerst abfrage was für ein command es ist
		// je nachdem soll anders gehandelt werden
		
		switch (command.getcEnum()) {
		case CREATE:
			System.out.println("normalerweise createGame()");
			//newGame(command);
			break;
		default:
			System.out.println("weis nicht was ich tun soll");
			break;
		}
	}

	private void newGame(Command command) {
		// Name der Client Queue, neuen Sender erstellen und Verbindung mit Queue des
		// Clients herstellen
		String clientQueueName = command.getAttributes().get("id");
		QueueSender queueSender = createNewQueueSender(clientQueueName);

		// Neues Spiel mit Queue erstellen und QueueName an den Client schicken
		String gameName = "DummyGame1";
		game = new DummyGame(gameName);
		
		// Neues Command erzeugen
		Command responseCommand = new Command();
		// Attribut-Map erstellen, GameName setzen und in Command setzen
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("name", gameName);
		responseCommand.setAttributes(attributes);
		// CommandEnum für Command setzen
		responseCommand.setcEnum(CommandEnum.CREATE);
		System.out.println(Serializer.serialize(responseCommand));
		// Command serialisieren und ueber queueSender senden
		queueSender.sendMessage(Serializer.serialize(responseCommand));
		
	}
}
