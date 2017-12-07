package models.base;

import java.util.UUID;

import communication.MessageInformation;
import models.editor.RoRSession;

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
	
	protected void addMessage(MessageInformation messageInformation) {
		roRSession.addMessage(messageInformation);
	}
	
	public RoRSession getRoRSession() {
		return roRSession;
	}
}
