package models.scripts;

import org.python.util.PythonInterpreter;

public class ScriptableObject {
	private PythonInterpreter pi = new PythonInterpreter();
	private ProxyObject proxyObject;
	private String currentScriptFilename = "";
	private Thread updateMethodThread;
	
	public ScriptableObject(ProxyObject proxyObject) {
		this.proxyObject = proxyObject;
	}
	
	/**
	 * Lädt die update-Methode des aktuellen Scripts in den Python-Interpreter
	 */
	private void importCurrentScript() {
		if (!currentScriptFilename.isEmpty()) {
			pi.exec("from " + currentScriptFilename + " import update");
			pi.set("proxy", proxyObject);
		}
	}

	/**
	 * Ruft die update-Methode des aktuellen Python-Scripts auf, sofern der letzte
	 * Aufruf dieser Methode bereits abgearbeitet ist
	 */
	public void callUpdateOnPythonScript() {
		if (updateMethodThread == null) {
			initAndStartUpdateMethodThread();
		} else {
			if(!updateMethodThread.isAlive()) {
				initAndStartUpdateMethodThread();
			}
		}
	}
	
	private void initAndStartUpdateMethodThread() {
		updateMethodThread = new Thread(new Runnable() {
			@Override
			public void run() {
				pi.exec("update(proxy)");
			}
		});
		updateMethodThread.start();
	}

	/**
	 * Ändert den filename des aktuellen Scripts. Dieses wird über Jython direkt
	 * gestartet.
	 * 
	 * @param currentScriptFilename
	 */
	public void changeCurrentScriptFilename(String currentScriptFilename) {
		this.currentScriptFilename = currentScriptFilename;

		// Falls gerade noch ein anderes Script läuft, töte es!
		if (updateMethodThread != null && updateMethodThread.isAlive()) {
			try {
				updateMethodThread.interrupt();
				updateMethodThread = null;
			} catch (Exception e) {
			}
		}

		importCurrentScript();
	}
	
	public String getCurrentScriptFilename() {
		return currentScriptFilename;
	}
}
