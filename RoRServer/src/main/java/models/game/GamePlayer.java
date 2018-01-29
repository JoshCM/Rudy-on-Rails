package models.game;

import java.util.UUID;

import communication.MessageInformation;
import resources.PropertyManager;

public class GamePlayer extends Player{

	private double coalCount;
	private int goldCount;
	private int pointCount;
	private Color color;
	private Publictrainstation currentSelectedPublictrainstation;
	public static int START_GOLD = 20;
	public static int START_COAL = 250;
	public static int START_POINTS = 0;

	public GamePlayer(String sessionName, String name, UUID id, boolean isHost) {
		super(sessionName, name, id, isHost);
		initializeResourceCounts();
		notifyCreated();
	}
	
	private void notifyCreated() {
    	MessageInformation messageInfo = new MessageInformation("CreatePlayer");
    	messageInfo.putValue("playerId", getId());
    	messageInfo.putValue("playerName", getDescription());
    	messageInfo.putValue("isHost", getIsHost());
    	messageInfo.putValue("coalCount", getCoalCount());
    	messageInfo.putValue("goldCount", getGoldCount());
    	messageInfo.putValue("pointCount", getPointCount());
    	notifyChange(messageInfo);
    }
	
	public void setCurrentSelectedPublictrainstation (Publictrainstation currentSelectedPublictrainstation) {
		this.currentSelectedPublictrainstation = currentSelectedPublictrainstation;
	}
	
	public Publictrainstation getCurrentSelectedPublictrainstation() {
		return currentSelectedPublictrainstation;
	}

	private void initializeResourceCounts() {
		this.coalCount = Integer.valueOf(PropertyManager.getProperty("initial_coal_count"));
		this.goldCount = Integer.valueOf(PropertyManager.getProperty("initial_gold_count"));
		this.pointCount = Integer.valueOf(PropertyManager.getProperty("initial_point_count"));;
	}

	public double getCoalCount() {
		return coalCount;
	}

	public void addCoal(int coal) {
		this.coalCount += coal;
		notifyResourceCountChanged();
	}
	
	public void removeCoal(double coal) {
		if(coal > coalCount) {
			coalCount = 0;
		} else {
			coalCount -= coal;
		}
		notifyResourceCountChanged();
	}

	public int getGoldCount() {
		return goldCount;
	}

	public void addGold(int gold) {
		this.goldCount += gold;
		notifyResourceCountChanged();
	}
	
	public void removeGold(int gold) {
		if(gold > goldCount) {
			goldCount = 0;
		} else {
			goldCount -= gold;
		}
		notifyResourceCountChanged();
	}
	public int getPointCount() {
		return pointCount;
	}
	
	public void addPoints(int points) {
		this.pointCount += points;
		notifyResourceCountChanged();
	}
	
	public void removePoints(int points) {
		if(points > pointCount) {
			pointCount = 0;
		} else {
			pointCount -= points;
		}
		notifyResourceCountChanged();
	}
	
	private void notifyResourceCountChanged() {
    	MessageInformation messageInfo = new MessageInformation("UpdateResourcesOfPlayer");
    	messageInfo.putValue("playerId", getId());
    	messageInfo.putValue("coalCount", getCoalCount());
    	messageInfo.putValue("goldCount", getGoldCount());
    	messageInfo.putValue("pointCount", getPointCount());
    	notifyChange(messageInfo);
    }
	
	private void notifyColorChanged() {
    	MessageInformation messageInfo = new MessageInformation("UpdateColorOfPlayer");
    	messageInfo.putValue("playerId", getId());
    	messageInfo.putValue("color", getColor().ordinal());
    	notifyChange(messageInfo);
    }

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		notifyColorChanged();
	}
}
