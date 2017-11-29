package HandleRequests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.DataTranserObject.RequestInformation;

import java.util.HashMap;
import java.util.Map;

public class RequestSerializer {

    private static RequestSerializer requestSerializer = null;
    private Gson gson = new Gson();


    private RequestSerializer() {}

    public static RequestSerializer getInstance() {
        if (requestSerializer == null) {
            requestSerializer = new RequestSerializer();
        }
        return requestSerializer;
    }

    /**
     * Deserialize a JSON-String and transfer it to the DataTransferObject(DTO) RequestInformation
     * @param msg - Represents the request message from the client
     * @return RequestInformation object
     */
    public RequestInformation deserialize(String msg) {
        RequestInformation requestInformation = null;

        JsonObject jsonObject = gson.fromJson(msg, JsonElement.class).getAsJsonObject();
        requestInformation = gson.fromJson(msg, RequestInformation.class);

        return requestInformation;
    }
}
