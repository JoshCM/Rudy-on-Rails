package models.session;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import models.game.Player;
import models.session.EditorSession;
import models.session.EditorSessionManager;

public class EditorSessionManagerTests {
	@Test
	public void EditorSessionManager_CreatesEditorSession() {
		String editorSessionName = "TestEditorSession";
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(editorSessionName);
		assertEquals(editorSessionName, editorSession.getName());
	}
	
	@Test
	public void EditorSessionManager_AddsPlayer() {
		String editorSessionName = "TestEditorSession";
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(editorSessionName);
		Player player = new Player(editorSessionName, "Neuer Spieler");
		editorSession.addPlayer(player);
		
		assertEquals(1, editorSession.getPlayers().size());
		assertEquals("Neuer Spieler", editorSession.getPlayers().get(0).getName());
	}
}
