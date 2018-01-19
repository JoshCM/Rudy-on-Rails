package models.game;

import java.util.UUID;

import org.python.util.PythonInterpreter;

import communication.MessageInformation;

public class GhostLoco extends Loco {
	private PythonInterpreter pi = new PythonInterpreter();
	private GhostLocoProxy ghostLocoProxy;
	private Thread updateThread;
	private boolean initialized;
	private String currentScriptName = "";
	
	public GhostLoco(String sessionName, Square square, UUID playerId) {
		super(sessionName, square, playerId);

		ghostLocoProxy = new GhostLocoProxy(this);
		NotifyLocoCreated();
		addInitialCart();
	}
	
	private void importCurrentScript() {
		if(!currentScriptName.isEmpty()) {
	        pi.exec("from " + currentScriptName + " import update");
	        pi.set("proxy", ghostLocoProxy);
		}
	}
	
	private void init() {
		if(!initialized && !currentScriptName.isEmpty()) {
			initialized = true;
			startUpdateThread();
		}
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

	private void NotifyLocoCreated() {
		MessageInformation messageInfo = new MessageInformation("CreateGhostLoco");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("drivingDirection", getDrivingDirection().toString());
		messageInfo.putValue("playerId", getPlayerId());
		notifyChange(messageInfo);
	}
	
	public void changeCurrentScriptName(String currentScriptName) {
		this.currentScriptName = currentScriptName;
		importCurrentScript();
		init();
	}
}
