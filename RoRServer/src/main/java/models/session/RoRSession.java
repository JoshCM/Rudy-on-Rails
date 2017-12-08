package models.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import communication.MessageInformation;
import communication.queue.receiver.QueueReceiver;
import communication.topic.TopicSender;
import models.game.Map;
import models.game.Player;

/**
 * Oberklasse von EditorSession und GameSession
 * 
 */
public abstract class RoRSession {
	private String name;
	private ArrayList<Player> players = new ArrayList<>();
	private Map map;
	
	protected QueueReceiver queueReceiver;
	private TopicSender topicSender;
	
	private Thread sendMessageThread;
	private ConcurrentLinkedQueue<MessageInformation> messagesToSendQueue = new ConcurrentLinkedQueue<>();
	
	public RoRSession(String name) {
		this.name = name;
		this.topicSender = new TopicSender(name);
		map = new Map(this);
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
		}
		return null;
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public void removePlayer(Player player) {
		this.players.remove(player);
	}

	public Map getMap() {
		return map;
	}

	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}
}
