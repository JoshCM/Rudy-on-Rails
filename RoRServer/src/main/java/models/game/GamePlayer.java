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
	
	private void notifyUpdated() {
		MessageInformation messageInfo = new MessageInformation("UpdatePlayer");
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

	public int getGoldCount() {
		return goldCount;
	}

	public int getPointCount() {
		return pointCount;
	}

	public void addCoal(int coal) {
		this.coalCount += coal;
		notifyUpdated();
	}

	public void addGold(int count) {
		this.goldCount += count;
		notifyUpdated();
	}
	
	public void removeGold(int count) {
		this.goldCount -= count;
		notifyUpdated();
	}
	
	public void addPoint(int point) {
		this.pointCount += point;
		notifyUpdated();
	}

}
