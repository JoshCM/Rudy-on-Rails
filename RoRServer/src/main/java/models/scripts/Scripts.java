package models.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.base.ModelBase;
import models.scripts.Script.ScriptType;

public class Scripts extends ModelBase {
	private List<Script> ghostLocoScripts = new ArrayList<Script>();
	
	public Scripts(String sessionName) {
		super(sessionName);
	}
	
	public void init() {
		loadGhostLocoDefaultScripts();
	}
	
	private void loadGhostLocoDefaultScripts() {
		ghostLocoScripts.add(new Script(getDescription(), "Schnell fahren", ScriptType.GHOSTLOCO, "ghostloco_default_drivefast"));
		ghostLocoScripts.add(new Script(getDescription(), "Langsam fahren", ScriptType.GHOSTLOCO, "ghostloco_default_driveslow"));
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
		case GHOSTLOCO:
			ghostLocoScripts.add(script);
			break;
		default:
			break;
		}
	}
}
