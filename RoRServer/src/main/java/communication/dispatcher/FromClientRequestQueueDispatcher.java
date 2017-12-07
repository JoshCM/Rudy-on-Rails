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
		queueSender.sendMessage(messageType, response);
	}

	/**
	 * Neuer Player wird erstellt und allen angemeldeten Clients mitgeteilt
	 * 
	 * @param messageInformation
	 */
	public void handleCreateEditorSession(MessageInformation messageInformation) {
		EditorSession editorSession;
		
		MessageInformation responseInformation = new MessageInformation();
		responseInformation.setClientid(messageInformation.getClientid());
		Player player = new Player(messageInformation.getValueAsString("Playername"));

		if (EditorSessionManager.getInstance().getEditorSession() == null) {
			editorSession = EditorSessionManager.getInstance()
					.createNewEditorSession(messageInformation.getValueAsString("Editorname"));
			editorSession.addPlayer(player);
			
			responseInformation.putValue("Topicname", editorSession.getName());
			responseInformation.putValue("Editorname", editorSession.getName());
			responseInformation.putValue("Playername", player.getName());		
			responseInformation.putValue("Playerid", player.getId().toString());
			sendMessage("CreateEditorSession", responseInformation);
		} else {
			editorSession = EditorSessionManager.getInstance().getEditorSession();
			editorSession.addPlayer(player);
			
			responseInformation.putValue("Topicname", editorSession.getName());
			responseInformation.putValue("Editorname", editorSession.getName());
			List<JsonObject> players = new ArrayList<JsonObject>();
			for(Player sessionPlayer : editorSession.getPlayers()) {
				JsonObject json = new JsonObject();
				json.addProperty("Id", sessionPlayer.getId().toString());
				json.addProperty("Playername", sessionPlayer.getName());
				players.add(json);
			}
			
			responseInformation.putValue("Playerlist", players);
			
			sendMessage("JoinEditorSession", responseInformation);
		}

		log.info("Called handleCreateEditorSession");
	}
}