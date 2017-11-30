package HandleRequests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.DataTranserObject.MessageInformation;

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
     * Deserialize a JSON-String and transfer it to the DataTransferObject(DTO) MessageInformation
     * @param msg - Represents the request message from the client
     * @return MessageInformation object
     */
    public MessageInformation deserialize(String msg) {
        MessageInformation messageInformation = null;

        JsonObject jsonObject = gson.fromJson(msg, JsonElement.class).getAsJsonObject();
        messageInformation = gson.fromJson(msg, MessageInformation.class);

        return messageInformation;
    }

    public String serialize(MessageInformation messageInformation) {
        return gson.toJson(messageInformation);
    }
}
