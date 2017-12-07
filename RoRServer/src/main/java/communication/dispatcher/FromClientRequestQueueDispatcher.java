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

public class FromClientRequestQueueDispatcher {
	private Logger log = Logger.getLogger(FromClientRequestQueueDispatcher.class.getName());

	private void callMethodFromString(String method, MessageInformation messageInfo) {
		try {
			Class params[] = new Class[1];
			params[0] = MessageInformation.class;
			Object paramsObj[] = new Object[1];
			paramsObj[0] = messageInfo;
			Method thisMethod = this.getClass().getDeclaredMethod(method, params);
			thisMethod.invoke(this, paramsObj);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * INFO: Eventuell kann man die Response-sache nochmal aufspillten/verteilen
	 * Verteilt die Anfrage je nach requestTyp an unterschiedliche Ziele, welche die
	 * Anfrage auflösen und eine Response für den Client erstellen
	 * 
	 * @param message
	 *            - Beinhaltet die Informationen der Anfrage
	 */
	public void dispatch(String request, String message) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		MessageInformation messageInformation = requestSerializer.deserialize(message);

		// Wird wahrscheinlich gar nicht gebraucht
		// createRequestToFunctionMap(request,requestInformation);

		callMethodFromString("handle" + request, messageInformation);
	}

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
	private void handleCreateEditorSession(MessageInformation messageInformation) {
		EditorSession editorSession;
		
		MessageInformation responseInformation = new MessageInformation();
		responseInformation.setClientid(messageInformation.getClientid());

		if (EditorSessionManager.getInstance().getEditorSession() == null) {
			editorSession = EditorSessionManager.getInstance()
					.createNewEditorSession(messageInformation.getValueAsString("Editorname"));
			Player player = new Player(editorSession, messageInformation.getValueAsString("Playername"));
			editorSession.addPlayer(player);
			
			responseInformation.putValue("Topicname", editorSession.getName());
			responseInformation.putValue("Editorname", editorSession.getName());
			responseInformation.putValue("Playername", player.getName());		
			responseInformation.putValue("Playerid", player.getId().toString());
			sendMessage("CreateEditorSession", responseInformation);
		} else {
			editorSession = EditorSessionManager.getInstance().getEditorSession();
			Player player = new Player(editorSession, messageInformation.getValueAsString("Playername"));
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