package communication.dispatcher;

import communication.MessageInformation;
import communication.queue.sender.QueueSender;
import models.game.Player;
import models.session.EditorSession;
import models.session.EditorSessionManager;

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
	 * Dies soll später getrennt laufen: CreateEditorSession ODER ReadEditorSession + JoinEditorSession
	 * 
	 * @param messageInformation
	 */
	public void handleCreateEditorSession(MessageInformation messageInformation) {
		EditorSession editorSession;

		if (EditorSessionManager.getInstance().getEditorSession() == null) {
			createEditorSessionAndSendResponse(messageInformation);
		} else {
			joinEditorSessionAndSendResponse(messageInformation);
		}

		log.info("Called handleCreateEditorSession");
	}

	private void joinEditorSessionAndSendResponse(MessageInformation messageInformation) {
		EditorSession editorSession;
		MessageInformation responseInformation = new MessageInformation("JoinEditorSession");
		responseInformation.setClientid(messageInformation.getClientid());
		
		editorSession = EditorSessionManager.getInstance().getEditorSession();
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
}