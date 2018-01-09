package communication.dispatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import communication.MessageInformation;
import models.base.ObservableModel;

/**
 * Base-Klasse für alle spezifischen Dispatcher. Hier ist die grundsätzliche Verteilungslogik 
 * der Nachrichten für Dispatcher verankert.
 */
public abstract class DispatcherBase extends ObservableModel {
	private Logger log = Logger.getLogger(FromClientRequestQueueDispatcher.class.getName());
	
	public DispatcherBase() {

	}

	private void callMethodFromString(String methodName, MessageInformation messageInfo) {
		try {
			@SuppressWarnings("rawtypes")
			Class params[] = new Class[1];
			params[0] = MessageInformation.class;
			Object paramsObj[] = new Object[1];
			paramsObj[0] = messageInfo;
			Method thisMethod = this.getClass().getDeclaredMethod(methodName, params);
			thisMethod.invoke(this, paramsObj);
			log.info("Called " + methodName);
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
	public void dispatch(String messageType, String message) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		MessageInformation messageInformation = requestSerializer.deserialize(message);

		callMethodFromString("handle" + messageType, messageInformation);  
	}
}
