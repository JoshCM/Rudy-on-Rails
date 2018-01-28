package models.scripts;

import org.python.core.PyException;
import org.python.util.PythonInterpreter;
import communication.dispatcher.FromClientRequestQueueDispatcher;

public class ScriptableObject {
	private PythonInterpreter pi = new PythonInterpreter();
	private ProxyObject proxyObject;
	private String currentScriptFilename = "";
	private Thread updateMethodThread;
	private boolean currentScriptIsValid;
	
	public ScriptableObject(ProxyObject proxyObject) {
		this.proxyObject = proxyObject;
	}
	
	/**
	 * Lädt die update-Methode des aktuellen Scripts in den Python-Interpreter
	 */
	private void importCurrentScript() {
		if (!currentScriptFilename.isEmpty()) {
			try {
				pi.exec("from " + currentScriptFilename + " import update");
				pi.exec("from " + currentScriptFilename + " import init");
				pi.set("proxy", proxyObject);
				currentScriptIsValid = true;
			} catch(PyException e) {
				e.printStackTrace();
				currentScriptIsValid = false;
			}
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
	
	public void callInitOnPythonScript() {
		if(currentScriptIsValid) {
			pi.exec("init(proxy)");
		}
	}
	
	private void initAndStartUpdateMethodThread() {
		updateMethodThread = new Thread(new Runnable() {
			@Override
			public void run() {
				if(currentScriptIsValid) {
					pi.exec("update(proxy)");
				}
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
		callInitOnPythonScript();
	}
	
	public String getCurrentScriptFilename() {
		return currentScriptFilename;
	}
}
