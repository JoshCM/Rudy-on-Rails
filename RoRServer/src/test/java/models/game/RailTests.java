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
		Compass node1 = Compass.NORTH;
		Compass node2 = Compass.SOUTH;
		List<Compass> railSectionPositions = new ArrayList<>();
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
		Compass node1 = Compass.NORTH;
		Compass node2 = Compass.SOUTH;
		List<Compass> railSectionPositions = new ArrayList<>();
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
		
		assertEquals(Compass.EAST, rail.getFirstSection().getNode1());
		assertEquals(Compass.WEST, rail.getFirstSection().getNode2());
	}
	
	@Test
	public void RailRotatesLeft() {
		Rail rail = createCrossRail();
		rail.rotate(false);
		
		assertEquals(Compass.WEST, rail.getFirstSection().getNode1());
		assertEquals(Compass.EAST, rail.getFirstSection().getNode2());
	}
	
	private Rail createCrossRail() {
		Compass node1 = Compass.NORTH;
		Compass node2 = Compass.SOUTH;
		Compass node3 = Compass.WEST;
		Compass node4 = Compass.EAST;
		List<Compass> railSectionPositions = new ArrayList<>();
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
