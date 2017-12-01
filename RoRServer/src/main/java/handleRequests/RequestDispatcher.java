package handleRequests;

import communication.queue.sender.FromServerResponseQueue;
import models.dataTranserObject.MessageInformation;
import org.apache.log4j.Logger;

import java.util.*;


public class RequestDispatcher {

    private static RequestDispatcher requestHandler = null;
    private static Map<String, Runnable> requestToManagerMap = new HashMap<String, Runnable>(); // hier Runnable evtl durch Command (interface ersetzen)

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
     * INFO: Eventuell kann man die Response-sache nochmal aufspillten/verteilen
     * Verteilt die Anfrage je nach requestTyp an unterschiedliche Ziele, welche die Anfrage auflösen und eine Response für den Client erstellen
     * @param message - Beinhaltet die Informationen der Anfrage
     */
    public void dispatch(String request, String message) {
        RequestSerializer requestSerializer = RequestSerializer.getInstance();
        MessageInformation requestInformation = requestSerializer.deserialize(message);
        MessageInformation responseInformation = requestSerializer.deserialize(message);

        createRequestToFunctionMap(request,requestInformation);



        String response = requestSerializer.serialize(responseInformation);
        FromServerResponseQueue fromServerResponseQueue = new FromServerResponseQueue(requestInformation.getClientid()); //Wie soll hier die Queue heißen?
        fromServerResponseQueue.sendMessage(response);
    }


    /**
     * Mapped den ensprechenden request auf eine Funktion und führt diese aus
     * @param request - Anfrage vom Client
     * @param requestInformation - Attribute der Anfrage
     */
    private void createRequestToFunctionMap(String request, MessageInformation requestInformation) {
        PlayerManager playerManager = new PlayerManager();
        requestToManagerMap.get(request).run();
        requestToManagerMap.put("CREATE_PLAYER", new Runnable() { //TO.DO am besten hier nur playerManager.create() in map legen
            @Override
            public void run() {
                playerManager.create();
            }
        });
        //requestToManagerMap.put("CREATE_MAP", funktionen);
        //requestToManagerMap.put("CREATE_EDITOR", funktionen);
    }
}