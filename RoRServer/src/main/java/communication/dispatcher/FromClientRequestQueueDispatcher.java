package communication.dispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;
import communication.MessageEnvelope;
import communication.MessageInformation;
import communication.queue.QueueMessageQueue;
import exceptions.MapNotFoundException;
import models.game.GamePlayer;
import models.game.Player;
import models.session.EditorSession;
import models.session.EditorSessionManager;
import models.session.GameSession;
import models.session.GameSessionManager;
import persistent.MapManager;
import resources.PropertyManager;

public class FromClientRequestQueueDispatcher extends DispatcherBase {

	public FromClientRequestQueueDispatcher() {
		addObserver(QueueMessageQueue.getInstance());
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

		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(editorSessionName,
				playerId, playerName);
		
		if(!editorSessionName.equals("TestSession")) {
			editorSession.setup();
		}

		sendCreateEditorSessionCommand(messageInformation.getClientid(), editorSession);
	}

	private void sendCreateEditorSessionCommand(String cliendId, EditorSession editorSession) {
		MessageInformation responseInformation = new MessageInformation("CreateEditorSession");
		responseInformation.setClientid(cliendId);
		responseInformation.putValue("topicName", editorSession.getSessionName());
		responseInformation.putValue("editorName", editorSession.getSessionName());
		Player hostPlayer = editorSession.getHost();
		responseInformation.putValue("playerName", hostPlayer.getDescription());
		responseInformation.putValue("playerId", hostPlayer.getId().toString());

		sendMessage(responseInformation);
	}

	public void handleJoinEditorSession(MessageInformation messageInformation) {
		String editorSessionName = messageInformation.getValueAsString("editorName");
		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(editorSessionName);
		String clientId = messageInformation.getClientid();
		
		if(editorSession == null) {
			sendErrorMessage(clientId, "SessionNotFound");
			return;
		}
		
		if(editorSession.isStarted()) {
			sendErrorMessage(clientId, "SessionAlreadyStarted");
			return;
		}

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
			json.addProperty("playerName", sessionPlayer.getDescription());
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
		String playerName = messageInformation.getValueAsString("playerName");
		UUID playerId = UUID.fromString(messageInformation.getClientid());
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(gameSessionName, playerId,
				playerName);
		
		if(!gameSessionName.equals("TestSession")) {
			gameSession.setup();
		}

		sendCreateGameSessionCommand(messageInformation.getClientid(), gameSession);
	}

	private void sendCreateGameSessionCommand(String clientId, GameSession gameSession) {
		GamePlayer gamePlayer = (GamePlayer)gameSession.getHost();
		
		MessageInformation responseInformation = new MessageInformation("CreateGameSession");
		responseInformation.setClientid(clientId);
		responseInformation.putValue("topicName", gameSession.getSessionName());
		responseInformation.putValue("gameName", gameSession.getSessionName());
		responseInformation.putValue("playerName", gamePlayer.getDescription());
		responseInformation.putValue("playerId", gamePlayer.getId().toString());
		
		// initial resources
		responseInformation.putValue("coalCount", gamePlayer.getCoalCount());
		responseInformation.putValue("goldCount", gamePlayer.getGoldCount());
		responseInformation.putValue("pointCount", gamePlayer.getPointCount());

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
		String clientId = messageInformation.getClientid();
		
		if(gameSession == null) {
			sendErrorMessage(clientId, "SessionNotFound");
			return;
		}
		
		if(gameSession.isStarted()) {
			sendErrorMessage(clientId, "SessionAlreadyStarted");
			return;
		}
		
		
		if (gameSession.isFull()) {
			sendErrorMessage(clientId, "SessionAlreadyFull");
			return;
		}
				
		String playerName = messageInformation.getValueAsString("playerName");
		UUID playerId = UUID.fromString(messageInformation.getClientid());
		gameSession.createPlayer(playerId, playerName);

		sendJoinGameSessionCommand(messageInformation.getClientid(), gameSession);
	}
	
	private void sendErrorMessage(String clientId, String type) {
		MessageInformation responseInformation = new MessageInformation("Error");
		responseInformation.setClientid(clientId);
		responseInformation.putValue("type", type);
		sendMessage(responseInformation);
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
			json.addProperty("playerName", sessionPlayer.getDescription());
			json.addProperty("isHost", sessionPlayer.getIsHost());
			players.add(json);
		}
		responseInformation.putValue("playerList", players);
		
		// initial resources
		GamePlayer gamePlayer = (GamePlayer)gameSession.getHost();
		responseInformation.putValue("coalCount", gamePlayer.getCoalCount());
		responseInformation.putValue("goldCount", gamePlayer.getGoldCount());
		responseInformation.putValue("pointCount", gamePlayer.getPointCount());

		sendMessage(responseInformation);
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
				json.addProperty("hostname", session.getHost().getDescription());
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
			if (!session.isStarted()) {
				JsonObject json = new JsonObject();
				json.addProperty("name", session.getSessionName());
				json.addProperty("amountOfPlayers", session.getPlayers().size());
				json.addProperty("hostname", session.getHost().getDescription());
				json.addProperty("availablePlayerSlots", session.getAvailablePlayerSlots());
				gameSessionInfos.add(json);
			}
		}

		responseInformation.putValue("gameSessionInfo", gameSessionInfos);

		sendMessage(responseInformation);
	}
	
	public void handleReadGameInfos(MessageInformation messageInformation) {
		MessageInformation responseInformation = new MessageInformation("ReadGameInfos");
		responseInformation.setClientid(messageInformation.getClientid());

		List<JsonObject> gameInfos = new ArrayList<JsonObject>();
		String sessionName = messageInformation.getValueAsString("sessionName");
		GameSession gameSession = GameSessionManager.getInstance().getGameSessionByName(sessionName);
		List<Player> gamePlayers = gameSession.getPlayers();
		for (Player player : gamePlayers) {
			JsonObject json = new JsonObject();
			json.addProperty("playerId", player.getId().toString());
			gameInfos.add(json);
		}

		responseInformation.putValue("gameInfo", gameInfos);
		sendMessage(responseInformation);
	}
	
	public void handleReadEditorInfos(MessageInformation messageInformation) {
		MessageInformation responseInformation = new MessageInformation("ReadEditorInfos");
		responseInformation.setClientid(messageInformation.getClientid());

		List<JsonObject> editorInfos = new ArrayList<JsonObject>();
		String sessionName = messageInformation.getValueAsString("sessionName");
		List<Player> editorPlayers = EditorSessionManager.getInstance().getEditorSessionByName(sessionName).getPlayers();
		for (Player player : editorPlayers) {
			JsonObject json = new JsonObject();
			json.addProperty("playerId", player.getId().toString());
			editorInfos.add(json);
		}

		responseInformation.putValue("editorInfo", editorInfos);
		sendMessage(responseInformation);
	}
	
	/**
	 * Sendet eine JsonObject-Liste mit MapNames an den Client
	 * @param messageInformation
	 */
	public void handleReadMapInfos(MessageInformation messageInformation) {
		MessageInformation responseInformation = new MessageInformation("ReadMapInfos");
		responseInformation.setClientid(messageInformation.getClientid());

		List<JsonObject> mapInfos = new ArrayList<JsonObject>();
		List<String> mapNames = MapManager.readMapNames();
		for (String mapName : mapNames) {
			JsonObject json = new JsonObject();
			json.addProperty("mapName", mapName);
			mapInfos.add(json);
		}
		
		// wenn die Session des Clients eine EditorSession ist
		// wird ein MapName für das erstellen einer neuen Map hinzugefügt
		EditorSessionManager possibleEditorSessionManager = EditorSessionManager.getInstance();
		if(possibleEditorSessionManager.getEditorSessionByName(messageInformation.getValueAsString("sessionName")) != null){
			JsonObject json = new JsonObject();
			json.addProperty("mapName", PropertyManager.getProperty("newMap"));
			mapInfos.add(json);
		}

		responseInformation.putValue("mapInfo", mapInfos);
		sendMessage(responseInformation);
	}
}