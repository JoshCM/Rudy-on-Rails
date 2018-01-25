package models.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.Test;

import exceptions.InvalidModelOperationException;
import models.session.EditorSession;
import models.session.EditorSessionManager;

public class RailSectionTests {
	@Test(expected = InvalidModelOperationException.class)
	public void RailSectionWithEqualNodesThrowsException() {
		Compass node1 = Compass.NORTH;
		Compass node2 = Compass.NORTH;
		List<Compass> railSectionPositions = new ArrayList<>();
		railSectionPositions.add(node1);
		railSectionPositions.add(node2);
		int squarePosX = 0;
		int squarePosY = 0;

		EditorSession editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "Player");
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);

		new Rail(editorSession.getSessionName(), square, railSectionPositions);
	}

	private Rail createTestRail(Compass node1, Compass node2) {
		List<Compass> railSectionPositions = new ArrayList<>();
		railSectionPositions.add(node1);
		railSectionPositions.add(node2);
		int squarePosX = 0;
		int squarePosY = 0;

		EditorSession editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "Player");
		Map map = editorSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);

		return new Rail(editorSession.getSessionName(), square, railSectionPositions);
	}

	@Test
	public void RailSectionNORTHSOUTHRotatesRight() {
		Compass node1 = Compass.NORTH;
		Compass node2 = Compass.SOUTH;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2, RailSectionStatus.ACTIVE);

		section.rotate(true);

		assertEquals(Compass.EAST, section.getNode1());
		assertEquals(Compass.WEST, section.getNode2());
	}

	@Test
	public void RailSectionEASTWESTRotatesRight() {
		Compass node1 = Compass.EAST;
		Compass node2 = Compass.WEST;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2, RailSectionStatus.ACTIVE);

		section.rotate(true);

		assertEquals(Compass.SOUTH, section.getNode1());
		assertEquals(Compass.NORTH, section.getNode2());
	}

	@Test
	public void RailSectionNORTHSOUTHRotatesLeft() {
		Compass node1 = Compass.NORTH;
		Compass node2 = Compass.SOUTH;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2, RailSectionStatus.ACTIVE);

		section.rotate(false);

		assertEquals(Compass.WEST, section.getNode1());
		assertEquals(Compass.EAST, section.getNode2());
	}

	@Test
	public void RailSectionEASTWESTRotatesLeft() {
		Compass node1 = Compass.EAST;
		Compass node2 = Compass.WEST;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2, RailSectionStatus.ACTIVE);

		section.rotate(false);

		assertEquals(Compass.NORTH, section.getNode1());
		assertEquals(Compass.SOUTH, section.getNode2());
	}

	@Test
	public void RailSectionDrivableToggle() {
		Compass node1 = Compass.EAST;
		Compass node2 = Compass.SOUTH;
		Rail rail = createTestRail(node1, node2);
		RailSection section = new RailSection("test", rail, node1, node2, RailSectionStatus.ACTIVE);
		Random generator = new Random();
		int togglecount = generator.nextInt(17);

		for (int i=0; i<togglecount; i++) {
		    section.toggleIsDrivable();
        }

		if (section.getIsDrivable()) {
		    section.toggleIsDrivable();
		    assertFalse(section.getIsDrivable());
        } else {
		    section.toggleIsDrivable();
		    assertTrue(section.getIsDrivable());
        }

	}



}
