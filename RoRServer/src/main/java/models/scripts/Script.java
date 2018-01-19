package models.scripts;

import java.util.UUID;

import communication.MessageInformation;
import models.base.ModelBase;

public class Script extends ModelBase {
	public enum ScriptType {
		GHOSTLOCO
	}
	
	private String description;
	private ScriptType scriptType;
	private String filename;
	private UUID playerId;
	
	public Script(String sessionName, String description, ScriptType scriptType, String filename, UUID playerId) {
		super(sessionName);
		this.playerId = playerId;
		this.description = description;
		this.filename = filename;
		this.scriptType = scriptType;
		notifyScriptCreated();
	}

	public Script(String sessionName, String description, ScriptType scriptType, String scriptName) {
		this(sessionName, description, scriptType, scriptName, UUID.fromString("00000000-0000-0000-0000-000000000000"));
	}

	public String getDescription() {
		return description;
	}

	public String getFilename() {
		return filename;
	}
	
	public ScriptType getScriptType() {
		return scriptType;
	}
	
	public UUID getPlayerId() {
		return playerId;
	}
	
	private void notifyScriptCreated() {
		MessageInformation messageInfo = new MessageInformation("CreateScript");
		messageInfo.putValue("id", getId());
		messageInfo.putValue("playerId", playerId);
		messageInfo.putValue("description", description);
		messageInfo.putValue("scriptType", scriptType.toString());
		messageInfo.putValue("filename", filename);
		notifyChange(messageInfo);
	}
}
