package models.session;

import java.util.HashMap;
import java.util.UUID;


/**
 * Hier werden alle GameSessions erzeugt und gehalten
 * GameSessions duerfen ausschliesslich ueber diesen Manager erzeugt werden!
 */
public class GameSessionManager {

	private static GameSessionManager gameSessionManager;
    private HashMap<UUID, GameSession> gameSessionMap = new HashMap<>();
    
    
    private GameSessionManager() {}
    	
    	public static GameSessionManager getInstance(){
            if(gameSessionManager == null) {
            	gameSessionManager = new GameSessionManager();
            }
            return gameSessionManager;
        }
    	/**
         * GameSession mit editorName wird erstellt und wird mit der UUID in eine HashMap gespeichert.
         * @param gameName
         * @return
         */
        public GameSession createNewGameSession(String gameName){
            GameSession gameSession = new GameSession(gameName);
            gameSessionMap.put(UUID.randomUUID(), gameSession);
            return gameSession;
        }
        
        /**
         * Entfernt spezifische GameSession aus HashMap
         * @param gameSession
         */
        public void removeGameSession(GameSession gameSession){
            gameSessionMap.remove(UUID.randomUUID());
        }
        
        //aktuell wird immer die erste GameSession in der HashMap zurueckgegeben
        //TODO: sobald wir eine Lobby haben in der man sich einen Game aussuchen kann, muss auch dieser zurueckgegeben werden.
        public GameSession getGameSession(){
            GameSession game;
            if(!gameSessionMap.values().isEmpty() ) {
            	game =  (GameSession) gameSessionMap.values().toArray()[0];
                return game;
            }
            return null;
        }

}
