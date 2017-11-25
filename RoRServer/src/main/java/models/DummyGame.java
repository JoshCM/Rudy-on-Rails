package models;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import communication.session.SessionQueueReceiver;
import communication.session.SessionTopicSender;

public class DummyGame {
	
	private Map map;
	SessionQueueReceiver sessionQueueReceiver;
	SessionTopicSender sessionTopicSender;
	static Logger log = Logger.getLogger(DummyGame.class.getName());
	
	public DummyGame(){
		
	}
	
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

	public void setMap(Map map){
		this.map = map;
	}
	
	public void saveMap(){
		Gson gson = new Gson();
		String jsonMap = gson.toJson(map);
		System.out.println(jsonMap);
	}
	
	public void loadMap(String jsonMap){
		Gson gson = new Gson();
		map = gson.fromJson(jsonMap, Map.class);
	}
	

}
