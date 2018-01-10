package models.session;

import exceptions.SessionManagerException;
import models.game.Map;
import models.game.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Hier werden alle GameSessions erzeugt und gehalten GameSessions duerfen
 * ausschliesslich ueber diesen Manager erzeugt werden!
 * PATTERN: Singleton!!!
 * TODO: Hier nach Statusänderungen schauen.
 */
public class GameSessionManager {
    private static GameSessionManager gameSessionManager;
    private HashMap<String, GameSession> gameSessionMap = new HashMap<>();

    private GameSessionManager() {
    }

    public static GameSessionManager getInstance() {
        if (gameSessionManager == null) {
            gameSessionManager = new GameSessionManager();
        }
        return gameSessionManager;
    }

    /**
     * Im Unterschied zur EditorSession braucht eine GameSession unbedingt eine Map um Starten zu können
     *
     * @param sessionName
     * @param map
     * @param hostPlayer
     * @return
     */
    public GameSession createGameSession(String sessionName, Map map, Player hostPlayer) {
        GameSession gameSession = new GameSession(sessionName, map, hostPlayer);
        gameSessionMap.put(sessionName, gameSession);
        return gameSession;
    }

    public GameSession createGameSession(Map map, Player hostPlayer) {
        return createGameSession(UUID.randomUUID().toString(), map, hostPlayer);
    }

    public GameSession createGameSession(String sessionName, Map map) {
        GameSession gameSession = new GameSession(sessionName, map);
        gameSessionMap.put(sessionName, gameSession);
        return gameSession;
    }

    public GameSession createGameSession(Map map) {
        return createGameSession(UUID.randomUUID().toString(), map);
    }

    /**
     * Entfernt spezifische GameSession aus HashMap
     *
     * @param gameSession
     */
    public void removeGameSessionByName(GameSession gameSession) {
        if (gameSessionMap.containsKey(gameSession)) {
        gameSessionMap.remove(gameSession.getName());
        } else {
            throw new SessionManagerException("Coulnd't remove GameSession - GameSession not Found");
        }
    }

    public GameSession getGameSessionByName(String name) {
        return gameSessionMap.get(name);
    }

    public List<GameSession> getGameSessionsAsList() {
        List<GameSession> result = new ArrayList<GameSession>();
        for (GameSession session : gameSessionMap.values()) {
            result.add(session);
        }
        return result;
    }
}
