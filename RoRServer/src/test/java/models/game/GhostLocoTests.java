package models.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import communication.MessageInformation;
import communication.topic.TopicMessageQueue;
import models.session.GameSession;
import models.session.GameSessionManager;

public class GhostLocoTests {
	@Before
	public void initTests() {
		TopicMessageQueue.getInstance().clear();
	}
	
	@Test
	public void GhostLoco_CreatesMessageAfterCreation() {		
		GhostLoco ghostLoco = createTestGhostLoco();
		
		MessageInformation messageInfo = TopicMessageQueue.getInstance()
				.getFirstFoundMessageInformationForMessageType("CreateGhostLoco");

		UUID messageInfoLocoId = messageInfo.getValueAsUUID("locoId");
		int messageInfoXPos = messageInfo.getValueAsInt("xPos");
		int messageInfoYPos = messageInfo.getValueAsInt("yPos");

		assertEquals(ghostLoco.getId(), messageInfoLocoId);
		assertEquals(ghostLoco.getXPos(), messageInfoXPos);
		assertEquals(ghostLoco.getYPos(), messageInfoYPos);
	}
	
	@Test
	public void GhostLoco_CreatesStartsWithOneCart() {		
		GhostLoco ghostLoco = createTestGhostLoco();
		
		MessageInformation messageInfo = TopicMessageQueue.getInstance()
				.getFirstFoundMessageInformationForMessageType("CreateCart");

		UUID messageInfoLocoId = messageInfo.getValueAsUUID("currentLocoId");
		int messageInfoXPos = messageInfo.getValueAsInt("xPos");
		int messageInfoYPos = messageInfo.getValueAsInt("yPos");
		
		Cart cart = ghostLoco.getCarts().get(0);
		
		assertEquals(ghostLoco.getId(), cart.getCurrentLocoId());
		assertEquals(ghostLoco.getId(), messageInfoLocoId);
		
		assertEquals(cart.getXPos(), messageInfoXPos);
		assertEquals(cart.getYPos(), messageInfoYPos);
	}
	
	private GhostLoco createTestGhostLoco() {
		// Square, auf dem die Loco steht
		int squarePosXLoco = 0;
		int squarePosYLoco = 0;
		// Square, auf dem der Cart steht
		int squarePosXCart = 0;
		int squarePosYCart = 1;
		Compass node1 = Compass.NORTH;
		Compass node2 = Compass.SOUTH;
		List<Compass> directions = new ArrayList<>();
		directions.add(node1);
		directions.add(node2);
		
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession("TestSession",
				UUID.randomUUID(), "HostPlayer");
		Player player = new Player(gameSession.getSessionName(), "Hans", UUID.randomUUID(), true);

		Map map = gameSession.getMap();
		Square squareLoco = map.getSquare(squarePosXLoco, squarePosYLoco);
		Square squareCart = map.getSquare(squarePosXCart, squarePosYCart);
		
		Rail railLoco = new Rail(gameSession.getSessionName(), squareLoco, directions);
		Rail railCart = new Rail(gameSession.getSessionName(), squareCart, directions);

		squareLoco.setPlaceableOnSquare(railLoco);
		squareCart.setPlaceableOnSquare(railCart);
		
		GhostLoco loco = new GhostLoco(gameSession.getSessionName(), squareLoco, player.getId());
		
		return loco;
	}
}
