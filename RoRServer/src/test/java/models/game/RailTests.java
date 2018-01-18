package models.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import communication.MessageInformation;
import communication.topic.TopicMessageQueue;
import exceptions.RailSectionException;
import models.session.EditorSession;
import models.session.EditorSessionManager;

public class RailTests {
	@Before
	public void initTests() {
		TopicMessageQueue.getInstance().clear(); // Leider nötig, damit die richtige Nachricht rausbekommt
	}

	@Test
	public void RailIsCreatedWithRightValues() {
		Compass node1 = Compass.NORTH;
		Compass node2 = Compass.SOUTH;
		List<Compass> railSectionPositions = new ArrayList<>();
		railSectionPositions.add(node1);
		railSectionPositions.add(node2);
		int squarePosX = 0;
		int squarePosY = 0;

		EditorSession editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "Player");
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(editorSession.getSessionName(), square, railSectionPositions);

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

		EditorSession editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "Player");
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		UUID railId = UUID.randomUUID();
		Rail rail = new Rail(editorSession.getSessionName(), square, railSectionPositions, UUID.randomUUID(), railId);

		MessageInformation messageInfo = TopicMessageQueue.getInstance()
				.getFirstFoundMessageInformationForAttribute(railId);

		UUID messageInfoRailId = messageInfo.getValueAsUUID("railId");
		UUID messageInfoSquareId = messageInfo.getValueAsUUID("squareId");
		int messageInfoXPos = messageInfo.getValueAsInt("xPos");
		int messageInfoYPos = messageInfo.getValueAsInt("yPos");

		// warum nicht die richtige ?
		assertEquals(rail.getId(), messageInfoRailId);
		assertEquals(rail.getXPos(), messageInfoXPos);
		assertEquals(rail.getYPos(), messageInfoYPos);
		assertEquals(rail.getSquareId(), messageInfoSquareId);
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

	@Test
	public void addRailSectionToRail() {
		Rail rail = createCrossRail();
		RailSection rs = new RailSection(rail.sessionName, rail, "NORTH", "EAST");
		rail.addRailSection(rs);
		assertTrue(rail.getAllRailSections().contains(rs));
	}

	@Test(expected = RailSectionException.class)
	public void addExistingRailSectionToRail() {
		Rail rail = createCrossRail();
		RailSection rs = new RailSection(rail.sessionName, rail, "NORTH", "EAST");
		rail.addRailSection(rs);
		RailSection rs2 = new RailSection(rail.sessionName, rail, "EAST", "NORTH");
		rail.addRailSection(rs2);
	}

	@Test
	public void removeExistingRailSectionFromRail() {
		Rail rail = createCrossRail();
		RailSection rs = new RailSection(rail.sessionName, rail, "NORTH", "SOUTH");
		rail.deleteRailSection(rs);
		assertFalse(rail.getAllRailSections().contains(rs));
	}

	@Test(expected = RailSectionException.class)
	public void removeExistingRailSectionFromRailTwice() {
		Rail rail = createCrossRail();
		RailSection rs = new RailSection(rail.sessionName, rail, "NORTH", "SOUTH");
		RailSection rs2 = new RailSection(rail.sessionName, rail, "SOUTH", "NORTH");
		rail.deleteRailSection(rs);
		rail.deleteRailSection(rs2);
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

		EditorSession editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "Player");
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(editorSession.getSessionName(), square, railSectionPositions);
		return rail;
	}

	@Test
	public void RailWithSignalsIsCreated() {
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

		EditorSession editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "Player");
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(editorSession.getSessionName(), square, railSectionPositions, true);
		Signals signals = rail.getSignals();

		assertNotNull(signals);
		assertTrue(signals.isNorthSignalActive());
		assertTrue(signals.isSouthSignalActive());
	}
}
