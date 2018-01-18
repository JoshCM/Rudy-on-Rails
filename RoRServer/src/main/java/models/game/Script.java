package models.game;

import communication.MessageInformation;
import models.base.ModelBase;

public class Script extends ModelBase {
	private String name;
	private String scriptName;

	public Script(String sessionName, String name, String scriptName) {
		super(sessionName);
		this.name = name;
		this.scriptName = scriptName;
		notifyScriptCreated();
	}

	public String getSessionName() {
		return name;
	}

	public String getScriptName() {
		return scriptName;
	}
	
	private void notifyScriptCreated() {
		MessageInformation messageInfo = new MessageInformation("CreateGhostLocoScript");
		messageInfo.putValue("id", getId());
		messageInfo.putValue("name", name);
		messageInfo.putValue("scriptName", scriptName);
		notifyChange(messageInfo);
	}
}
