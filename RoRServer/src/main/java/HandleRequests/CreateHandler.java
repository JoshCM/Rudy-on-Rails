package HandleRequests;

import models.DataTranserObject.RequestInformation;
import models.Game.Player;
import org.apache.log4j.Logger;

import java.util.Map;

public class CreateHandler implements RequestHandler {

    private static CreateHandler createHandler = null;
    static Logger log = Logger.getLogger(CreateHandler.class.getName());



    private CreateHandler() {
        createHandler = new CreateHandler();
    }

    public static CreateHandler getInstance() {
        if (createHandler == null) {
            createHandler = new CreateHandler();
        }

        return createHandler;
    }


    /**
     * Sucht nach der richtigen Methode um ein Objekt auf Basis der gegebenen Informationen zu erzeugen
     * @param requestInformation - Informationen vom Client als DTO
     */
    @Override
    public void manageRequest(RequestInformation requestInformation) {
        switch(requestInformation.getRequest()) {
            case "PLAYER":
                createPlayer(requestInformation);
                break;
        }

    }


    public void createPlayer(RequestInformation requestInformation) {
        // Fehler abfangen: sind alle infos gesetzt?
        Player player = new Player(requestInformation.getClientid(), (String)requestInformation.getAttributes().get("Playername"));
    }
}
