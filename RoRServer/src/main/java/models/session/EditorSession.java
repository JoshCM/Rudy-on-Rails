package models.session;

import communication.dispatcher.EditorSessionDispatcher;
import communication.queue.receiver.QueueReceiver;
import models.base.InterActiveGameModel;
import models.game.Map;
import models.game.Player;

/**
 * Oberklasse vom Editor-Modus. 
 * Hält die Map und die Liste von verbundenen Playern
 * Erhält über einen QueueReceiver Anfragen von Clients, die mit der EditorSession verbunden sind
 */
public class EditorSession extends RoRSession {
	public EditorSession(String sessionName, Map map, Player hostPlayer) {
		super(sessionName, map, hostPlayer);
	
		EditorSessionDispatcher dispatcher = new EditorSessionDispatcher(this);
		this.queueReceiver = new QueueReceiver(sessionName, dispatcher);
	}

	public EditorSession(String sessionName, Map map) {
	    this(sessionName, map, null);
    }

    @Override
    public void update(InterActiveGameModel o, Object arg) {
        // Queue/Topic über Update informieren...
    }
}
