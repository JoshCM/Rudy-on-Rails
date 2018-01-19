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
    	messageInfo.putValue("playerName", getSessionName());
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
	}

	public int getGoldCount() {
		return goldCount;
	}

	public void addGold(int gold) {
		this.goldCount += gold;
	}
	
	public int getPointCount() {
		return pointCount;
	}

	public void addPoint(int point) {
		this.pointCount += point;
	}

}
