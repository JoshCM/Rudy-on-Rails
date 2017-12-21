package models.session;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import models.game.Player;
import models.session.EditorSession;
import models.session.EditorSessionManager;

public class GameSessionManagerTests {
	@Test
	public void EditorSessionManager_CreatesEditorSession() {
		String gameSessionName = "TestGameSession";
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(gameSessionName);
		assertEquals(gameSessionName, gameSession.getSessionName());
	}
	
	@Test
	public void EditorSessionManager_AddsPlayer() {
		String gameSessionName = "TestGameSession";
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(gameSessionName);
		Player player = new Player(gameSessionName, "Neuer Spieler", UUID.randomUUID(), true);
		gameSession.addPlayer(player);
		
		assertEquals(1, gameSession.getPlayers().size());
		assertEquals("Neuer Spieler", gameSession.getPlayers().get(0).getName());
	}
}
