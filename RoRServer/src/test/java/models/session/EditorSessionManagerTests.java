package models.session;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import models.game.Player;
import models.session.EditorSession;
import models.session.EditorSessionManager;

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
		editorSession.createPlayer(UUID.randomUUID(), "Neuer Spieler");

		assertEquals(2, editorSession.getPlayers().size());
		assertEquals("HostPlayer", editorSession.getPlayers().get(0).getName());
		assertEquals("Neuer Spieler", editorSession.getPlayers().get(1).getName());
	}
}
