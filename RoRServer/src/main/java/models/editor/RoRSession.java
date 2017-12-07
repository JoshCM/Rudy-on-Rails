package models.editor;

import java.util.concurrent.ConcurrentLinkedQueue;
import communication.MessageInformation;
import communication.topic.TopicSender;

public class RoRSession {
	private String name;
	private TopicSender topicSender;
	
	private Thread sendMessageThread;
	private ConcurrentLinkedQueue<MessageInformation> messagesToSendQueue = new ConcurrentLinkedQueue<>();
	
	public RoRSession(String name) {
		this.name = name;
		this.topicSender = new TopicSender(name);
		StartSendMessageThread();
	}
	
	public String getName() {
		return name;
	}
	
	public void addMessage(MessageInformation messageInformation) {
		if(messageInformation != null) {
			messagesToSendQueue.add(messageInformation);
		}
	}
	
	public void StartSendMessageThread() {
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
}
