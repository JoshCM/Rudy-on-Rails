package models.game;

import org.python.util.PythonInterpreter;

public class ScriptableObject {
	private PythonInterpreter pi = new PythonInterpreter();
	private ProxyObject proxyObject;
	private Thread updateThread;
	private boolean initialized;
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
	 * Startet den Update-Thread einmalig
	 * Notwendig, damit man GhostLoco Unit-testen kann
	 */
	private void init() {
		if (!initialized && !currentScriptFilename.isEmpty()) {
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
					callUpdateOnPythonScript();

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

	/**
	 * Ruft die update-Methode des aktuellen Python-Scripts auf, sofern der letzte
	 * Aufruf dieser Methode bereits abgearbeitet ist
	 */
	private void callUpdateOnPythonScript() {
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
	 * @param currentScriptName
	 */
	public void changeCurrentScriptName(String currentScriptName) {
		this.currentScriptFilename = currentScriptName;

		// Falls gerade noch ein anderes Script läuft, töte es!
		if (updateMethodThread != null && updateMethodThread.isAlive()) {
			try {
				updateMethodThread.interrupt();
				updateMethodThread = null;
			} catch (Exception e) {
			}
		}

		importCurrentScript();
		init();
	}
}
