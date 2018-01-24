package models.game;

import java.util.UUID;

import communication.MessageInformation;

public class GamePlayer extends Player{

	private int coalCount;
	private int goldCount;
	private int pointCount;

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

	private void initializeResourceCounts() {
		this.coalCount = 25;
		this.goldCount = 10;
		this.pointCount = 0;
	}

	public int getCoalCount() {
		return coalCount;
	}

	public void addCoal(int coal) {
		this.coalCount += coal;
		notifyResourceCountChanged();
	}
	
	public void removeCoal(int coal) {
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
}
