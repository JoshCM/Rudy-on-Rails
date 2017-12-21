package communication.dispatcher;

import communication.MessageInformation;
import communication.queue.sender.QueueSender;
import models.game.Player;
import models.session.EditorSession;
import models.session.EditorSessionManager;
import models.session.GameSession;
import models.session.GameSessionManager;
import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FromClientRequestQueueDispatcher extends DispatcherBase {
	private void sendMessage(String messageType, MessageInformation messageInformation) {
		QueueSender queueSender = new QueueSender(messageInformation.getClientid());
		queueSender.setup();
		queueSender.sendMessage(messageType, messageInformation);
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

		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(editorSessionName,
				playerId, playerName);
		editorSession.setup();

		sendCreateEditorSessionCommand(messageInformation.getClientid(), editorSession);
	}

	private void sendCreateEditorSessionCommand(String cliendId, EditorSession editorSession) {
		MessageInformation responseInformation = new MessageInformation("CreateEditorSession");
		responseInformation.setClientid(cliendId);
		responseInformation.putValue("topicName", editorSession.getSessionName());
		responseInformation.putValue("editorName", editorSession.getSessionName());
		Player hostPlayer = editorSession.getHost();
		responseInformation.putValue("playerName", hostPlayer.getName());
		responseInformation.putValue("playerId", hostPlayer.getId().toString());
		sendMessage("CreateEditorSession", responseInformation);
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
		responseInformation.putValue("topicName", editorSession.getSessionName());
		responseInformation.putValue("editorName", editorSession.getSessionName());

		List<JsonObject> players = new ArrayList<JsonObject>();
		for (Player sessionPlayer : editorSession.getPlayers()) {
			JsonObject json = new JsonObject();
			json.addProperty("playerId", sessionPlayer.getId().toString());
			json.addProperty("playerName", sessionPlayer.getName());
			json.addProperty("isHost", sessionPlayer.getIsHost());
			players.add(json);
		}
		responseInformation.putValue("playerList", players);

		sendMessage("JoinEditorSession", responseInformation);
	}

	/**
	 * Neue GameSession wird erstellt und dem Client mitgeteilt
	 * 
	 * @param messageInformation
	 */
	public void handleCreateGameSession(MessageInformation messageInformation) {
		String gameSessionName = messageInformation.getValueAsString("gameName");
		String playerName = messageInformation.getValueAsString("playerName");
		UUID playerId = UUID.fromString(messageInformation.getClientid());
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(gameSessionName, playerId,
				playerName);
		gameSession.setup();

		sendCreateGameSessionCommand(messageInformation.getClientid(), gameSession);
	}

	private void sendCreateGameSessionCommand(String clientId, GameSession gameSession) {
		MessageInformation responseInformation = new MessageInformation("CreateGameSession");
		responseInformation.setClientid(clientId);
		responseInformation.putValue("topicName", gameSession.getSessionName());
		responseInformation.putValue("gameName", gameSession.getSessionName());
		responseInformation.putValue("playerName", gameSession.getHost().getName());
		responseInformation.putValue("playerId", gameSession.getHost().getId().toString());
		sendMessage("CreateGameSession", responseInformation);
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
		responseInformation.putValue("topicName", gameSession.getSessionName());
		responseInformation.putValue("gameName", gameSession.getSessionName());
		List<JsonObject> players = new ArrayList<JsonObject>();
		for (Player sessionPlayer : gameSession.getPlayers()) {
			JsonObject json = new JsonObject();
			json.addProperty("playerId", sessionPlayer.getId().toString());
			json.addProperty("playerName", sessionPlayer.getName());
			json.addProperty("isHost", sessionPlayer.getIsHost());
			players.add(json);
		}
		responseInformation.putValue("playerList", players);

		sendMessage("JoinGameSession", responseInformation);
	}

	public void handleReadEditorSessions(MessageInformation messageInformation) {
		MessageInformation responseInformation = new MessageInformation("ReadEditorSessions");
		responseInformation.setClientid(messageInformation.getClientid());

		List<JsonObject> editorSessionInfos = new ArrayList<JsonObject>();
		List<EditorSession> editorSessions = EditorSessionManager.getInstance().getEditorSessionsAsList();
		for (EditorSession session : editorSessions) {
			if (!session.isStarted()) {
				JsonObject json = new JsonObject();
				json.addProperty("name", session.getSessionName());
				json.addProperty("amountOfPlayers", session.getPlayers().size());
				json.addProperty("hostname", session.getHost().getName());
				editorSessionInfos.add(json);
			}
		}

		responseInformation.putValue("editorSessionInfo", editorSessionInfos);

		sendMessage("ReadEditorSessions", responseInformation);
	}

	public void handleReadGameSessions(MessageInformation messageInformation) {
		MessageInformation responseInformation = new MessageInformation("ReadGameSessions");
		responseInformation.setClientid(messageInformation.getClientid());

		List<JsonObject> gameSessionInfos = new ArrayList<JsonObject>();
		List<GameSession> gameSessions = GameSessionManager.getInstance().getGameSessionsAsList();
		for (GameSession session : gameSessions) {
			if (!session.isStarted()) {
				JsonObject json = new JsonObject();
				json.addProperty("name", session.getSessionName());
				json.addProperty("amountOfPlayers", session.getPlayers().size());
				json.addProperty("hostname", session.getHost().getName());
				gameSessionInfos.add(json);
			}
		}

		responseInformation.putValue("gameSessionInfo", gameSessionInfos);

		sendMessage("ReadGameSessions", responseInformation);
	}
}