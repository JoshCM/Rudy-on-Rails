package models.session;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import models.game.Player;

public class EditorSessionManagerTests {
	@Test
	public void EditorSessionManager_CreatesEditorSession() {
		String editorSessionName = "TestEditorSession";
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(editorSessionName,
				UUID.randomUUID(), "HostPlayer");
		assertEquals(editorSessionName, editorSession.getName());
	}

	@Test
	public void EditorSessionManager_AddsPlayer() {
		String editorSessionName = "TestEditorSession";
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(editorSessionName,
				UUID.randomUUID(), "HostPlayer");
		Player player = editorSession.createPlayer(UUID.randomUUID(), "Neuer Spieler");

		assertEquals(2, editorSession.getPlayerList().size());
		assertEquals("HostPlayer", editorSession.getPlayerList().get(0).getPlayerName());
		assertEquals("Neuer Spieler", editorSession.getPlayerList().get(1).getPlayerName());
	}
}
