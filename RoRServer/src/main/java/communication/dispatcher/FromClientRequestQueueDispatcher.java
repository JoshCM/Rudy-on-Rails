package communication.dispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import communication.MessageEnvelope;
import communication.MessageInformation;
import communication.queue.QueueMessageQueue;
import models.game.Player;
import models.session.*;
import models.session.EditorSession;
import models.session.GameSession;

public class FromClientRequestQueueDispatcher extends DispatcherBase {

	public FromClientRequestQueueDispatcher() {
		registerObserver(QueueMessageQueue.getInstance());
	}

	private void sendMessage(MessageInformation messageInformation) {
		MessageEnvelope messageEnvelope = new MessageEnvelope(messageInformation.getClientid(),
				messageInformation.getMessageType(), messageInformation);
		setChanged();
		notifyObservers(messageEnvelope);
	}

	/**
	 * Neue EditorSession wird erstellt und dem Client mitgeteilt Ist schon eine
	 * EditorSession offen, dann werden diesem Client alle Infos zur EditorSession
	 * sowie alle bereits existierenden Player-Informationen zugesendet
	 * 
	 * @param messageInformation
	 */
	public void handleCreateEditorSession(MessageInformation messageInformation) {
		String editorSessionName = messageInformation.getValueAsString("editorName");
		String playerName = messageInformation.getValueAsString("playerName");
		UUID playerId = UUID.fromString(messageInformation.getClientid());

		EditorSession editorSession = EditorSessionManager.getInstance().createEditorSession(editorSessionName);
		
		if(!editorSessionName.equals("TestSession")) {
			editorSession.setupQueueReceiver();
		}

		sendCreateEditorSessionCommand(messageInformation.getClientid(), editorSession);
	}

	private void sendCreateEditorSessionCommand(String cliendId, EditorSession editorSession) {
		MessageInformation responseInformation = new MessageInformation("CreateEditorSession");
		responseInformation.setClientid(cliendId);
		responseInformation.putValue("topicName", editorSession.getName());
		responseInformation.putValue("editorName", editorSession.getName());
		Player hostPlayer = editorSession.getHost();
		responseInformation.putValue("playerName", hostPlayer.getPlayerName());
		responseInformation.putValue("playerId", hostPlayer.getID().toString());

		sendMessage(responseInformation);
	}

	public void handleJoinEditorSession(MessageInformation messageInformation) {
		String editorSessionName = messageInformation.getValueAsString("editorName");
		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(editorSessionName);

		String playerName = messageInformation.getValueAsString("playerName");
		UUID playerId = UUID.fromString(messageInformation.getClientid());
		editorSession.createPlayer(playerId, playerName);

		sendJoinEditorSessionCommand(messageInformation.getClientid(), editorSession);
	}

	private void sendJoinEditorSessionCommand(String clientId, EditorSession editorSession) {
		MessageInformation responseInformation = new MessageInformation("JoinEditorSession");
		responseInformation.setClientid(clientId);
		responseInformation.putValue("topicName", editorSession.getName());
		responseInformation.putValue("editorName", editorSession.getName());

		List<JsonObject> players = new ArrayList<JsonObject>();
		for (Player sessionPlayer : editorSession.getPlayerList()) {
			JsonObject json = new JsonObject();
			json.addProperty("playerId", sessionPlayer.getID().toString());
			json.addProperty("playerName", sessionPlayer.getPlayerName());
			json.addProperty("isHost", sessionPlayer.getIsHost());
			players.add(json);
		}
		responseInformation.putValue("playerList", players);

		sendMessage(responseInformation);
	}

	/**
	 * Neue GameSession wird erstellt und dem Client mitgeteilt
	 * 
	 * @param messageInformation
	 */
	public void handleCreateGameSession(MessageInformation messageInformation) {
		String gameSessionName = messageInformation.getValueAsString("gameName");
		String hostPlayer = messageInformation.getValueAsString("playerName");
		UUID playerId = UUID.fromString(messageInformation.getClientid());
		GameSession gameSession = GameSessionManager.getInstance().createGameSession(gameSessionName,
				hostPlayer);
		
		if(!gameSessionName.equals("TestSession")) {
			gameSession.setupQueueReceiver();
		}

		sendCreateGameSessionCommand(messageInformation.getClientid(), gameSession);
	}

	private void sendCreateGameSessionCommand(String clientId, GameSession gameSession) {
		MessageInformation responseInformation = new MessageInformation("CreateGameSession");
		responseInformation.setClientid(clientId);
		responseInformation.putValue("topicName", gameSession.getName());
		responseInformation.putValue("gameName", gameSession.getName());
		responseInformation.putValue("playerName", gameSession.getHost().getPlayerName());
		responseInformation.putValue("playerId", gameSession.getHost().getID().toString());

		sendMessage(responseInformation);
	}

	/**
	 * Es gibt schon einen Game mit mind. einen Player. Diesem wird ein neuer Player
	 * hinzugefuegt und eine Response wird für den Client zusammengesetzt.
	 * 
	 * @param messageInformation
	 */
	public void handleJoinGameSession(MessageInformation messageInformation) {
		String gameSessionName = messageInformation.getValueAsString("gameName");
		GameSession gameSession = GameSessionManager.getInstance().getGameSessionByName(gameSessionName);

		String playerName = messageInformation.getValueAsString("playerName");
		UUID playerId = UUID.fromString(messageInformation.getClientid());
		gameSession.createPlayer(playerId, playerName);

		sendJoinGameSessionCommand(messageInformation.getClientid(), gameSession);
	}

	private void sendJoinGameSessionCommand(String clientId, GameSession gameSession) {
		MessageInformation responseInformation = new MessageInformation("JoinGameSession");
		responseInformation.setClientid(clientId);
		responseInformation.putValue("topicName", gameSession.getName());
		responseInformation.putValue("gameName", gameSession.getName());
		List<JsonObject> players = new ArrayList<JsonObject>();
		for (Player sessionPlayer : gameSession.getPlayerList()) {
			JsonObject json = new JsonObject();
			json.addProperty("playerId", sessionPlayer.getID().toString());
			json.addProperty("playerName", sessionPlayer.getPlayerName());
			json.addProperty("isHost", sessionPlayer.getIsHost());
			players.add(json);
		}
		responseInformation.putValue("playerList", players);

		sendMessage(responseInformation);
	}

	public void handleReadEditorSessions(MessageInformation messageInformation) {
		MessageInformation responseInformation = new MessageInformation("ReadEditorSessions");
		responseInformation.setClientid(messageInformation.getClientid());

		List<JsonObject> editorSessionInfos = new ArrayList<JsonObject>();
		List<EditorSession> editorSessions = EditorSessionManager.getInstance().getEditorSessionsAsList();
		for (EditorSession session : editorSessions) {
			if (!session.isRunning()) {
				JsonObject json = new JsonObject();
				json.addProperty("name", session.getName());
				json.addProperty("amountOfPlayers", session.getPlayerList().size());
				json.addProperty("hostname", session.getHost().getPlayerName());
				editorSessionInfos.add(json);
			}
		}

		responseInformation.putValue("editorSessionInfo", editorSessionInfos);

		sendMessage(responseInformation);
	}

	public void handleReadGameSessions(MessageInformation messageInformation) {
		MessageInformation responseInformation = new MessageInformation("ReadGameSessions");
		responseInformation.setClientid(messageInformation.getClientid());

		List<JsonObject> gameSessionInfos = new ArrayList<JsonObject>();
		List<GameSession> gameSessions = GameSessionManager.getInstance().getGameSessionsAsList();
		for (GameSession session : gameSessions) {
			if (!session.isRunning()) {
				JsonObject json = new JsonObject();
				json.addProperty("name", session.getName());
				json.addProperty("amountOfPlayers", session.getPlayerList().size());
				json.addProperty("hostname", session.getHost().getPlayerName());
				gameSessionInfos.add(json);
			}
		}

		responseInformation.putValue("gameSessionInfo", gameSessionInfos);

		sendMessage(responseInformation);
	}
}