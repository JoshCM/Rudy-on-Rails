package models.dummy;

import org.apache.log4j.Logger;

import communication.topic.TopicSender;
import models.game.Map;
import persistent.MapManager;

public class DummyGame {
	// ToDo: Wird das hier noch gebraucht?
	private Map map;
	TopicSender sessionTopicSender;
	static Logger log = Logger.getLogger(DummyGame.class.getName());
	
	public DummyGame(){
	}
	
	public DummyGame(String sessionName) {
		log.info("DummyGame.DummyGame(String sessionName) : sessionName"+sessionName);
	}
	
	public void setMap(Map map){
		this.map = map;
	}
	
	public Map getMap() {
		return map;
	}
}
