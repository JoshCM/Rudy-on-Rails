package HandleRequests;

import communication.queue.sender.FromServerResponseQueue;
import models.dataTranserObject.MessageInformation;
import models.editor.EditorSession;
import models.game.Player;
import models.session.EditorSessionManager;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestDispatcher {

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
		MessageInformation requestInformation = requestSerializer.deserialize(message);
		MessageInformation responseInformation = requestSerializer.deserialize(message);

		// Wird wahrscheinlich gar nicht gebraucht
		// createRequestToFunctionMap(request,requestInformation);

		callMethodFromString("handle" + request, requestInformation);

		String response = requestSerializer.serialize(responseInformation);
		FromServerResponseQueue fromServerResponseQueue = new FromServerResponseQueue(requestInformation.getClientid());
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
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(messageInfo.getValueAsString("Editorname"));
		Player player = new Player(messageInfo.getValueAsString("Playername"));
		editorSession.addPlayer(player);
		editorSession.getTopicSender()
				.sendMessage("EditorSessionCreated", messageInfo.getValueAsString("Playername"));

		log.info("Called handleCreateEditorSession");
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