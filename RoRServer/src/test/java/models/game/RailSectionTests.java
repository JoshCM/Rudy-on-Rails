package models.game;

import static org.junit.Assert.assertEquals;

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
	
	private Rail createTestRail(RailSectionPosition node1, RailSectionPosition node2) {
		List<RailSectionPosition> railSectionPositions = new ArrayList<>();
		railSectionPositions.add(node1);
		railSectionPositions.add(node2);
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		
		return new Rail(editorSession.getName(), square, railSectionPositions);
	}
	
	@Test
	public void RailSectionNORTHSOUTHRotatesRight() {
		RailSectionPosition node1 = RailSectionPosition.NORTH;
		RailSectionPosition node2 = RailSectionPosition.SOUTH;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2);
		
		section.rotate(true);
		
		assertEquals(RailSectionPosition.EAST, section.getNode1());
		assertEquals(RailSectionPosition.WEST, section.getNode2());
	}
	
	@Test
	public void RailSectionEASTWESTRotatesRight() {
		RailSectionPosition node1 = RailSectionPosition.EAST;
		RailSectionPosition node2 = RailSectionPosition.WEST;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2);
		
		section.rotate(true);
		
		assertEquals(RailSectionPosition.SOUTH, section.getNode1());
		assertEquals(RailSectionPosition.NORTH, section.getNode2());
	}
	
	@Test
	public void RailSectionNORTHSOUTHRotatesLeft() {
		RailSectionPosition node1 = RailSectionPosition.NORTH;
		RailSectionPosition node2 = RailSectionPosition.SOUTH;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2);
		
		section.rotate(false);
		
		assertEquals(RailSectionPosition.WEST, section.getNode1());
		assertEquals(RailSectionPosition.EAST, section.getNode2());
	}
	
	@Test
	public void RailSectionEASTWESTRotatesLeft() {
		RailSectionPosition node1 = RailSectionPosition.EAST;
		RailSectionPosition node2 = RailSectionPosition.WEST;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2);
		
		section.rotate(false);
		
		assertEquals(RailSectionPosition.NORTH, section.getNode1());
		assertEquals(RailSectionPosition.SOUTH, section.getNode2());
	}
}
