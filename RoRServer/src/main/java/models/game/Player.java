package models.game;

import communication.MessageInformation;
import models.base.ModelBase;


public class Player extends ModelBase {
    private String playerName;
    private boolean isHost;

    public Player(String playerName, boolean isHost) {
        this.playerName = playerName;
        this.isHost = isHost;
        notifyCreated();
    }

    public Player(String playerName) {
        this(playerName, false);
    }

    public Player() {
        this("DEFAULTNAME");
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean getIsHost() {
        return isHost;
    }


    // TODO: Serialisierung vereinheitlichen.
    private void notifyCreated() {
        MessageInformation messageInfo = new MessageInformation("CreatePlayer");
        messageInfo.putValue("playerId", getID());
        messageInfo.putValue("playerName", playerName);
        messageInfo.putValue("isHost", isHost);
        notifyObservers();
        //notifyChange(messageInfo);
    }
}
