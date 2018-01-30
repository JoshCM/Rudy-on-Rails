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
		scripts.add(new Script(getSessionName(), "Default (schnell)", ScriptType.GHOSTLOCO, "ghostloco_default_drivefast"));
		scripts.add(new Script(getSessionName(), "Default (langsam)", ScriptType.GHOSTLOCO, "ghostloco_default_driveslow"));
		scripts.add(new Script(getSessionName(), "Nur Gold stehlen", ScriptType.GHOSTLOCO, "ghostloco_default_stealgold"));
		scripts.add(new Script(getSessionName(), "Nichts machen", ScriptType.GHOSTLOCO, "ghostloco_default_doNothing"));
	}
	
	private void loadSensorDefaultScripts() {
		scripts.add(new Script(getSessionName(), "Loco anhalten", ScriptType.SENSOR, "stop"));
		scripts.add(new Script(getSessionName(), "Weiche umstellen", ScriptType.SENSOR, "switch"));
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
