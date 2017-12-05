package models.game;

import org.apache.log4j.Logger;
import communication.session.SessionQueueReceiver;
import communication.session.SessionTopicSender;
import models.MapManager;

public class DummyGame {
	
	private Map map;
	private MapManager mapManager;
	SessionQueueReceiver sessionQueueReceiver;
	SessionTopicSender sessionTopicSender;
	static Logger log = Logger.getLogger(DummyGame.class.getName());
	
	public DummyGame(){
		map = new Map(null);
		mapManager = new MapManager();
	}
	
	public DummyGame(String sessionName) {
		sessionQueueReceiver = new SessionQueueReceiver(sessionName);
		sessionTopicSender = new SessionTopicSender(sessionName);
		log.info("DummyGame.DummyGame(String sessionName) : sessionName"+sessionName);
		sessionQueueReceiver.setGame(this);
	}
	
	public void sendAction() {
		sessionTopicSender.sendMessage("Message", "Connected to Topic");
		log.info("DummyGame.sendAction()");
	}

	public void setMap(Map map){
		this.map = map;
	}
	
	public void saveMap(String mapName){
		mapManager.saveMap(map, mapName);
	}
	
	public void loadMap(String mapName){
		map = mapManager.loadMap(mapName);
	}
	
	public Map getMap() {
		return map;
	}
	

}
