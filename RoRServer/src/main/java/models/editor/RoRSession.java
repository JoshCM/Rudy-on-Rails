package models.editor;

import java.util.concurrent.ConcurrentLinkedQueue;
import communication.MessageInformation;
import communication.queue.receiver.QueueReceiver;
import communication.topic.TopicSender;

public abstract class RoRSession {
	private String name;
	protected QueueReceiver queueReceiver;
	private TopicSender topicSender;
	
	private Thread sendMessageThread;
	private ConcurrentLinkedQueue<MessageInformation> messagesToSendQueue = new ConcurrentLinkedQueue<>();
	
	public RoRSession(String name) {
		this.name = name;
		this.topicSender = new TopicSender(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void addMessage(MessageInformation messageInformation) {
		if(messageInformation != null) {
			messagesToSendQueue.add(messageInformation);
		}
	}
			
	public void setup() {
		topicSender.setup();
		queueReceiver.setup();
		startSendMessageThread();
	}
	
	private void startSendMessageThread() {
		sendMessageThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						if(messagesToSendQueue.peek() != null) {
							MessageInformation messageInformation = messagesToSendQueue.poll();
							String messageType = messageInformation.getMessageType();
							topicSender.sendMessage(messageType, messageInformation);
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
		};
		
		return null;
	}
}
