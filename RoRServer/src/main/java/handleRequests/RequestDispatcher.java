package HandleRequests;

import communication.queue.sender.FromServerResponseQueue;
import models.dataTranserObject.MessageInformation;
import models.editor.EditorSession;
import models.game.Player;
import models.session.EditorSessionManager;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RequestDispatcher {
	private boolean isInitial = false;
	private static RequestDispatcher requestHandler;

	static Logger log = Logger.getLogger(RequestDispatcher.class.getName());

	private RequestDispatcher() {

	}

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

	public static RequestDispatcher getInstance() {
		if (requestHandler == null) {
			requestHandler = new RequestDispatcher();
		}

		return requestHandler;
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

	private void sendMessage(MessageInformation messageInformation) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		String response = requestSerializer.serialize(messageInformation);
		FromServerResponseQueue fromServerResponseQueue = new FromServerResponseQueue(messageInformation.getClientid());
		fromServerResponseQueue.sendMessage(response);
	}

	private void handleCREATE_GAMESESSION(MessageInformation messageInfo) {
		log.info("handleCREATE_GAMESESSION(MessageInformation messageInfo): Ich wurde aufgerufen!");
	}

	/**
	 * Neuer Player wird erstellt und allen angemeldeten Clients mitgeteilt
	 * 
	 * @param messageInfo
	 */
	private void handleCreateEditorSession(MessageInformation messageInfo) {
		EditorSession editorSession;
		
		MessageInformation responseInfo = new MessageInformation();
		Player player = new Player(messageInfo.getValueAsString("Playername"));

		if (EditorSessionManager.getInstance().getEditorSession() == null) {
			editorSession = EditorSessionManager.getInstance()
					.createNewEditorSession(messageInfo.getValueAsString("Editorname"));
			editorSession.addPlayer(player);
			
			responseInfo.putValue("Topicname", editorSession.getName());
			responseInfo.putValue("Playername", player.getName());
		} else {
			editorSession = EditorSessionManager.getInstance().getEditorSession();
			editorSession.addPlayer(player);
			
			List<JsonObject> players = new ArrayList<JsonObject>();
			for(Player sessionPlayer : editorSession.getPlayers()) {
				JsonObject json = new JsonObject();
				json.addProperty("Id", sessionPlayer.getId().toString());
				json.addProperty("Playername", sessionPlayer.getName());
				players.add(json);
			}
			
			responseInfo.putValue("Playerlist", players);
		}

		log.info("Called handleCreateEditorSession");

		sendMessage(responseInfo);
	}

	private void handleREAD_GAMESESSIONS(MessageInformation messageInfo) {
		log.info("handleCREATE_GAMESESSION(MessageInformation messageInfo): Ich wurde aufgerufen!");
	}

	private void handleREAD_EDITORSESSIONS(MessageInformation messageInfo) {
		log.info("handleREAD_EDITORSESSIONS(MessageInformation messageInfo): Ich wurde aufgerufen!");
	}

	private void handleJOIN_GAMESESSION(MessageInformation messageInfo) {
		log.info("handleUPDATE_JOIN_GAMESESSION(MessageInformation messageInfo): Ich wurde aufgerufen!");
	}

	private void handleJOIN_EDITORSESSION(MessageInformation messageInfo) {
		log.info("handleUPDATE_JOIN_EDITORSESSION(MessageInformation messageInfo): Ich wurde aufgerufen!");
	}
}