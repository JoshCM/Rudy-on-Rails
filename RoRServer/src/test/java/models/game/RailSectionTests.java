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
		Direction node1 = Direction.NORTH;
		Direction node2 = Direction.NORTH;
		List<Direction> directions = new ArrayList<>();
		directions.add(node1);
		directions.add(node2);
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		
		new Rail(editorSession.getName(), square, directions);
	}
	
	private Rail createTestRail(Direction node1, Direction node2) {
		List<Direction> directions = new ArrayList<>();
		directions.add(node1);
		directions.add(node2);
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		
		return new Rail(editorSession.getName(), square, directions);
	}
	
	@Test
	public void RailSectionNORTHSOUTHRotatesRight() {
		Direction node1 = Direction.NORTH;
		Direction node2 = Direction.SOUTH;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2);
		
		section.rotate(true);
		
		assertEquals(Direction.EAST, section.getNode1());
		assertEquals(Direction.WEST, section.getNode2());
	}
	
	@Test
	public void RailSectionEASTWESTRotatesRight() {
		Direction node1 = Direction.EAST;
		Direction node2 = Direction.WEST;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2);
		
		section.rotate(true);
		
		assertEquals(Direction.SOUTH, section.getNode1());
		assertEquals(Direction.NORTH, section.getNode2());
	}
	
	@Test
	public void RailSectionNORTHSOUTHRotatesLeft() {
		Direction node1 = Direction.NORTH;
		Direction node2 = Direction.SOUTH;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2);
		
		section.rotate(false);
		
		assertEquals(Direction.WEST, section.getNode1());
		assertEquals(Direction.EAST, section.getNode2());
	}
	
	@Test
	public void RailSectionEASTWESTRotatesLeft() {
		Direction node1 = Direction.EAST;
		Direction node2 = Direction.WEST;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2);
		
		section.rotate(false);
		
		assertEquals(Direction.NORTH, section.getNode1());
		assertEquals(Direction.SOUTH, section.getNode2());
	}
}
