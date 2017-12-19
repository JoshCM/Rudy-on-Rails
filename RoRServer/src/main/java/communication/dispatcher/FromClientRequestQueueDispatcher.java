package communication.dispatcher;

import communication.MessageInformation;
import communication.queue.sender.QueueSender;
import models.game.Player;
import models.session.EditorSession;
import models.session.EditorSessionManager;
import models.session.GameSession;
import models.session.GameSessionManager;
import models.session.RoRSession;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import commands.game.CreateLocoCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FromClientRequestQueueDispatcher extends DispatcherBase {
	private Logger log = Logger.getLogger(FromClientRequestQueueDispatcher.class.getName());

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
		EditorSession editorSession;
		MessageInformation responseInformation = new MessageInformation("CreateEditorSession");
		responseInformation.setClientid(messageInformation.getClientid());

		editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(messageInformation.getValueAsString("editorName"));
		editorSession.setup();
		
		String editorSessionName = editorSession.getName();
		String playerName = messageInformation.getValueAsString("playerName");
		Player player = new Player(editorSessionName, playerName, true);
		editorSession.addPlayer(player);

		responseInformation.putValue("topicName", editorSession.getName());
		responseInformation.putValue("editorName", editorSession.getName());
		responseInformation.putValue("playerName", player.getName());
		responseInformation.putValue("playerId", player.getId().toString());
		sendMessage("CreateEditorSession", responseInformation);

		log.info("Called handleCreateEditorSession");
	}

	public void handleJoinEditorSession(MessageInformation messageInformation) {
		EditorSession editorSession;
		MessageInformation responseInformation = new MessageInformation("JoinEditorSession");
		responseInformation.setClientid(messageInformation.getClientid());

		editorSession = EditorSessionManager.getInstance()
				.getEditorSessionByName(messageInformation.getValueAsString("editorName"));
		Player player = new Player(editorSession.getName(), messageInformation.getValueAsString("playerName"));
		editorSession.addPlayer(player);

		responseInformation.putValue("topicName", editorSession.getName());
		responseInformation.putValue("editorName", editorSession.getName());
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
		GameSession gameSession;
		MessageInformation responseInformation = new MessageInformation("CreateGameSession");
		responseInformation.setClientid(messageInformation.getClientid());

		String gameName = messageInformation.getValueAsString("gameName");
		gameSession = GameSessionManager.getInstance().createNewGameSession(gameName);
		gameSession.setup();
		
		String playerName = messageInformation.getValueAsString("playerName");
		Player player = new Player(gameSession.getName(), playerName, true);
		gameSession.addPlayer(player);
		
		responseInformation.putValue("topicName", gameSession.getName());
		responseInformation.putValue("gameName", gameSession.getName());
		responseInformation.putValue("playerName", player.getName());
		responseInformation.putValue("playerId", player.getId().toString());
		sendMessage("CreateGameSession", responseInformation);
		
		log.info("Called handleCreateGameSession");
	}

	/**
	 * Es gibt schon einen Game mit mind. einen Player. Diesem wird ein neuer Player
	 * hinzugefuegt und eine Response wird für den Client zusammengesetzt.
	 * 
	 * @param messageInformation
	 */
	public void handleJoinGameSession(MessageInformation messageInformation) {
		GameSession gameSession;
		MessageInformation responseInformation = new MessageInformation("JoinGameSession");
		responseInformation.setClientid(messageInformation.getClientid());

		String gameName = messageInformation.getValueAsString("gameName");
		gameSession = GameSessionManager.getInstance().getGameSessionByName(gameName);
		Player player = new Player(gameSession.getName(), messageInformation.getValueAsString("playerName"));
		gameSession.addPlayer(player);

		responseInformation.putValue("topicName", gameSession.getName());
		responseInformation.putValue("gameName", gameSession.getName());
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
			JsonObject json = new JsonObject();
			json.addProperty("name", session.getName());
			json.addProperty("amountOfPlayers", session.getPlayers().size());
			json.addProperty("hostname", session.getHost().getName());
			editorSessionInfos.add(json);
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
			JsonObject json = new JsonObject();
			json.addProperty("name", session.getName());
			json.addProperty("amountOfPlayers", session.getPlayers().size());
			json.addProperty("hostname", session.getHost().getName());
			gameSessionInfos.add(json);
		}

		responseInformation.putValue("gameSessionInfo", gameSessionInfos);

		sendMessage("ReadGameSessions", responseInformation);
	}
}