package models.editor;

import models.dataTranserObject.MessageInformation;
import models.game.Map;
import models.game.Player;
import communication.queue.receiver.FromClientRequestsEditorQueueReceiver;
import communication.topic.TopicSender;
import communication.dispatcher.RequestSerializer;

import java.util.ArrayList;

//Erbt von BaseModel, die die ID generiert
public class EditorSession {

    private String name;
    private ArrayList<Player> players = new ArrayList<>();
    private Map map;

    private TopicSender topicSender;
    private FromClientRequestsEditorQueueReceiver queueReceiver;

    public EditorSession(String name) {
        this.name = name;
        this.topicSender = new TopicSender(name);
        this.queueReceiver = new FromClientRequestsEditorQueueReceiver(name, this);
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

    public TopicSender getTopicSender() {
        return topicSender;
    }
	
	public void SendMessage(String messageType, MessageInformation messageInfo) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		String response = requestSerializer.serialize(messageInfo);
		topicSender.sendMessage(messageType, response);
	}
}
