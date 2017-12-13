package models.base;

import java.util.Observable;
import java.util.UUID;

import communication.MessageEnvelope;
import communication.MessageInformation;
import communication.topic.MessageQueue;
import models.session.RoRSession;

/**
 * Base-Klasse für alle Models, damit alle Models eine Id und die zugehörige
 * RoRSession kennen Die RoRSession wird aktuell gebraucht, damit die Models
 * Änderungen an den Client geben können über die Methode addMessage()
 */
public abstract class ModelBase extends Observable implements Model{
	private UUID id;
	private String sessionName;

	public ModelBase(String sessionName) {
		this.addObserver(MessageQueue.getInstance());
		this.id = UUID.randomUUID();
		this.sessionName = sessionName;
	}

	public UUID getId() {
		return id;
	}

	/**
	 * Hier werden Nachrichten hinzugefügt, die an die verbundenen Clients geschickt
	 * werden sollen
	 * 
	 * @param messageInformation
	 */
	protected void notifyChange(MessageInformation messageInformation) {
		MessageEnvelope messageEnvelope = new MessageEnvelope(sessionName, messageInformation.getMessageType(),
				messageInformation);
		
		setChanged();
		notifyObservers(messageEnvelope);
	}
}
