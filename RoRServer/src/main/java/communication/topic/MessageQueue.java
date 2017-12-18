package communication.topic;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import communication.MessageEnvelope;
import communication.MessageInformation;
import models.base.ModelObserver;
import models.base.ObservableModel;

public class MessageQueue implements ModelObserver {
	private TopicSender topicSender;
	private Thread sendMessageThread;
	private ConcurrentLinkedQueue<MessageEnvelope> messagesToSendQueue = new ConcurrentLinkedQueue<>();
	private static MessageQueue instance;
	
	private MessageQueue() {
		
	}
	
	public static MessageQueue getInstance() {
		if(instance == null) {
			instance = new MessageQueue();
		}
		return instance;
	}
	
	public void setup() {
		topicSender = new TopicSender();
		topicSender.setup();
		startSendMessageThread();
	}
	
	private void addMessage(MessageEnvelope messageEnvelope) {
		if(messageEnvelope != null) {
			messagesToSendQueue.add(messageEnvelope);
		}
	}	

	private void startSendMessageThread() {
		sendMessageThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						if(messagesToSendQueue.peek() != null) {
							topicSender.sendMessage(messagesToSendQueue.poll());
						}
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		sendMessageThread.start();
	}
	
	/**
	 * Wird für UnitTests genutzt, um zu überprüfen, ob auch alle wichtigen Informationen in der Nachricht stehen
	 * @param messageType
	 * @return
	 */
	public MessageInformation getFirstFoundMessageInformationForMessageType(String messageType) {
		Object[] messages;
		messages = messagesToSendQueue.toArray();

		for(Object obj : messages) {
			MessageInformation messageInfo = ((MessageEnvelope)obj).getMessageInformation();
			if(messageInfo.getMessageType().equals(messageType)) {
				return messageInfo;
			}
		}
		return null;
	}
	
	/**
	 * Wird für UnitTests genutzt, um zu überprüfen, ob auch alle wichtigen Informationen in der Nachricht stehen
	 * @param messageType
	 * @return
	 */
	public MessageInformation geMessageInformationForRailId(String messageType, UUID railId) {
		Object[] messages;
		messages = messagesToSendQueue.toArray();

		for(Object obj : messages) {
			MessageInformation messageInfo = ((MessageEnvelope)obj).getMessageInformation();
			if(messageInfo.getMessageType().equals(messageType)) {
				if(messageInfo.getValueAsUUID("railId").equals(railId)) {
					return messageInfo;
				}
			}
		}
		return null;
	}

	@Override
	public void update(ObservableModel observable, Object arg) {
		MessageEnvelope messageEnvelope = (MessageEnvelope) arg;
		addMessage(messageEnvelope);
	}
}
