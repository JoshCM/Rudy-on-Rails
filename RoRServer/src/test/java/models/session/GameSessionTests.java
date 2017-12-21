package models.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;
import org.junit.Test;
import helper.MessageQueueStub;

public class GameSessionTests {
	@Test
	public void GameSession_StartGame_StartedIsSet() {
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString());
		
		gameSession.startGame();
		
		assertTrue(gameSession.isStarted());
	}
	
	@Test
	public void GameSession_StatGame_CreatesStartGameMessage() {
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString());
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		gameSession.addObserver(messageQueueStub);
		
		gameSession.startGame();
		
		String messageType = messageQueueStub.messages.get(0).getMessageType();
		assertEquals("StartGame", messageType);
	}
}
