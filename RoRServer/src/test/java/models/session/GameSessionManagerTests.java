package models.session;

import static org.junit.Assert.assertEquals;
import java.util.UUID;
import org.junit.Test;
import models.game.Player;

public class GameSessionManagerTests {
	@Test
	public void EditorSessionManager_CreatesEditorSession() {
		String gameSessionName = "TestGameSession";
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(gameSessionName, UUID.randomUUID(), "HostPlayer");
		assertEquals(gameSessionName, gameSession.getName());
	}
	
	@Test
	public void EditorSessionManager_AddsPlayer() {
		String gameSessionName = "TestGameSession";
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(gameSessionName, UUID.randomUUID(), "HostPlayer");
		Player player = gameSession.createPlayer(UUID.randomUUID(), "Neuer Spieler");

		assertEquals(2, gameSession.getPlayers().size());
		assertEquals("HostPlayer", gameSession.getPlayers().get(0).getName());
		assertEquals("Neuer Spieler", gameSession.getPlayers().get(1).getName());
	}
}
