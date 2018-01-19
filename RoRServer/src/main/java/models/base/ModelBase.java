package models.base;

import java.util.UUID;
import communication.MessageEnvelope;
import communication.MessageInformation;
import communication.topic.TopicMessageQueue;

/**
 * Base-Klasse für alle Models, damit alle Models eine Id und die zugehörige
 * RoRSession kennen Die RoRSession wird aktuell gebraucht, damit die Models
 * Änderungen an den Client geben können über die Methode addMessage()
 */
public abstract class ModelBase extends ObservableModel implements Model {

	private UUID id;
	public String sessionName;

	public ModelBase(String sessionName) {
		this.addObserver(TopicMessageQueue.getInstance());
		this.id = UUID.randomUUID();
		this.sessionName = sessionName;
	}

	// Konstruktur um eine vordefinierte UUID zu setzen
	public ModelBase(String sessionName, UUID id) {
		this.addObserver(TopicMessageQueue.getInstance());
		this.id = id;
		this.sessionName = sessionName;
	}

	protected void setId(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	/**
	 * Hier werden Nachrichten hinzugefuegt, die an die verbundenen Clients
	 * geschickt werden sollen
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
