package requestHandler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import communication.queue.sender.FromServerResponseQueue;
import models.MessageType;
import models.Player;
import org.apache.log4j.Logger;

import javax.jms.TextMessage;

// Singleton
public class RequestHandler {

    private static RequestHandler requestHandler = null;
    static Logger log = Logger.getLogger(RequestHandler.class.getName());



    private RequestHandler() {
    }

    public static RequestHandler getInstance() {
        if (requestHandler == null) {
            requestHandler = new RequestHandler();
        }

        return requestHandler;
    }

    public void handleRequest(MessageType command, String message) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonElement.class).getAsJsonObject();
        switch(command) {
            case CREATE:
                //JsonArray attributes = jsonObject.getAsJsonArray("attributes");
            	JsonObject obj = jsonObject.getAsJsonObject("attributes");
            	
                String clientid  = jsonObject.get("clientid").getAsString();
                String name = obj.get("Playername").getAsString();
                
                Player player = new Player(clientid, name);
                FromServerResponseQueue fromServerResponseQueue = new FromServerResponseQueue(clientid);
                fromServerResponseQueue.sendMessage("alles ok du sahnetoertchen");
                
                break;
            case READ:
                break;
            case UPDATE:
                break;
            case DELETE:
                break;
            case LEAVE:
                break;
            case ERROR:
                break;
        }
    }
}
