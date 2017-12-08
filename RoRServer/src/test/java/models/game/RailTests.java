package models.game;

import static org.junit.Assert.*;
import java.util.UUID;
import org.junit.Test;

import communication.MessageInformation;
import models.session.EditorSession;
import models.session.EditorSessionManager;

public class RailTests {

	@Test
	public void RailIsCreatedWithRightValues() {
		RailSectionPosition node1 = RailSectionPosition.NORTH;
		RailSectionPosition node2 = RailSectionPosition.SOUTH;
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(square, node1, node2);
		
		assertEquals(node1, rail.getSection().getNode1());
		assertEquals(node2, rail.getSection().getNode2());
		assertEquals(square, rail.getSquare());
	}
	
	@Test
	public void RailCreatesMessageAfterCreation() {
		RailSectionPosition node1 = RailSectionPosition.NORTH;
		RailSectionPosition node2 = RailSectionPosition.SOUTH;
		int squarePosX = 0;
		int squarePosY = 0;
		
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(square, node1, node2);
		
		MessageInformation messageInfo = editorSession.getFirstFoundMessageInformationForMessageType("CreateRail");
	
		UUID railId = messageInfo.getValueAsUUID("railId");
		UUID railSectionId = messageInfo.getValueAsUUID("railSectionId");
		UUID squareId = messageInfo.getValueAsUUID("squareId");
		String railSectionPositionNode1 = messageInfo.getValueAsString("railSectionPositionNode1");
		String railSectionPositionNode2 = messageInfo.getValueAsString("railSectionPositionNode2");
		int xPos = messageInfo.getValueAsInt("xPos");
		int yPos = messageInfo.getValueAsInt("yPos");

		assertEquals(rail.getId(), railId);
		assertEquals(rail.getSection().getId(), railSectionId);
		assertEquals(rail.getSquare().getId(), squareId);
		assertEquals(rail.getSection().getNode1().toString(), railSectionPositionNode1);
		assertEquals(rail.getSection().getNode2().toString(), railSectionPositionNode2);
		assertEquals(rail.getSquare().getXIndex(), xPos);
		assertEquals(rail.getSquare().getYIndex(), yPos);
	}
}
