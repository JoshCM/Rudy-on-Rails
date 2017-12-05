package models.editor;

import models.dataTranserObject.MessageInformation;
import models.game.Map;
import models.game.Player;
import communication.queue.receiver.FromClientRequestsEditorQueue;
import communication.session.SessionTopicSender;
import java.util.ArrayList;

import HandleRequests.RequestSerializer;

//Erbt von BaseModel, die die ID generiert
public class EditorSession {

    private String name;
    private ArrayList<Player> players = new ArrayList<>();
    private Map map;

    private SessionTopicSender topicSender;
    private FromClientRequestsEditorQueue queueReceiver;

    public EditorSession(String name) {
        this.name = name;
        this.topicSender = new SessionTopicSender(name);
        this.queueReceiver = new FromClientRequestsEditorQueue(name, this);
        map = new Map(this);
    }

    public String getName() {
        return name;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }
    
    public Map getMap() {
    	return map;
    }
    
    // ToDo: Unmodifiable List zurückgeben
    public ArrayList<Player> getPlayers(){
    	return players;
    }

    public SessionTopicSender getTopicSender() {
        return topicSender;
    }
	
	public void SendMessage(String messageType, MessageInformation messageInfo) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		String response = requestSerializer.serialize(messageInfo);
		topicSender.sendMessage(messageType, response);
	}
}
