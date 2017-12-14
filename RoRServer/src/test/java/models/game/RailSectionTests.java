package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;

import exceptions.InvalidModelOperationException;
import models.session.EditorSession;
import models.session.EditorSessionManager;

public class RailSectionTests {
	@Test(expected = InvalidModelOperationException.class)
	public void RailSectionWithEqualNodesThrowsException() {
		RailSectionPosition node1 = RailSectionPosition.NORTH;
		RailSectionPosition node2 = RailSectionPosition.NORTH;
		List<RailSectionPosition> railSectionPositions = new ArrayList<>();
		railSectionPositions.add(node1);
		railSectionPositions.add(node2);
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		
		new Rail(editorSession.getName(), square, railSectionPositions);
	}
}
