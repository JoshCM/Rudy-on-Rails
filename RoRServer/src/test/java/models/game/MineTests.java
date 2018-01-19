package models.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import communication.MessageInformation;
import communication.topic.TopicMessageQueue;
import models.session.EditorSession;
import models.session.EditorSessionManager;
import models.session.GameSession;
import models.session.GameSessionManager;

public class MineTests {

	// vor jedem Test "aufr�umen" (Instanz l�schen)
	@Before
	public void initTest() {
		TopicMessageQueue.getInstance().clear();
	}

	@Test
	public void mineCreatesMessageAfterCreation() {
		Mine mine = createTestGameMine();

		MessageInformation messageInfo = TopicMessageQueue.getInstance()
				.getFirstFoundMessageInformationForMessageType("CreateMine");
		
		UUID messageInfoMineId = messageInfo.getValueAsUUID("mineId");
		UUID messageInfoSquareId = messageInfo.getValueAsUUID("squareId");
		int messageInfoXPos = messageInfo.getValueAsInt("xPos");
		int messageInfoYPos = messageInfo.getValueAsInt("yPos");
		
		assertEquals(mine.getId(), messageInfoMineId);
		assertEquals(mine.getXPos(), messageInfoXPos);
		assertEquals(mine.getYPos(), messageInfoYPos);
		assertEquals(mine.getSquareId(), messageInfoSquareId);
	}
	
	private Mine createTestGameMine() {
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "HostPlayer");
		Square square = gameSession.getMap().getSquare(0, 0);

		// Neue Mine mit Rail erstellen
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);

		Rail rail = new Rail(gameSession.getDescription(), square, railSectionPositions);
		square.setPlaceableOnSquare(rail);
		Mine mine = new Mine(gameSession.getDescription(), square, rail.getAlignment(), rail.getId());
		rail.setPlaceableOnRail(mine);

		return mine;
	}

	// man k�nnte noch Methode "createTestEditorMine(){} erstellen, um Code den man
	// immer wieder verwendet, auszulagern

	@Test
	public void testRotateLeft() {

		// GameSession und Square erstellen
		EditorSession editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		Square square = editorSession.getMap().getSquare(0, 0);

		// Neue Mine mit Rail erstellen
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);

		Rail rail = new Rail(editorSession.getDescription(), square, railSectionPositions);
		square.setPlaceableOnSquare(rail);
		Mine mine = new Mine(editorSession.getDescription(), square, rail.getAlignment(), rail.getId());
		rail.setPlaceableOnRail(mine);

		mine.rotateLeft();

		assertEquals(rail.getAlignment(), mine.getAlignment());
	}

	@Test
	public void testRotateRight() {

		// GameSession und Square erstellen
		EditorSession editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		Square square = editorSession.getMap().getSquare(0, 0);

		// Neue Mine mit Rail erstellen
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);

		Rail rail = new Rail(editorSession.getDescription(), square, railSectionPositions);
		square.setPlaceableOnSquare(rail);
		Mine mine = new Mine(editorSession.getDescription(), square, rail.getAlignment(), rail.getId());
		rail.setPlaceableOnRail(mine);

		mine.rotateRight();

		assertEquals(rail.getAlignment(), mine.getAlignment());
	}

}
