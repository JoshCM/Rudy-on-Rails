package models.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import communication.MessageInformation;
import communication.topic.TopicMessageQueue;
import helper.MessageQueueStub;
import models.session.EditorSession;
import models.session.EditorSessionManager;
import models.session.GameSession;
import models.session.GameSessionManager;

public class LocoTests {

	@Before
	public void initTests() {
		TopicMessageQueue.getInstance().clear();
	}
	
	@Test
	public void LocoCreatesMessageAfterCreation() {		
		Loco loco = createTestLoco();
		
		MessageInformation messageInfo = TopicMessageQueue.getInstance()
				.getFirstFoundMessageInformationForMessageType("CreateLoco");

		UUID messageInfoLocoId = messageInfo.getValueAsUUID("locoId");
		//UUID messageInfoSquareId = messageInfo.getValueAsUUID("squareId");
		int messageInfoXPos = messageInfo.getValueAsInt("xPos");
		int messageInfoYPos = messageInfo.getValueAsInt("yPos");

		assertEquals(loco.getId(), messageInfoLocoId);
		assertEquals(loco.getXPos(), messageInfoXPos);
		assertEquals(loco.getYPos(), messageInfoYPos);
		//assertEquals(loco.getSquareId(), messageInfoSquareId);
	}
	
	/**
	 * Loco sendet richtige Nachricht an Client, wenn sie f�hrt
	 */
	/* es müssten die Rails erstellt werden um keine NullPointerExceptions zu bekommen
	@Test
	public void LocoSendsUpdateLocoMessage() {
		// Square, auf das die Loco f�hrt
		int squarePosXNext = 0;
		int squarePosYNext = 0;
		// Square, auf dem die Loco steht
		int squarePosXLoco = 0;
		int squarePosYLoco = 1;
		int squarePosXCart = 0;
		int squarePosYCart = 2;
		Compass node1 = Compass.NORTH;
		Compass node2 = Compass.SOUTH;
		List<Compass> directions = new ArrayList<>();
		directions.add(node1);
		directions.add(node2);
		
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "HostPlayer");
		Player player = new Player(gameSession.getSessionName(), "Hans", UUID.randomUUID(), true);

		Map map = gameSession.getMap();
		Square squareLoco = map.getSquare(squarePosXLoco, squarePosYLoco);
		Square squareCart = map.getSquare(squarePosXCart, squarePosYCart);
		Square squareNext = map.getSquare(squarePosXNext, squarePosYNext);
		
		Rail railLoco = new Rail(gameSession.getSessionName(), squareLoco, directions);
		Rail railCart = new Rail(gameSession.getSessionName(), squareCart, directions);
		Rail railNext = new Rail(gameSession.getSessionName(), squareNext, directions);

		squareLoco.setPlaceable(railLoco);
		squareCart.setPlaceable(railCart);
		squareNext.setPlaceable(railNext);

		Loco loco = new Loco(gameSession.getSessionName(), squareLoco, map, player.getId());
		
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		loco.addObserver(messageQueueStub);
		
		// Methode, die in diesem Fall getestet wird
		loco.drive();
		
		String messageType = messageQueueStub.messages.get(0).getMessageType();

		assertEquals("UpdateLocoPosition", messageType);
	}
	
	/**
	 * Besitzt die erzeugte Loco initial ein Cart?
	 */
	@Test
	public void LocoHasInitialCart() {
		Loco loco = createTestLoco();

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
				UUID.randomUUID(), "HostPlayer");
		
		Map map = gameSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		
		Rail rail = new Rail(gameSession.getName(), square, directions);

		square.setPlaceableOnSquare(rail);
		
		Loco loco = createTestLoco();

		assertEquals(rail, loco.getRail());
	}
	
	private Loco createTestLoco() {
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
		
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "HostPlayer");
		Player player = new Player(gameSession.getName(), "Hans", UUID.randomUUID(), true);

		Map map = gameSession.getMap();
		Square squareLoco = map.getSquare(squarePosXLoco, squarePosYLoco);
		Square squareCart = map.getSquare(squarePosXCart, squarePosYCart);
		
		Rail railLoco = new Rail(gameSession.getName(), squareLoco, directions);
		Rail railCart = new Rail(gameSession.getName(), squareCart, directions);

		squareLoco.setPlaceableOnSquare(railLoco);
		squareCart.setPlaceableOnSquare(railCart);
		
		PlayerLoco loco = new PlayerLoco(gameSession.getName(), squareLoco, player.getId());
		
		return loco;
	}
}