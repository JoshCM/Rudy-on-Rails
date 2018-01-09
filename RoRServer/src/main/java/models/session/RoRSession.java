package models.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import communication.queue.receiver.QueueReceiver;
import exceptions.SessionException;
import models.base.ModelBase;
import models.game.Map;
import models.game.Player;
import states.RoRSessionState;
import states.SessionState;

//TODO: States einpflegen und überprüfen.
/**
 * Oberklasse von EditorSession und GameSession
 */
public abstract class RoRSession extends ModelBase implements RoRSessionState {
    private ArrayList<Player> playerList;
    private Map map;
    QueueReceiver queueReceiver;
    private Player hostPlayer;
    private String sessionName;
    private SessionState sessionState;

    public RoRSession(String sessionName, Map map, Player hostPlayer) {
        this.map = map;
        this.playerList = new ArrayList<>();
        this.hostPlayer = hostPlayer;
        this.sessionName = sessionName;
        playerList.add(hostPlayer);
        setSessionState(SessionState.READYTOSTART);
    }

    public RoRSession(Map map, Player hostPlayer) {
        this(null, map, hostPlayer);
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
        queueReceiver.setup();
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

    public SessionState getSessionState() {
        return sessionState;
    }

    public void setSessionState(SessionState sessionState) {
        this.sessionState = sessionState;
    }

    public String getSessionName() {
        return sessionName;
    }
}
