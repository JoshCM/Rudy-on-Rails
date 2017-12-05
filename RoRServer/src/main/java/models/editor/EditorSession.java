package models.editor;

import models.dataTranserObject.MessageInformation;
import models.game.Map;
import models.game.Player;
import models.game.Rail;
import communication.session.SessionTopicSender;
import java.util.ArrayList;

//Erbt von BaseModel, die die ID generiert
public class EditorSession {

    private String name;
    private ArrayList<Player> players = new ArrayList<>();
    private Map map;

    private SessionTopicSender topicSender;

    public EditorSession(String name) {
        this.name = name;
        this.topicSender = new SessionTopicSender("BaseModelID");
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

    public SessionTopicSender getTopicSender() {
        return topicSender;
    }

	public void SendRailCreatedMessage(Rail rail) {
		// ToDo: Hier soll eine Nachricht an den Topic gesendet werden
	}
}
