package models.Game;

import org.apache.log4j.Logger;

import communication.session.SessionQueueReceiver;
import communication.session.SessionTopicSender;

public class DummyGame {
	
	SessionQueueReceiver sessionQueueReceiver;
	SessionTopicSender sessionTopicSender;
	static Logger log = Logger.getLogger(DummyGame.class.getName());
	
	public DummyGame(String sessionName) {
		sessionQueueReceiver = new SessionQueueReceiver(sessionName);
		sessionTopicSender = new SessionTopicSender(sessionName);
		log.info("DummyGame.DummyGame(String sessionName) : sessionName"+sessionName);
		sessionQueueReceiver.setGame(this);
	}
	
	public void sendAction() {
		sessionTopicSender.sendMessage("Connected to Topic");
		log.info("DummyGame.sendAction()");
	}
	
	

}
