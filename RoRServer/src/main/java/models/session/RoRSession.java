package models.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import communication.dispatcher.SessionDispatcher;
import communication.queue.receiver.ReceiverQueue;
import communication.topic.SenderTopic;
import exceptions.SessionException;
import models.base.ModelBase;
import models.game.Map;
import models.game.Player;
import states.RoRState;

//TODO: States einpflegen und überprüfen.
/**
 * Oberklasse von EditorSession und GameSession
 */
public abstract class RoRSession extends ModelBase implements javax.jms.Session {
    ArrayList<Player> playerList;
    Map map;
    ReceiverQueue receiverQueue;
    SenderTopic senderTopic;
    Player hostPlayer;
    String sessionName;
    SessionDispatcher sessionDispatcher;

    public RoRSession(String sessionName, Map map, Player hostPlayer) {
        this.map = map;
        this.playerList = new ArrayList<>();
        this.hostPlayer = hostPlayer;
        this.sessionName = sessionName;
        playerList.add(hostPlayer);
        // queue erstellen + mittteilen
        // topic erstellen + mitteilen
        setState(RoRState.READYTOSTART);
    }

    public RoRSession(Map map, Player hostPlayer) {
        this(UUID.randomUUID().toString(), map, hostPlayer);
    }

    public RoRSession(String sessionName, Map map) {
        this(sessionName, map, null);
    }

    public RoRSession(Map map) {
        this(null, map, null);
    }

    public RoRSession(String sessionName) {
        this(sessionName, null, null);
    }

    public Player createPlayer(UUID playerId, String playerName) {
        Player player = new Player(playerName, false);
        playerList.add(player);
        return player;
    }

    public void addPlayerToSession(Player player) {
        if (playerList.contains(player)) {
            throw new SessionException("Player already in Playerlist");
        } else {
            playerList.add(player);
        }
    }

    public void removePlayerFromSession(Player player) {
        if (playerList.contains(player)) {
            playerList.remove(player);
        } else {
            throw new SessionException("Player is not in Playerlist");
        }
    }

    public void setupQueueReceiver() {
        receiverQueue.setup();
    }

    public Player getHost() {
        return hostPlayer;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List<Player> getPlayerList() {
        return Collections.unmodifiableList(playerList);
    }


    public boolean getState(RoRState sessionState) {
        if (getState().equals(sessionState)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRunning() {
        if (getState() == RoRState.RUNNING) {
            return false;
        } else {
            return true;
        }
    }

    public String getName() {
        return sessionName;
    }
}
