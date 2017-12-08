package models.base;

import java.util.UUID;

import communication.MessageInformation;
import models.editor.RoRSession;

/**
 * Base-Klasse für alle Models, damit alle Models eine Id und die zugehörige RoRSession kennen
 */
public abstract class ModelBase implements Model {
	private UUID id;
	private RoRSession roRSession;
	
	public ModelBase(RoRSession session) {
		this.id = UUID.randomUUID();
		this.roRSession = session;
	}
	
	public UUID getId() {
		return id;
	}
	
	/**
	 * Hier werden Nachrichten hinzugefügt, die an die verbundenen Clients geschickt werden sollen
	 * @param messageInformation
	 */
	protected void addMessage(MessageInformation messageInformation) {
		roRSession.addMessage(messageInformation);
	}
	
	public RoRSession getRoRSession() {
		return roRSession;
	}
}
