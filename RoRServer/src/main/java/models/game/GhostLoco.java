package models.game;

import java.util.UUID;
import communication.MessageInformation;
import models.scripts.ProxyObject;
import models.scripts.ScriptableObject;
import models.session.GameSessionManager;

/**
 * Dem Geisterzug kann ein Script hinterlegt werden, dass ihn steuert
 */
public class GhostLoco extends Loco {
	ScriptableObject scriptableObject;

	public GhostLoco(String sessionName, Square square, UUID playerId, Compass drivingDirection) {
		super(sessionName, square, playerId, drivingDirection);

		ProxyObject ghostLocoProxy = new GhostLocoProxy(this);
		scriptableObject = new ScriptableObject(ghostLocoProxy);
		GameSessionManager.getInstance().getGameSessionByName(sessionName).addScriptableObject(scriptableObject);
		
		NotifyLocoCreated();
		addInitialCart();
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
		scriptableObject.changeCurrentScriptName(currentScriptName);
	}
}
