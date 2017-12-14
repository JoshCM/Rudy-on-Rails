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
		RailSectionPosition node1 = RailSectionPosition.NORTH;
		RailSectionPosition node2 = RailSectionPosition.SOUTH;
		List<RailSectionPosition> railSectionPositions = new ArrayList<>();
		railSectionPositions.add(node1);
		railSectionPositions.add(node2);
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(editorSession.getName(), square, railSectionPositions);
		
		assertEquals(node1, rail.getFirstSection().getNode1());
		assertEquals(node2, rail.getFirstSection().getNode2());
	}
	
	@Test
	public void RailCreatesMessageAfterCreation() {
		RailSectionPosition node1 = RailSectionPosition.NORTH;
		RailSectionPosition node2 = RailSectionPosition.SOUTH;
		List<RailSectionPosition> railSectionPositions = new ArrayList<>();
		railSectionPositions.add(node1);
		railSectionPositions.add(node2);
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(editorSession.getName(), square, railSectionPositions);
		
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
		
		assertEquals(RailSectionPosition.EAST, rail.getFirstSection().getNode1());
		assertEquals(RailSectionPosition.WEST, rail.getFirstSection().getNode2());
	}
	
	@Test
	public void RailRotatesLeft() {
		Rail rail = createCrossRail();
		rail.rotate(false);
		
		assertEquals(RailSectionPosition.WEST, rail.getFirstSection().getNode1());
		assertEquals(RailSectionPosition.EAST, rail.getFirstSection().getNode2());
	}
	
	private Rail createCrossRail() {
		RailSectionPosition node1 = RailSectionPosition.NORTH;
		RailSectionPosition node2 = RailSectionPosition.SOUTH;
		RailSectionPosition node3 = RailSectionPosition.WEST;
		RailSectionPosition node4 = RailSectionPosition.EAST;
		List<RailSectionPosition> railSectionPositions = new ArrayList<>();
		railSectionPositions.add(node1);
		railSectionPositions.add(node2);
		railSectionPositions.add(node3);
		railSectionPositions.add(node4);
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(editorSession.getName(), square, railSectionPositions);
		return rail;
	}
}
