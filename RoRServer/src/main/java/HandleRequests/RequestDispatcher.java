package HandleRequests;

import models.DataTranserObject.MessageType;
import models.DataTranserObject.RequestInformation;
import models.Game.Player;
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
     * Verteilt die Anfrage je nach requestTyp an unterschiedliche Ziele, welche die Anfrage auflösen
     * @param messageType - Spezifiziert die Anfrage und unterscheidet zwischen z. B. Create, Update, Delete
     * @param message - Beinhaltet die Informationen der Anfrage
     */
    public void dispatch(MessageType messageType, String message) {
        RequestSerializer requestSerializer = RequestSerializer.getInstance();
        RequestInformation requestInformation = requestSerializer.deserialize(message);
        switch(messageType) {
            case CREATE:
                resolveCreateTarget(requestInformation);

                //Antworte dem Client! - wieder Serializieren
                //FromServerResponseQueue fromServerResponseQueue = new FromServerResponseQueue(clientid);
                //fromServerResponseQueue.sendMessage("alles ok du sahnetörtchen");

                break;
            case DELETE:
                //resolveDeleteTarget()
                break;
            case UPDATE:
                //resolveUpdateTarget()

                break;
        }
    }

    /**
     * Bearbeitet Anfragen des Typs "CREATE" und unterscheidet zwischen verschiedenen Anfragen, um neue Objekte zu erzeugen
     * @param requestInformation - RequestInformation-DTO welches alle Informationen zum bearbeiten der Anfrage beinhaltet
     */
    private void resolveCreateTarget (RequestInformation requestInformation) {
        switch(requestInformation.getRequest()){
            case "PLAYER":
                Player player = new Player(requestInformation.getClientid(), requestInformation.getAttributes().get("Playername"));
        }

    }

}
