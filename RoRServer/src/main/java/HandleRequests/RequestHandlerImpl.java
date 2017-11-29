package HandleRequests;

import org.apache.log4j.Logger;


// Singleton
public class RequestHandlerImpl {

    private static RequestHandlerImpl requestHandler = null;
    private static CreateHandler createHandler = CreateHandler.getInstance();

    static Logger log = Logger.getLogger(RequestHandlerImpl.class.getName());



    private RequestHandlerImpl() {
        requestHandler = new RequestHandlerImpl();
    }

    public static RequestHandlerImpl getInstance() {
        if (requestHandler == null) {
            requestHandler = new RequestHandlerImpl();
        }

        return requestHandler;
    }

    public void handleRequest(String request, String message) {
        //Information object -- message propeties
        switch(request) {
            case "CREATE":
                createHandler.manageRequest(message);

                //Antworte dem Client!
                //FromServerResponseQueue fromServerResponseQueue = new FromServerResponseQueue(clientid);
                //fromServerResponseQueue.sendMessage("alles ok du sahnetörtchen");
                break;
            case "DELETE":
                break;
            case "UPDATE":
                break;
        }
    }



}
