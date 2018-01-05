package models.game;

import communication.MessageInformation;
import models.base.ModelBase;

public class Signals extends ModelBase {
	private final static int DEFAULT_AUTO_SWITCH_INTERVAL_IN_SECONDS = 30;
	private final static int DEFAULT_PENALTY = 5;
	private final static int DEFAULT_SWITCH_COST = 5;

	private int squarePosX;
	private int squarePosY;

	private int autoSwitchIntervalInSeconds = DEFAULT_AUTO_SWITCH_INTERVAL_IN_SECONDS;
	private int penalty = DEFAULT_PENALTY;
	private int switchCost = DEFAULT_SWITCH_COST;

	private boolean northSignalActive;
	private boolean eastSignalActive;
	private boolean southSignalActive;
	private boolean westSignalActive;

	public Signals(String sessionName, Square square) {
		super(sessionName);
		this.squarePosX = square.getXIndex();
		this.squarePosY = square.getYIndex();

		// Startpositionen für Kreuzungen
		northSignalActive = true;
		southSignalActive = true;

		notifySignalsCreated();
	};

	private void notifySignalsCreated() {
		MessageInformation messageInfo = new MessageInformation("CreateSignals");
		messageInfo.putValue("signalsId", getId());
		messageInfo.putValue("xPos", squarePosX);
		messageInfo.putValue("yPos", squarePosY);
		messageInfo.putValue("autoSwitchIntervalInSeconds", autoSwitchIntervalInSeconds);
		messageInfo.putValue("penalty", penalty);
		messageInfo.putValue("switchCost", switchCost);
		messageInfo.putValue("northSignalActive", northSignalActive);
		messageInfo.putValue("eastSignalActive", eastSignalActive);
		messageInfo.putValue("southSignalActive", southSignalActive);
		messageInfo.putValue("westSignalActive", westSignalActive);

		notifyChange(messageInfo);
	}

	/**
	 * Hier werden die Signale umgeschaltet. Zurzeit ist die Logik nur für
	 * Kreuzungen implementiert Wenn vorher NORTH und SOUTH aktiv waren, dann sind
	 * nach dem switch EAST und WEST aktiv
	 */
	public void switchSignals() {
		if (northSignalActive && southSignalActive) {
			northSignalActive = false;
			southSignalActive = false;
			eastSignalActive = true;
			westSignalActive = true;
		} else {
			northSignalActive = true;
			southSignalActive = true;
			eastSignalActive = false;
			westSignalActive = false;
		}
		
		notifySignalsSwitched();
	};
	
	private void notifySignalsSwitched() {
		MessageInformation messageInfo = new MessageInformation("UpdateActivityOfSignals");
		messageInfo.putValue("signalsId", getId());
		messageInfo.putValue("xPos", squarePosX);
		messageInfo.putValue("yPos", squarePosY);
		messageInfo.putValue("northSignalActive", northSignalActive);
		messageInfo.putValue("eastSignalActive", eastSignalActive);
		messageInfo.putValue("southSignalActive", southSignalActive);
		messageInfo.putValue("westSignalActive", westSignalActive);

		notifyChange(messageInfo);
	};

	public int getSquarePosX() {
		return squarePosX;
	}

	public int getSquarePosY() {
		return squarePosY;
	}

	public int getAutoSwitchIntervalInSeconds() {
		return autoSwitchIntervalInSeconds;
	}

	public int getPenalty() {
		return penalty;
	}

	public int getSwitchCost() {
		return switchCost;
	}

	public boolean isNorthSignalActive() {
		return northSignalActive;
	}

	public boolean isEastSignalActive() {
		return eastSignalActive;
	}

	public boolean isSouthSignalActive() {
		return southSignalActive;
	}

	public boolean isWestSignalActive() {
		return westSignalActive;
	}
}
