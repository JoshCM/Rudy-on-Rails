package communication.queue.receiver;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import HandleRequests.EditorRequestDispatcher;
import models.editor.EditorSession;

public class FromClientRequestsEditorQueue extends QueueReceiver {

	private EditorRequestDispatcher editorRequestDispatcher;

	public FromClientRequestsEditorQueue(String queueName, EditorSession editorSession) {
		super(queueName);
		editorRequestDispatcher = new EditorRequestDispatcher(editorSession);
	}
	
	@Override
	public void onMessage(Message message) {
		log.info("FromClientRequestsEditorQueue.onMessage(): Message incoming ...");
		//Todo: auf nicht initiale Nachricht reagieren
		TextMessage textMessage = (TextMessage) message;
		try {
			String request = message.getJMSType();

			log.info("FromClientRequestsEditorQueue.onMessage(): ... Message received [" + new Date().toString() + "]: "
					+ textMessage.getText());
			
			editorRequestDispatcher.dispatch(request, textMessage.getText());
		} catch (JMSException e) {
			log.error(
					"FromClientRequestsEditorQueue.onMessage(Message message) : QueueSender konnte Nachricht nicht verschicken");
			e.printStackTrace();
		}
	}
}
