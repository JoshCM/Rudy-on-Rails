package communication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import models.session.EditorSession;
import models.session.GameSession;
import org.junit.Test;

import com.google.gson.JsonObject;

import communication.dispatcher.FromClientRequestQueueDispatcher;
import helper.MessageQueueStub;
import models.game.Player;
import models.session.EditorSessionManager;
import models.session.GameSessionManager;

public class FromClientRequestQueueDispatcherTests {

	@Test
	public void FromClientRequestQueueDispatcher_handleCreateEditorSession_createsEditorSessionAndHostPlayer() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.registerObserver(messageQueueStub);
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

		Player player = editorSession.getPlayerList().get(0);
		assertNotNull(player);
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleCreateEditorSession_createsResponseWithExpectedValues() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.registerObserver(messageQueueStub);
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
		dispatcher.registerObserver(messageQueueStub);
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

		Player player = editorSession.getPlayerList().get(0);
		assertNotNull(player);
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleCreateGameSession_createsResponseWithExpectedValues() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.registerObserver(messageQueueStub);
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
	public void FromClientRequestQueueDispatcher_handleJoinEditorSession_createsNewPlayerWithRightName() {
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		String messageType = "JoinEditorSession";
		String editorSessionName = "TestSession";
		String joinedPlayerName = "MyPlayer";

		EditorSessionManager.getInstance().createEditorSession(editorSessionName, UUID.randomUUID(), "host");
		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.putValue("editorName", editorSessionName);
		messageInfo.putValue("playerName", joinedPlayerName);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleJoinEditorSession(messageInfo);

		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(editorSessionName);

		assertEquals(2, editorSession.getPlayerList().size());

		Player joinedPlayer = editorSession.getPlayerList().stream().filter(x -> x.getPlayerName().equals(joinedPlayerName))
				.findFirst().get();
		assertNotNull(joinedPlayer);
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleJoinEditorSession_createsResponseWithExpectedValues() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.registerObserver(messageQueueStub);
		String messageType = "JoinEditorSession";
		String editorSessionName = "TestSession";
		String joinedPlayerName = "MyPlayer";

		EditorSessionManager.getInstance().createEditorSession(editorSessionName, UUID.randomUUID(), "host");
		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.putValue("editorName", editorSessionName);
		messageInfo.putValue("playerName", joinedPlayerName);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleJoinEditorSession(messageInfo);

		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(editorSessionName);

		MessageEnvelope messageEnvelope = messageQueueStub.messages.get(0);
		MessageInformation response = messageEnvelope.getMessageInformation();
		assertEquals(messageType, messageEnvelope.getMessageType());
		assertEquals(editorSessionName, response.getValueAsString("editorName"));
		assertEquals(editorSessionName, response.getValueAsString("topicName"));

		@SuppressWarnings("unchecked")
		List<JsonObject> playerList = (List<JsonObject>) response.getValue("playerList");

		JsonObject hostPlayerData = playerList.get(0);
		Player hostPlayer = editorSession.getPlayerList().get(0);
		assertEquals(hostPlayer.getUUID().toString(), hostPlayerData.get("playerId").getAsString());
		assertEquals(hostPlayer.getPlayerName(), hostPlayerData.get("playerName").getAsString());
		assertEquals(hostPlayer.getIsHost(), hostPlayerData.get("isHost").getAsBoolean());

		JsonObject joinedPlayerData = playerList.get(1);
		Player joinedPlayer = editorSession.getPlayerList().get(1);
		assertEquals(joinedPlayer.getUUID().toString(), joinedPlayerData.get("playerId").getAsString());
		assertEquals(joinedPlayer.getPlayerName(), joinedPlayerData.get("playerName").getAsString());
		assertEquals(joinedPlayer.getIsHost(), joinedPlayerData.get("isHost").getAsBoolean());
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleJoinGameSession_createsNewPlayerWithRightName() {
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		String messageType = "JoinGameSession";
		String gameSessionName = "TestSession";
		String joinedPlayerName = "MyPlayer";

		GameSessionManager.getInstance().createNewGameSession(gameSessionName, UUID.randomUUID(), "host");
		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.putValue("gameName", gameSessionName);
		messageInfo.putValue("playerName", joinedPlayerName);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleJoinGameSession(messageInfo);

		GameSession gameSession = GameSessionManager.getInstance().getGameSessionByName(gameSessionName);

		assertEquals(2, gameSession.getPlayerList().size());

		Player joinedPlayer = gameSession.getPlayerList().stream().filter(x -> x.getPlayerName().equals(joinedPlayerName))
				.findFirst().get();
		assertNotNull(joinedPlayer);
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleJoinGameSession_createsResponseWithExpectedValues() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.registerObserver(messageQueueStub);
		String messageType = "JoinGameSession";
		String gameSessionName = "TestSession";
		String joinedPlayerName = "MyPlayer";

		EditorSessionManager.getInstance().createEditorSession(gameSessionName, UUID.randomUUID(), "host");
		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.putValue("gameName", gameSessionName);
		messageInfo.putValue("playerName", joinedPlayerName);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleJoinGameSession(messageInfo);

		GameSession gameSession = GameSessionManager.getInstance().getGameSessionByName(gameSessionName);

		MessageEnvelope messageEnvelope = messageQueueStub.messages.get(0);
		MessageInformation response = messageEnvelope.getMessageInformation();
		assertEquals(messageType, messageEnvelope.getMessageType());
		assertEquals(gameSessionName, response.getValueAsString("gameName"));
		assertEquals(gameSessionName, response.getValueAsString("topicName"));

		@SuppressWarnings("unchecked")
		List<JsonObject> playerList = (List<JsonObject>) response.getValue("playerList");

		JsonObject hostPlayerData = playerList.get(0);
		Player hostPlayer = gameSession.getPlayerList().get(0);
		assertEquals(hostPlayer.getUUID().toString(), hostPlayerData.get("playerId").getAsString());
		assertEquals(hostPlayer.getPlayerName(), hostPlayerData.get("playerName").getAsString());
		assertEquals(hostPlayer.getIsHost(), hostPlayerData.get("isHost").getAsBoolean());

		JsonObject joinedPlayerData = playerList.get(1);
		Player joinedPlayer = gameSession.getPlayerList().get(1);
		assertEquals(joinedPlayer.getUUID().toString(), joinedPlayerData.get("playerId").getAsString());
		assertEquals(joinedPlayer.getPlayerName(), joinedPlayerData.get("playerName").getAsString());
		assertEquals(joinedPlayer.getIsHost(), joinedPlayerData.get("isHost").getAsBoolean());
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleReadGameSessions_createsResonseWithExpectedValues() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.registerObserver(messageQueueStub);
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
		dispatcher.registerObserver(messageQueueStub);
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
		dispatcher.registerObserver(messageQueueStub);
		String messageType = "ReadEditorSessions";
		String editorSessionName = "TestSession";
		String hostPlayerName = "HostPlayer";

		EditorSessionManager.getInstance().createEditorSession(editorSessionName, UUID.randomUUID(), hostPlayerName);
		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleReadEditorSessions(messageInfo);

		MessageEnvelope messageEnvelope = messageQueueStub.messages.get(0);
		MessageInformation response = messageEnvelope.getMessageInformation();
		assertEquals(messageType, messageEnvelope.getMessageType());

		@SuppressWarnings("unchecked")
		List<JsonObject> editorSessionInfos = (List<JsonObject>) response.getValue("editorSessionInfo");
		JsonObject editorSessionInfo = editorSessionInfos.stream()
				.filter(x -> x.get("name").getAsString().equals(editorSessionName)).findFirst().get();
		assertEquals(editorSessionName, editorSessionInfo.get("name").getAsString());
		assertEquals(hostPlayerName, editorSessionInfo.get("hostname").getAsString());
		assertEquals(1, editorSessionInfo.get("amountOfPlayers").getAsInt());
	}
}
