package models.game;

import models.base.ModelBase;

public class Script extends ModelBase {
	private String name;
	private String scriptName;

	public Script(String sessionName, String name, String scriptName) {
		super(sessionName);
		this.name = name;
		this.scriptName = scriptName;
	}

	public String getSessionName() {
		return name;
	}

	public String getScriptName() {
		return scriptName;
	}
}
