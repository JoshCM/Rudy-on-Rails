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
	private boolean picksUpGoldContainerNextToRails = true;
	private boolean picksUpCoalContainerNextToRails = true;

	public GhostLoco(String sessionName, Square square, UUID playerId, Compass drivingDirection) {
		super(sessionName, square, playerId, drivingDirection);

		ProxyObject ghostLocoProxy = new GhostLocoProxy(this);
		scriptableObject = new ScriptableObject(ghostLocoProxy);
		GameSessionManager.getInstance().getGameSessionByName(sessionName).addScriptableObject(scriptableObject);

		NotifyLocoCreated();
		addCart();
		addCart();
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

	public void changeCurrentScriptFilename(String currentScriptName) {
		scriptableObject.changeCurrentScriptFilename(currentScriptName);
	}

	@Override
	public void specificUpdate() {
		// Das eigentliche Fahren ist in der Oberklasse Loco geregelt
		super.specificUpdate();

		if (picksUpGoldContainerNextToRails || picksUpCoalContainerNextToRails) {
			handleResourcePickUps();
		}
	}

	private void handleResourcePickUps() {
		for (Cart cart : getCarts()) {
			if (cart.getResource() == null) {
				Resource resourceRight = cart.getResourceNextToCart(true);
				Resource resourceLeft = cart.getResourceNextToCart(false);
				boolean done = false;

				if (picksUpGoldContainerNextToRails) {
					if (resourceRight != null) {
						if (resourceRight instanceof Gold) {
							loadResourceOnCartAndRemoveItFromSquare(cart, resourceRight);
							done = true;
							break;
						}
					} else if (resourceLeft != null) {
						if (resourceLeft instanceof Gold) {
							loadResourceOnCartAndRemoveItFromSquare(cart, resourceLeft);
							done = true;
							break;
						}
					}
				}
				if (!done && picksUpCoalContainerNextToRails) {
					if (resourceRight != null) {
						if (resourceRight instanceof Coal) {
							loadResourceOnCartAndRemoveItFromSquare(cart, resourceRight);
							done = true;
							break;
						}
					} else if (resourceLeft != null) {
						if (resourceLeft instanceof Coal) {
							loadResourceOnCartAndRemoveItFromSquare(cart, resourceLeft);
							done = true;
							break;
						}
					}
				}
			}	
		}
	}
	
	private void loadResourceOnCartAndRemoveItFromSquare(Cart cart, Resource resource) {
		cart.loadResourceOntoCart(resource);
		Square square = map.getSquareById(resource.getSquareId());
		square.deletePlaceable();
	}
}