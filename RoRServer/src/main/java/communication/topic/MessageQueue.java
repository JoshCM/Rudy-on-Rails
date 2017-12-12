package communication.topic;

import java.util.concurrent.ConcurrentLinkedQueue;

import communication.MessageEnvelope;
import communication.MessageInformation;

public class MessageQueue {
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
	
	public void addMessage(MessageEnvelope messageEnvelope) {
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
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		sendMessageThread.start();
	}
	
	public MessageInformation getFirstFoundMessageInformationForMessageType(String messageType) {
		Object[] messages;
		messages = messagesToSendQueue.toArray();

		for(Object obj : messages) {
			MessageInformation messageInfo = (MessageInformation)obj;
			if(messageInfo.getMessageType().equals(messageType)) {
				return messageInfo;
			}
		}
		return null;
	}
}
