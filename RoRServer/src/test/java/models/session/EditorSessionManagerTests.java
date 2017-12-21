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
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(editorSessionName);
		assertEquals(editorSessionName, editorSession.getSessionName());
	}
	
	@Test
	public void EditorSessionManager_AddsPlayer() {
		String editorSessionName = "TestEditorSession";
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(editorSessionName);
		Player player = new Player(editorSessionName, "Neuer Spieler", UUID.randomUUID(), true);
		editorSession.addPlayer(player);
		
		assertEquals(1, editorSession.getPlayers().size());
		assertEquals("Neuer Spieler", editorSession.getPlayers().get(0).getName());
	}
}
