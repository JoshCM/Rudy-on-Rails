package models.scripts;

import java.util.ArrayList;
import java.util.List;

public class ScriptableObjectManager {
	private Thread updateThread;
	private List<ScriptableObject> scriptableObjects = new ArrayList<ScriptableObject>();
	private boolean initialized;
	
	public void addScriptableObject(ScriptableObject scriptableObject) {
		scriptableObjects.add(scriptableObject);
	}
	
	/**
	 * Startet den Update-Thread einmalig
	 * Notwendig, damit man GhostLoco Unit-testen kann
	 */
	public void init() {
		if (!initialized) {
			initialized = true;
			startUpdateThread();
		}
	}

	/**
	 * Startet den Thread, der in regelmäßigen Abständen die update-Methode des
	 * aktuellen Python-Scripts aufruft
	 */
	private void startUpdateThread() {
		updateThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					for(ScriptableObject scriptableObject : scriptableObjects) {	
						if(!scriptableObject.getCurrentScriptFilename().isEmpty()) {							
							scriptableObject.callUpdateOnPythonScript();
						}
					}

					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		updateThread.start();
	}
}
