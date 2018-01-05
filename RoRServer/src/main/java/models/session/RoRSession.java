package models.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import communication.queue.receiver.QueueReceiver;
import models.base.ModelBase;
import models.game.Map;
import models.game.Player;

/**
 * Oberklasse von EditorSession und GameSession
 */
public abstract class RoRSession extends ModelBase {
    private ArrayList<Player> players = new ArrayList<>();
    private Map map;
    protected boolean running;
    private String sessionName;

    protected QueueReceiver queueReceiver;

    public RoRSession(String mapName, String hostPlayerName) {
        map = new Map(mapName);
        createHostPlayer(hostPlayerName);
    }

    private void createHostPlayer(String playerName) {
        Player player = new Player(playerName, true);
        players.add(player);
    }

    public Player createPlayer(UUID playerId, String playerName) {
        Player player = new Player(playerName, false);
        players.add(player);
        return player;
    }

    public void setup() {
        queueReceiver.setup();
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public Player getHost() {
        return players.stream().filter(x -> x.getIsHost()).findFirst().get();
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public boolean isRunning() {
        return running;
    }
}
