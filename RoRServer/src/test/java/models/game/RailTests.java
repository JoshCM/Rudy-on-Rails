package models.game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;

import communication.MessageInformation;
import communication.topic.MessageQueue;
import models.session.EditorSession;
import models.session.EditorSessionManager;

public class RailTests {

	@Test
	public void RailIsCreatedWithRightValues() {
		Direction node1 = Direction.NORTH;
		Direction node2 = Direction.SOUTH;
		List<Direction> directions = new ArrayList<>();
		directions.add(node1);
		directions.add(node2);
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(editorSession.getName(), square, directions);
		
		assertEquals(node1, rail.getFirstSection().getNode1());
		assertEquals(node2, rail.getFirstSection().getNode2());
	}
	
	@Test
	public void RailCreatesMessageAfterCreation() {
		Direction node1 = Direction.NORTH;
		Direction node2 = Direction.SOUTH;
		List<Direction> directions = new ArrayList<>();
		directions.add(node1);
		directions.add(node2);
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(editorSession.getName(), square, directions);
		
		MessageInformation messageInfo = MessageQueue.getInstance().getFirstFoundMessageInformationForMessageType("CreateRail");
	
		UUID railId = messageInfo.getValueAsUUID("railId");
		UUID squareId = messageInfo.getValueAsUUID("squareId");
		int xPos = messageInfo.getValueAsInt("xPos");
		int yPos = messageInfo.getValueAsInt("yPos");

		assertEquals(rail.getId(), railId);
		assertEquals(rail.getXPos(), xPos);
		assertEquals(rail.getYPos(), yPos);
		assertEquals(rail.getSquareId(), squareId);
	}
	
	@Test
	public void RailRotatesRight() {
		Rail rail = createCrossRail();
		rail.rotate(true);
		
		assertEquals(Direction.EAST, rail.getFirstSection().getNode1());
		assertEquals(Direction.WEST, rail.getFirstSection().getNode2());
	}
	
	@Test
	public void RailRotatesLeft() {
		Rail rail = createCrossRail();
		rail.rotate(false);
		
		assertEquals(Direction.WEST, rail.getFirstSection().getNode1());
		assertEquals(Direction.EAST, rail.getFirstSection().getNode2());
	}
	
	private Rail createCrossRail() {
		Direction node1 = Direction.NORTH;
		Direction node2 = Direction.SOUTH;
		Direction node3 = Direction.WEST;
		Direction node4 = Direction.EAST;
		List<Direction> directions = new ArrayList<>();
		directions.add(node1);
		directions.add(node2);
		directions.add(node3);
		directions.add(node4);
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(editorSession.getName(), square, directions);
		return rail;
	}
}
