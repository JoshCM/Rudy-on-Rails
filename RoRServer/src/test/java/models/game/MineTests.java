package models.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import models.session.EditorSession;
import models.session.EditorSessionManager;

public class MineTests {
	
	@Test
	public void testRotateLeft() {
		
		// GameSession und Square erstellen
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		Square square = editorSession.getMap().getSquare(0, 0);
		
		// Neue Mine mit Rail erstellen
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);
		
		Rail rail = new Rail(editorSession.getName(), square, railSectionPositions);
		square.setPlaceableOnSquare(rail);
		Mine mine = new Mine(editorSession.getName(), square, rail.getAlignment(), rail.getId());
		rail.setPlaceableOnRail(mine);
		
		System.out.println(rail.getAlignment() + " / " + mine.getAlignment());		
		mine.rotateLeft();
		System.out.println(rail.getAlignment() + " / " + mine.getAlignment());
		
		assertEquals(rail.getAlignment(), mine.getAlignment());
	}
	
	@Test
	public void testRotateRight() {
		
		// GameSession und Square erstellen
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		Square square = editorSession.getMap().getSquare(0, 0);
		
		// Neue Mine mit Rail erstellen
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);
		
		Rail rail = new Rail(editorSession.getName(), square, railSectionPositions);
		square.setPlaceableOnSquare(rail);
		Mine mine = new Mine(editorSession.getName(), square, rail.getAlignment(), rail.getId());
		rail.setPlaceableOnRail(mine);
		
		System.out.println(rail.getAlignment() + " / " + mine.getAlignment());		
		mine.rotateRight();
		System.out.println(rail.getAlignment() + " / " + mine.getAlignment());
		
		assertEquals(rail.getAlignment(), mine.getAlignment());
	}

}
