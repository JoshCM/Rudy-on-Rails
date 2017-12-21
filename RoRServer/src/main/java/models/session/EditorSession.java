package models.session;

import java.util.UUID;

import communication.dispatcher.EditorSessionDispatcher;
import communication.queue.receiver.QueueReceiver;

/**
 * Oberklasse vom Editor-Modus. 
 * Hält die Map und die Liste von verbundenen Playern
 * Erhält über einen QueueReceiver Anfragen von Clients, die mit der EditorSession verbunden sind
 */
public class EditorSession extends RoRSession {
	public EditorSession(String name, UUID hostPlayerId, String hostPlayerName) {
		super(name, hostPlayerId, hostPlayerName);
	
		EditorSessionDispatcher dispatcher = new EditorSessionDispatcher(this);
		this.queueReceiver = new QueueReceiver(name, dispatcher);
	}
}
