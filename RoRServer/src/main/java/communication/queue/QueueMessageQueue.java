package communication.queue;

import java.util.concurrent.ConcurrentLinkedQueue;
import communication.MessageEnvelope;
import communication.MessageInformation;
import communication.queue.sender.QueueSender;
import models.base.ModelObserver;
import models.base.InterActiveGameModel;

public class QueueMessageQueue implements ModelObserver {
	private QueueSender queueSender;
	private Thread sendMessageThread;
	private ConcurrentLinkedQueue<MessageEnvelope> messagesToSendOnTopicQueue = new ConcurrentLinkedQueue<>();
	private static QueueMessageQueue instance;

	private QueueMessageQueue() {

	}

	public static QueueMessageQueue getInstance() {
		if (instance == null) {
			instance = new QueueMessageQueue();
			instance.setup();
		}
		return instance;
	}

	public void setup() {
		queueSender = new QueueSender();
		queueSender.setup();
		startSendMessageThread();
	}

	private void addMessage(MessageEnvelope messageEnvelope) {
		if (messageEnvelope != null) {
			messagesToSendOnTopicQueue.add(messageEnvelope);
		}
	}

	private void startSendMessageThread() {
		sendMessageThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (messagesToSendOnTopicQueue.peek() != null) {
						queueSender.sendMessage(messagesToSendOnTopicQueue.poll());
					}
				}
			}
		});
		sendMessageThread.start();
	}

	/**
	 * Wird für UnitTests genutzt, um zu überprüfen, ob auch alle wichtigen
	 * Informationen in der Nachricht stehen
	 * 
	 * @param messageType
	 * @return
	 */
	public MessageInformation getFirstFoundMessageInformationForMessageType(String messageType) {
		Object[] messages;
		messages = messagesToSendOnTopicQueue.toArray();

		for (Object obj : messages) {
			MessageInformation messageInfo = ((MessageEnvelope) obj).getMessageInformation();
			if (messageInfo.getMessageType().equals(messageType)) {
				return messageInfo;
			}
		}
		return null;
	}
	
	/**
	 * Wird für UnitTests genutzt, um zu überprüfen, ob auch alle wichtigen
	 * Informationen in der Nachricht stehen anhand eines Attributes
	 * 
	 * @param messageType
	 * @return
	 */
	public MessageInformation getFirstFoundMessageInformationForAttribute(Object attribute) {
		Object[] messages;
		messages = messagesToSendOnTopicQueue.toArray();

		for (Object obj : messages) {
			MessageInformation messageInfo = ((MessageEnvelope) obj).getMessageInformation();
			if (messageInfo.getAttributes().values().contains(attribute)) {
				return messageInfo;
			}
		}
		return null;
	}

	@Override
	public void update(InterActiveGameModel observable, Object arg) {
		MessageEnvelope messageEnvelope = (MessageEnvelope) arg;
		addMessage(messageEnvelope);
	}
}
