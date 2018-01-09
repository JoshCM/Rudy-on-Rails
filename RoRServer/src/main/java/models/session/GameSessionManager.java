package models.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Hier werden alle GameSessions erzeugt und gehalten GameSessions duerfen
 * ausschliesslich ueber diesen Manager erzeugt werden!
 * PATTERN: Singleton!!!
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
	 * GameSession mit editorName wird erstellt und wird mit der UUID in eine
	 * HashMap gespeichert.
	 * 
	 * @param gameName
	 * @return
	 */
	public GameSession createNewGameSession(String gameName, UUID hostPlayerId, String hostPlayerName) {
		GameSession gameSession = new GameSession(gameName, hostPlayerId, hostPlayerName);
		gameSessionMap.put(gameName, gameSession);
		return gameSession;
	}

	/**
	 * Entfernt spezifische GameSession aus HashMap
	 * 
	 * @param gameSession
	 */
	public void removeGameSession(GameSession gameSession) {
		gameSessionMap.remove(gameSession.getName());
	}

	// aktuell wird immer die erste GameSession in der HashMap zurueckgegeben
	// TODO: sobald wir eine Lobby haben in der man sich einen Game aussuchen kann,
	// muss auch dieser zurueckgegeben werden.
	public GameSession getGameSession() {
		GameSession game;
		if (!gameSessionMap.values().isEmpty()) {
			game = (GameSession) gameSessionMap.values().toArray()[0];
			return game;
		}
		return null;
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
