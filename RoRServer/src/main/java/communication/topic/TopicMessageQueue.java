package communication.topic;

import java.util.concurrent.ConcurrentLinkedQueue;
import communication.MessageEnvelope;
import communication.MessageInformation;
import models.base.ModelObserver;
import models.base.ObservableModel;

public class TopicMessageQueue implements ModelObserver {
	private TopicSender topicSender;
	private Thread sendMessageThread;
	private ConcurrentLinkedQueue<MessageEnvelope> messagesToSendOnTopicQueue = new ConcurrentLinkedQueue<>();
	private static TopicMessageQueue instance;

	private TopicMessageQueue() {

	}

	public static TopicMessageQueue getInstance() {
		if (instance == null) {
			instance = new TopicMessageQueue();
		}
		return instance;
	}

	public void setup() {
		topicSender = new TopicSender();
		topicSender.setup();
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
						topicSender.sendMessage(messagesToSendOnTopicQueue.poll());
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
	
	public void clear() {
		messagesToSendOnTopicQueue.clear();
	}

	@Override
	public void update(ObservableModel observable, Object arg) {
		MessageEnvelope messageEnvelope = (MessageEnvelope) arg;
		addMessage(messageEnvelope);
	}
}
