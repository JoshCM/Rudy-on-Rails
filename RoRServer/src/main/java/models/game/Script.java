package models.game;

import java.util.UUID;

import communication.MessageInformation;
import models.base.ModelBase;

public class Script extends ModelBase {
	private String name;
	private String scriptName;
	private UUID playerId;
	
	public Script(String sessionName, String name, String scriptName, UUID playerId) {
		super(sessionName);
		this.playerId = playerId;
		this.name = name;
		this.scriptName = scriptName;
		notifyScriptCreated();
	}

	public Script(String sessionName, String name, String scriptName) {
		this(sessionName, name, scriptName, UUID.fromString("00000000-0000-0000-0000-000000000000"));
	}

	public String getSessionName() {
		return name;
	}

	public String getScriptName() {
		return scriptName;
	}
	
	public UUID getPlayerId() {
		return playerId;
	}
	
	private void notifyScriptCreated() {
		MessageInformation messageInfo = new MessageInformation("CreateGhostLocoScript");
		messageInfo.putValue("id", getId());
		messageInfo.putValue("playerId", playerId);
		messageInfo.putValue("name", name);
		messageInfo.putValue("scriptName", scriptName);
		notifyChange(messageInfo);
	}
}
