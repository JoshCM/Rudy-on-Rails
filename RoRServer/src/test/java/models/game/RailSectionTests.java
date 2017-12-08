package models.game;

import java.util.UUID;
import org.junit.Test;
import models.editor.EditorSession;
import models.editor.EditorSessionManager;
import exceptions.InvalidModelOperationException;

public class RailSectionTests {
	@Test(expected = InvalidModelOperationException.class)
	public void RailSectionWithEqualNodesThrowsException() {
		RailSectionPosition node1 = RailSectionPosition.NORTH;
		RailSectionPosition node2 = RailSectionPosition.NORTH;
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		new Rail(square, node1, node2);
	}
}
