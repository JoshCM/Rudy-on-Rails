package HandleRequests;

import models.DataTranserObject.RequestInformation;

public interface RequestHandler {

    public void manageRequest(RequestInformation requestInformation);
}
