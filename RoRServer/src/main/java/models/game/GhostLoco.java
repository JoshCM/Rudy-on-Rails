package models.game;

import java.util.UUID;

import org.python.util.PythonInterpreter;

public class GhostLoco extends Loco {
	private PythonInterpreter pi = new PythonInterpreter();
	private GhostLocoProxy ghostLocoProxy;
	private Thread updateThread;
	
	public GhostLoco(String sessionName, Square square, UUID playerId) {
		super(sessionName, square, playerId);
		initGhostLocoProxy();
		startUpdateThread();
	}
	
	private void initGhostLocoProxy() {
		ghostLocoProxy = new GhostLocoProxy(this);
        pi.exec("from ghostloco import update");
        pi.set("proxy", ghostLocoProxy);
        pi.exec("print 'hallo', proxy");
	}
	
	private void startUpdateThread() {
		updateThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					pi.exec("update(proxy)");
					
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
