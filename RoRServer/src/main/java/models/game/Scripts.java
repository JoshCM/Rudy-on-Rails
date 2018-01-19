package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.base.ModelBase;

public class Scripts extends ModelBase {
	private List<Script> ghostLocoScripts = new ArrayList<Script>();
	
	public Scripts(String sessionName) {
		super(sessionName);
	}
	
	public void loadGhostLocoDefaultScripts() {
		ghostLocoScripts.add(new Script(getSessionName(), "Schnell fahren", "ghostloco", "ghostloco_default_drivefast"));
		ghostLocoScripts.add(new Script(getSessionName(), "Langsam fahren", "ghostloco", "ghostloco_default_driveslow"));
	}
	
	public List<Script> getGhostLocoScripts() {
		return ghostLocoScripts;
	}
	
	public Script getGhostLocoScriptForId(UUID scriptId) {
		Script script = ghostLocoScripts.stream().filter(x -> x.getId().equals(scriptId)).findFirst().get();
		return script;
	}
	
	public void addScript(Script script) {
		switch(script.getScriptType()) {
		case "ghostloco":
			ghostLocoScripts.add(script);
			break;
		default:
			break;
		}
	}
}
