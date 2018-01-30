package models.game;

import java.util.UUID;

import communication.MessageInformation;
import resources.PropertyManager;

public class PlayerLoco extends Loco {
	private final double coalDecreaseFactor = Double.parseDouble(PropertyManager.getProperty("coal_decrease_factor"));
	public PlayerLoco(String sessionName, Square square, UUID playerId, Compass drivingDirection) {
		super(sessionName, square, playerId, drivingDirection);
		
		NotifyLocoCreated();
		addCart();
	}
	
	/**
	 * notifiziert wenn eine Lok erstellt wurde
	 */
	private void NotifyLocoCreated() {
		MessageInformation messageInfo = new MessageInformation("CreatePlayerLoco");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("drivingDirection", getDrivingDirection().toString());
		messageInfo.putValue("playerId", getPlayerId());
		notifyChange(messageInfo);
	}
	

	/**
	 * Reduzirt die Kohle des Spielers um das Produkt von speed und
	 * coalDecreaseFactor
	 */
	public void spendCoal() {
		double coalToDecrease = (double) getSpeed() * coalDecreaseFactor;
		coalToDecrease = Math.abs(coalToDecrease);
		getPlayer().removeCoal(coalToDecrease);
	}
	
	public boolean needsCoalToDrive() {
		return true;
	}
}
