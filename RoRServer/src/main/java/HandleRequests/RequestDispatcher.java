package HandleRequests;

import communication.queue.sender.FromServerResponseQueue;
import models.DataTranserObject.MessageInformation;
import models.DataTranserObject.MessageType;
import models.Game.Player;
import models.Session.EditorSessionManager;
import org.apache.log4j.Logger;


// Singleton
public class RequestDispatcher {

    private static RequestDispatcher requestHandler = null;

    static Logger log = Logger.getLogger(RequestDispatcher.class.getName());



    private RequestDispatcher() {
        requestHandler = new RequestDispatcher();
    }

    public static RequestDispatcher getInstance() {
        if (requestHandler == null) {
            requestHandler = new RequestDispatcher();
        }

        return requestHandler;
    }

    /**
     * INFO: Eventuell kann man die Responsesache nochmal aufspillten/verteilen
     * Verteilt die Anfrage je nach requestTyp an unterschiedliche Ziele, welche die Anfrage auflösen und eine Response für den Client erstellen
     * @param messageType - Spezifiziert die Anfrage und unterscheidet zwischen z. B. Create, Update, Delete
     * @param message - Beinhaltet die Informationen der Anfrage
     */
    public void dispatch(MessageType messageType, String message) {
        RequestSerializer requestSerializer = RequestSerializer.getInstance();
        MessageInformation requestInformation = requestSerializer.deserialize(message);
        MessageInformation responseInformation = requestSerializer.deserialize(message);

        switch(messageType) {
            case CREATE:
                responseInformation = resolveCreateTarget(requestInformation);

                break;
            case DELETE:
                //resolveDeleteTarget()
                break;
            case UPDATE:
                //resolveUpdateTarget()

                break;
        }
        String response = requestSerializer.serialize(responseInformation);
        FromServerResponseQueue fromServerResponseQueue = new FromServerResponseQueue(requestInformation.getClientid()); //Wie soll hier die Queue heißen?
        fromServerResponseQueue.sendMessage(response);
    }

    /**
     * Bearbeitet Anfragen des Typs "CREATE" und unterscheidet zwischen verschiedenen Anfragen, um neue Objekte zu erzeugen
     * @param messageInformation - MessageInformation-DTO welches alle Informationen zum bearbeiten der Anfrage beinhaltet
     */
    private MessageInformation resolveCreateTarget (MessageInformation messageInformation) {
        MessageInformation responseInformation = new MessageInformation();
        switch(messageInformation.getRequest()){
            case "PLAYER":
                Player player = new Player(messageInformation.getAttributes().get("Playername"));
                responseInformation.setClientid("basemodelID"); //muss hier noch eingezeigt werden
                responseInformation.setRequest("OK-PLAYER");
                break;
            default:
                break;
        }
         return responseInformation;
    }

}
