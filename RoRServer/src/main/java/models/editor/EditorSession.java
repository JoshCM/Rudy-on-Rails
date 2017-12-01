package models.editor;

import models.game.Player;
import communication.session.SessionTopicSender;
import java.util.ArrayList;

//Erbt von BaseModel, die die ID generiert
public class EditorSession {

    private String name;
    private ArrayList<Player> players = new ArrayList<>();

    private SessionTopicSender topicSender;

    public EditorSession(String name) {
        this.name = name;
        this.topicSender = new SessionTopicSender("BaseModelID");
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

    public SessionTopicSender getTopicSender() {
        return topicSender;
    }
}
