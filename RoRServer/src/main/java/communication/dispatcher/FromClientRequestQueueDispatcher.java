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

public class FromClientRequestQueueDispatcher extends DispatcherBase {
	private Logger log = Logger.getLogger(FromClientRequestQueueDispatcher.class.getName());

	private void sendMessage(String messageType, MessageInformation messageInformation) {
		QueueSender queueSender = new QueueSender(messageInformation.getClientid());
		queueSender.setup();
		queueSender.sendMessage(messageType, messageInformation);
	}

	/**
	 * Neue EditorSession wird erstellt und dem Client mitgeteilt
	 * Ist schon eine EditorSession offen, dann werden diesem Client alle Infos zur EditorSession 
	 * sowie alle bereits existierenden Player-Informationen zugesendet
	 * 
	 * @param messageInformation
	 */
	public void handleCreateEditorSession(MessageInformation messageInformation) {
		if (EditorSessionManager.getInstance().getFirstEditorSession() == null) {
			createEditorSessionAndSendResponse(messageInformation);
		} else {
			EditorSession editorSession = EditorSessionManager.getInstance().getFirstEditorSession();
			messageInformation.putValue("editorName", editorSession.getName());
			handleJoinEditorSession(messageInformation);
		}

		log.info("Called handleCreateEditorSession");
	}
	
	public void handleJoinEditorSession(MessageInformation messageInformation) {
		EditorSession editorSession;
		MessageInformation responseInformation = new MessageInformation("JoinEditorSession");
		responseInformation.setClientid(messageInformation.getClientid());
		
		editorSession = EditorSessionManager.getInstance().getEditorSessionByName(messageInformation.getValueAsString("editorName"));
		Player player = new Player(editorSession.getName(), messageInformation.getValueAsString("playerName"));
		editorSession.addPlayer(player);
		
		responseInformation.putValue("topicName", editorSession.getName());
		responseInformation.putValue("editorName", editorSession.getName());
		List<JsonObject> players = new ArrayList<JsonObject>();
		for(Player sessionPlayer : editorSession.getPlayers()) {
			JsonObject json = new JsonObject();
			json.addProperty("playerId", sessionPlayer.getId().toString());
			json.addProperty("playerName", sessionPlayer.getName());
			players.add(json);
		}
		
		responseInformation.putValue("playerList", players);
		
		sendMessage("JoinEditorSession", responseInformation);
	}
	
	/**
	 * Neue GameSession wird erstellt und dem Client mitgeteilt
	 * Ist schon eine GameSession offen, dann werden diesem Client alle Infos zur GameSession 
	 * sowie alle bereits existierenden Player-Informationen zugesendet
	 * 
	 * @param messageInformation
	 */
	public void handleCreateGameSession(MessageInformation messageInformation) {

		if (GameSessionManager.getInstance().getGameSession() == null) {
			createGameSessionAndSendResponse(messageInformation);
		} else {
			joinGameSessionAndSendResponse(messageInformation);
		}

		log.info("Called handleCreateGameSession");
	}
	/**
	 * Es gibt schon einen Game mit mind. einem Player. Diesem wird ein neuer Player hinzugefuegt und eine Response 
	 * wird f�r den Client zusammengesetzt.
	 * @param messageInformation
	 */
	private void joinGameSessionAndSendResponse(MessageInformation messageInformation) {
		GameSession gameSession;
		MessageInformation responseInformation = new MessageInformation("JoinGameSession");
		responseInformation.setClientid(messageInformation.getClientid());
		
		gameSession = GameSessionManager.getInstance().getGameSession();
		Player player = new Player(gameSession.getName(), messageInformation.getValueAsString("playerName"));
		gameSession.addPlayer(player);
		
		responseInformation.putValue("topicName", gameSession.getName());
		responseInformation.putValue("gameName", gameSession.getName());
		List<JsonObject> players = new ArrayList<JsonObject>();
		for(Player sessionPlayer : gameSession.getPlayers()) {
			JsonObject json = new JsonObject();
			json.addProperty("playerId", sessionPlayer.getId().toString());
			json.addProperty("playerName", sessionPlayer.getName());
			players.add(json);
		}
		
		responseInformation.putValue("playerList", players);
		
		sendMessage("JoinGameSession", responseInformation);
	}
	/**
	 * EditorSession mit einem Player wird erstellt und Antwortnachricht wird zusammengesetzt
	 * @param messageInformation
	 */
	private void createEditorSessionAndSendResponse(MessageInformation messageInformation) {
		EditorSession editorSession;
		MessageInformation responseInformation = new MessageInformation("CreateEditorSession");
		responseInformation.setClientid(messageInformation.getClientid());
		
		editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(messageInformation.getValueAsString("editorName"));
		editorSession.setup();
		Player player = new Player(editorSession.getName(), messageInformation.getValueAsString("playerName"));
		editorSession.addPlayer(player);
		
		responseInformation.putValue("topicName", editorSession.getName());
		responseInformation.putValue("editorName", editorSession.getName());
		responseInformation.putValue("playerName", player.getName());		
		responseInformation.putValue("playerId", player.getId().toString());
		sendMessage("CreateEditorSession", responseInformation);
	}
	
	/**
	 * GameSession mit einem Player wird erstellt und Antwortnachricht wird zusammengesetzt
	 * @param messageInformation
	 */
	private void createGameSessionAndSendResponse(MessageInformation messageInformation) {
		GameSession gameSession;
		MessageInformation responseInformation = new MessageInformation("CreateGameSession");
		responseInformation.setClientid(messageInformation.getClientid());
		
		gameSession = GameSessionManager.getInstance()
				.createNewGameSession(messageInformation.getValueAsString("gameName"));
		gameSession.setup();
		Player player = new Player(gameSession.getName(), messageInformation.getValueAsString("playerName"));
		gameSession.addPlayer(player);
		
		responseInformation.putValue("topicName", gameSession.getName());
		responseInformation.putValue("gameName", gameSession.getName());
		responseInformation.putValue("playerName", player.getName());		
		responseInformation.putValue("playerId", player.getId().toString());
		sendMessage("CreateGameSession", responseInformation);
	}
	
	public void handleReadEditorSessions(MessageInformation messageInformation) {
		MessageInformation responseInformation = new MessageInformation("ReadEditorSessions");
		responseInformation.setClientid(messageInformation.getClientid());
		
		List<JsonObject> editorSessionInfos = new ArrayList<JsonObject>();
		List<EditorSession> editorSessions = EditorSessionManager.getInstance().getEditorSessionsAsList();
		for(EditorSession session : editorSessions) {
			JsonObject json = new JsonObject();
			json.addProperty("name", session.getName());
			json.addProperty("amountOfPlayers", session.getPlayers().size());
			editorSessionInfos.add(json);
		}
		
		responseInformation.putValue("editorSessionInfo", editorSessionInfos);
		
		sendMessage("ReadEditorSessions", responseInformation);
	}
}