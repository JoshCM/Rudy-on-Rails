package models.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.base.ModelBase;
import models.scripts.Script.ScriptType;

public class Scripts extends ModelBase {
	private List<Script> scripts = new ArrayList<Script>();
	
	public Scripts(String sessionName) {
		super(sessionName);
	}
	
	public void init() {
		loadGhostLocoDefaultScripts();
		loadSensorDefaultScripts();
	}
	
	private void loadGhostLocoDefaultScripts() {
		scripts.add(new Script(getDescription(), "Schnell fahren", ScriptType.GHOSTLOCO, "ghostloco_default_drivefast"));
		scripts.add(new Script(getDescription(), "Langsam fahren", ScriptType.GHOSTLOCO, "ghostloco_default_driveslow"));
	}
	
	private void loadSensorDefaultScripts() {
		scripts.add(new Script(getDescription(), "Funny", ScriptType.SENSOR, "funny"));
	}
	
	public List<Script> getScripts() {
		return scripts;
	}
	
	public Script getScriptForId(UUID scriptId) {
		Script script = scripts.stream().filter(x -> x.getId().equals(scriptId)).findFirst().get();
		return script;
	}
	
	public void addScript(Script script) {
		scripts.add(script);
	}
}
