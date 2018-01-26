package models.game;

import communication.MessageInformation;

/**
 * Die Signals werden in der Rail verwendet und stellen die vier Signale (Norden, Osten, Westen, Süden) dar.
 * 
 * Ein Signal kann aktiv (z.B. northSignalActive == true) sein, dies würde dann bedeuten, dass das Signal 
 * grün leuchtet. 
 * 
 * Da wir zurzeit nur auf Kreuzungen Signale haben ist die Logik an manchen stellen stark auf diesen 
 * Anwendungsfall reduziert, um die Logik überschaubar zu halten.
 */
public class Signals extends TickableGameObject {
	private final static int DEFAULT_AUTO_SWITCH_INTERVAL_IN_SECONDS = 5;
	private final static int DEFAULT_PENALTY = 5;
	private final static int DEFAULT_SWITCH_COST = 5;

	private int squarePosX;
	private int squarePosY;

	private int autoSwitchIntervalInSeconds = DEFAULT_AUTO_SWITCH_INTERVAL_IN_SECONDS;
	private int penalty = DEFAULT_PENALTY;
	private int switchCost = DEFAULT_SWITCH_COST;

	// Active bedeutet ein rotes Signal, also Strafe beim drüberfahren
	private boolean northSignalActive;
	private boolean eastSignalActive;
	private boolean southSignalActive;
	private boolean westSignalActive;
	
	private boolean notManuallySwitchableTillNextAutomaticSwitch;
	
	private long timeDeltaCounter = 0;

	public Signals(String sessionName, Square square) {
		super(sessionName, square);
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
		
		notManuallySwitchableTillNextAutomaticSwitch = false;
		timeDeltaCounter = 0;
		notifySignalsSwitched();
	};
	
	/**
	 * Schaltet die Signale um, sofern sie nicht zuletzt von einem Spieler manuell umgestellt wurden
	 */
	public void switchSignalsManually() {
		if(!notManuallySwitchableTillNextAutomaticSwitch) {
			switchSignals();
			notManuallySwitchableTillNextAutomaticSwitch = true;
		}
	}
	
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

	@Override
	public void specificUpdate() {
		this.timeDeltaCounter += timeDeltaInNanoSeconds;
		if (this.timeDeltaCounter >= SEC_IN_NANO * autoSwitchIntervalInSeconds) {
			switchSignals();
		}
	}

	public void changeConfig(int autoSwitchIntervalInSeconds, int penalty, int switchCost) {
		this.autoSwitchIntervalInSeconds = autoSwitchIntervalInSeconds;
		this.penalty = penalty;
		this.switchCost = switchCost;
		
		notifyConfigChanged();
	}
	
	private void notifyConfigChanged() {
		MessageInformation messageInfo = new MessageInformation("UpdateConfigOfSignals");
		messageInfo.putValue("signalsId", getId());
		messageInfo.putValue("xPos", squarePosX);
		messageInfo.putValue("yPos", squarePosY);
		messageInfo.putValue("autoSwitchIntervalInSeconds", autoSwitchIntervalInSeconds);
		messageInfo.putValue("penalty", penalty);
		messageInfo.putValue("switchCost", switchCost);

		notifyChange(messageInfo);
	};
	
	public boolean isSignalActive(Compass compass) {
		switch(compass) {
		case NORTH:
			return isNorthSignalActive();
		case EAST:
			return isEastSignalActive();
		case SOUTH:
			return isSouthSignalActive();
		case WEST:
			return isWestSignalActive();
		default:
			return false;
		}
	}

	public void handleLoco(Loco loco) {
		if (isSignalActive(loco.getDrivingDirection())) {
			loco.getPlayer().removeGold(penalty);
		}
	}
	
	public boolean isNotManuallySwitchableTillNextAutomaticSwitch() {
		return notManuallySwitchableTillNextAutomaticSwitch;
	}
}
