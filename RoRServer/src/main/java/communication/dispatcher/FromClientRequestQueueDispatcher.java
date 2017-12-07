package communication.dispatcher;

import communication.MessageInformation;
import communication.queue.sender.QueueSender;
import models.editor.EditorSession;
import models.editor.EditorSessionManager;
import models.game.Player;
import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FromClientRequestQueueDispatcher extends DispatcherBase {
	private Logger log = Logger.getLogger(FromClientRequestQueueDispatcher.class.getName());

	private void sendMessage(String messageType, MessageInformation messageInformation) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		String response = requestSerializer.serialize(messageInformation);
		QueueSender queueSender = new QueueSender(messageInformation.getClientid());
		queueSender.setup();
		queueSender.sendMessage(messageType, response);
	}

	/**
	 * Neuer Player wird erstellt und allen angemeldeten Clients mitgeteilt
	 * 
	 * @param messageInformation
	 */
	public void handleCreateEditorSession(MessageInformation messageInformation) {
		EditorSession editorSession;


		if (EditorSessionManager.getInstance().getEditorSession() == null) {
			MessageInformation responseInformation = new MessageInformation("CreateEditorSession");
			responseInformation.setClientid(messageInformation.getClientid());
			
			editorSession = EditorSessionManager.getInstance()
					.createNewEditorSession(messageInformation.getValueAsString("Editorname"));
			editorSession.setup();
			Player player = new Player(editorSession, messageInformation.getValueAsString("Playername"));
			editorSession.addPlayer(player);
			
			responseInformation.putValue("topicName", editorSession.getName());
			responseInformation.putValue("editorName", editorSession.getName());
			responseInformation.putValue("playerName", player.getName());		
			responseInformation.putValue("playerId", player.getId().toString());
			sendMessage("CreateEditorSession", responseInformation);
		} else {
			MessageInformation responseInformation = new MessageInformation("JoinEditorSession");
			responseInformation.setClientid(messageInformation.getClientid());
			
			editorSession = EditorSessionManager.getInstance().getEditorSession();
			Player player = new Player(editorSession, messageInformation.getValueAsString("Playername"));
			editorSession.addPlayer(player);
			
			responseInformation.putValue("topicName", editorSession.getName());
			responseInformation.putValue("editorName", editorSession.getName());
			List<JsonObject> players = new ArrayList<JsonObject>();
			for(Player sessionPlayer : editorSession.getPlayers()) {
				JsonObject json = new JsonObject();
				json.addProperty("id", sessionPlayer.getId().toString());
				json.addProperty("playerName", sessionPlayer.getName());
				players.add(json);
			}
			
			responseInformation.putValue("playerList", players);
			
			sendMessage("JoinEditorSession", responseInformation);
		}

		log.info("Called handleCreateEditorSession");
	}
}