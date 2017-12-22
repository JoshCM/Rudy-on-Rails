package models.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import communication.topic.TopicMessageQueue;
import helper.MessageQueueStub;
import models.session.GameSession;
import models.session.GameSessionManager;

public class LocoTests {

	@Before
	public void intiTests() {
		TopicMessageQueue.getInstance().clear();
	}
	
	/**
	 * Loco sendet richtige Nachricht an Client
	 */
	@Test
	public void Loco_SendUpdateLocoMessage() {
		int squarePosX = 0;
		int squarePosY = 0;
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "HostPlayer");

		Player player = new Player(gameSession.getSessionName(), "Hans", UUID.randomUUID(), true);

		Map map = gameSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Loco loco = new Loco(gameSession.getSessionName(), square, map, player.getId());
		
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		loco.addObserver(messageQueueStub);
		
		loco.drive();
		
		String messageType = messageQueueStub.messages.get(0).getMessageType();

		assertEquals("UpdateLocoPosition", messageType);
	}
	
	/**
	 * Besitzt die erzeugte Loco initial ein Cart?
	 */
	@Test
	public void LocoHasInitialCart() {
		int squarePosX = 0;
		int squarePosY = 0;
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "HostPlayer");
		Player player = new Player(gameSession.getSessionName(), "Hans", UUID.randomUUID(), true);

		Map map = gameSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Loco loco = new Loco(gameSession.getSessionName(), square, map, player.getId());

		assertEquals(1, loco.getCarts().size());
	}

	/**
	 * Findet die Loco die Rail, auf der sie steht?
	 */
	@Test
	public void LocoFindsRailItStandsOn() {
		int squarePosX = 0;
		int squarePosY = 0;
		Compass node1 = Compass.NORTH;
		Compass node2 = Compass.SOUTH;
		List<Compass> directions = new ArrayList<>();
		directions.add(node1);
		directions.add(node2);

		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		Player player = new Player(gameSession.getSessionName(), "Hans", UUID.randomUUID(), true);
		Map map = gameSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(gameSession.getSessionName(), square, directions);

		square.setPlaceable(rail);
		Loco loco = new Loco(gameSession.getSessionName(), square, map, player.getId());

		assertEquals(rail, loco.getRail());
	}
}
