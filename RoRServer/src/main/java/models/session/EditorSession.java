package models.session;

import communication.dispatcher.EditorSessionDispatcher;
import communication.queue.receiver.ReceiverQueue;
import communication.topic.SenderTopic;
import models.game.Map;
import models.game.Player;

/**
 * Oberklasse vom Editor-Modus.
 * Hält die Map und die Liste von verbundenen Playern
 * Erhält über einen ReceiverQueue Anfragen von Clients, die mit der EditorSession verbunden sind
 */
public class EditorSession extends RoRSession {

    public EditorSession(String sessionName, Map map, Player hostPlayer) {
        super(sessionName, map, hostPlayer);

        sessionDispatcher = new EditorSessionDispatcher();
        receiverQueue = new ReceiverQueue(sessionName, sessionDispatcher);
        senderTopic = new SenderTopic();
    }

    public EditorSession(String sessionName, Map map) {
        this(sessionName, map, null);
    }

}
