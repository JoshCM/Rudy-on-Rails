package communication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.google.gson.JsonObject;

import communication.dispatcher.FromClientRequestQueueDispatcher;
import communication.dispatcher.RequestSerializer;
import helper.MessageQueueStub;
import models.game.Player;
import models.session.EditorSession;
import models.session.EditorSessionManager;
import models.session.GameSession;
import models.session.GameSessionManager;

public class FromClientRequestQueueDispatcherTests {
	@Test
	public void FromClientRequestQueueDispatcher_handleCreateEditorSession_createsEditorSessionAndHostPlayer() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.addObserver(messageQueueStub);
		String messageType = "CreateEditorSession";
		String editorSessionName = "TestSession";
		String playerName = "MyPlayer";

		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.putValue("editorName", editorSessionName);
		messageInfo.putValue("playerName", playerName);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleCreateEditorSession(messageInfo);

		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(editorSessionName);
		assertNotNull(editorSession);

		Player player = editorSession.getPlayers().get(0);
		assertNotNull(player);
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleCreateEditorSession_createsResponseWithExpectedValues() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.addObserver(messageQueueStub);
		String messageType = "CreateEditorSession";
		String editorSessionName = "TestSession";
		String playerName = "MyPlayer";

		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.putValue("editorName", editorSessionName);
		messageInfo.putValue("playerName", playerName);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleCreateEditorSession(messageInfo);

		MessageEnvelope messageEnvelope = messageQueueStub.messages.get(0);
		MessageInformation response = messageEnvelope.getMessageInformation();
		assertEquals(messageType, messageEnvelope.getMessageType());
		assertEquals(editorSessionName, response.getValueAsString("editorName"));
		assertEquals(editorSessionName, response.getValueAsString("topicName"));
		assertEquals(playerName, response.getValueAsString("playerName"));
		assertNotNull(response.getValueAsUUID("playerId"));
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleCreateGameSession_createsGameSessionAndHostPlayer() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.addObserver(messageQueueStub);
		String messageType = "CreateGameSession";
		String gameSessionName = "TestSession";
		String playerName = "MyPlayer";

		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.putValue("gameName", gameSessionName);
		messageInfo.putValue("playerName", playerName);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleCreateGameSession(messageInfo);

		GameSession editorSession = GameSessionManager.getInstance().getGameSessionByName(gameSessionName);
		assertNotNull(editorSession);

		Player player = editorSession.getPlayers().get(0);
		assertNotNull(player);
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleCreateGameSession_createsResponseWithExpectedValues() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.addObserver(messageQueueStub);
		String messageType = "CreateGameSession";
		String gameSessionName = "TestSession";
		String playerName = "MyPlayer";

		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.putValue("gameName", gameSessionName);
		messageInfo.putValue("playerName", playerName);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleCreateGameSession(messageInfo);

		MessageEnvelope messageEnvelope = messageQueueStub.messages.get(0);
		MessageInformation response = messageEnvelope.getMessageInformation();
		assertEquals(messageType, messageEnvelope.getMessageType());
		assertEquals(gameSessionName, response.getValueAsString("gameName"));
		assertEquals(gameSessionName, response.getValueAsString("topicName"));
		assertEquals(playerName, response.getValueAsString("playerName"));
		assertNotNull(response.getValueAsUUID("playerId"));
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleReadGameSessions_createsResonseWithExpectedValues() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.addObserver(messageQueueStub);
		String messageType = "ReadGameSessions";
		String gameSessionName = "TestSession";
		String hostPlayerName = "HostPlayer";

		GameSessionManager.getInstance().createNewGameSession(gameSessionName, UUID.randomUUID(), hostPlayerName);
		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleReadGameSessions(messageInfo);

		MessageEnvelope messageEnvelope = messageQueueStub.messages.get(0);
		MessageInformation response = messageEnvelope.getMessageInformation();
		assertEquals(messageType, messageEnvelope.getMessageType());

		@SuppressWarnings("unchecked")
		List<JsonObject> gameSessionInfos = (List<JsonObject>) response.getValue("gameSessionInfo");
		JsonObject gameSessionInfo = gameSessionInfos.get(0);
		assertEquals(gameSessionName, gameSessionInfo.get("name").getAsString());
		assertEquals(hostPlayerName, gameSessionInfo.get("hostname").getAsString());
		assertEquals(1, gameSessionInfo.get("amountOfPlayers").getAsInt());
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleReadGameSessions_ignoresStartedGame() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.addObserver(messageQueueStub);
		String messageType = "ReadGameSessions";
		String gameSessionName = "TestSession";
		String hostPlayerName = "HostPlayer";

		GameSessionManager.getInstance().createNewGameSession(gameSessionName, UUID.randomUUID(), hostPlayerName);
		GameSession startedGameSession = GameSessionManager.getInstance().createNewGameSession("StartedGame",
				UUID.randomUUID(), "NewPlayer");
		startedGameSession.startGame();

		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleReadGameSessions(messageInfo);

		MessageEnvelope messageEnvelope = messageQueueStub.messages.get(0);
		MessageInformation response = messageEnvelope.getMessageInformation();
		assertEquals(messageType, messageEnvelope.getMessageType());

		@SuppressWarnings("unchecked")
		List<JsonObject> gameSessionInfos = (List<JsonObject>) response.getValue("gameSessionInfo");
		JsonObject gameSessionInfo = gameSessionInfos.get(0);
		assertEquals(gameSessionName, gameSessionInfo.get("name").getAsString());
		assertEquals(hostPlayerName, gameSessionInfo.get("hostname").getAsString());
		assertEquals(1, gameSessionInfo.get("amountOfPlayers").getAsInt());
	}
	
	@Test
	public void FromClientRequestQueueDispatcher_handleReadEditorSessions_createsResonseWithExpectedValues() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.addObserver(messageQueueStub);
		String messageType = "ReadEditorSessions";
		String gameSessionName = "TestSession";
		String hostPlayerName = "HostPlayer";

		EditorSessionManager.getInstance().createNewEditorSession(gameSessionName, UUID.randomUUID(), hostPlayerName);
		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleReadEditorSessions(messageInfo);

		MessageEnvelope messageEnvelope = messageQueueStub.messages.get(0);
		MessageInformation response = messageEnvelope.getMessageInformation();
		assertEquals(messageType, messageEnvelope.getMessageType());

		@SuppressWarnings("unchecked")
		List<JsonObject> editorSessionInfos = (List<JsonObject>) response.getValue("editorSessionInfo");
		JsonObject editorSessionInfo = editorSessionInfos.get(0);
		assertEquals(gameSessionName, editorSessionInfo.get("name").getAsString());
		assertEquals(hostPlayerName, editorSessionInfo.get("hostname").getAsString());
		assertEquals(1, editorSessionInfo.get("amountOfPlayers").getAsInt());
	}
}
