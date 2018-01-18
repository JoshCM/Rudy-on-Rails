package models.game;

import java.util.ArrayList;
import java.util.List;

import models.base.ModelBase;

public class Scripts extends ModelBase {
	private List<Script> ghostLocoScripts = new ArrayList<Script>();
	
	public Scripts(String sessionName) {
		super(sessionName);
		loadGhostLocoDefaultScripts();
	}
	
	private void loadGhostLocoDefaultScripts() {
		ghostLocoScripts.add(new Script(getSessionName(), "Schnell fahren", "ghostloco_default_drivefast"));
		ghostLocoScripts.add(new Script(getSessionName(), "Langsam fahren", "ghostloco_default_driveslow"));
	}
	
	public List<Script> getGhostLocoScripts() {
		return ghostLocoScripts;
	}
}
