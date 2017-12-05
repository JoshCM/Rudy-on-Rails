package models.dataTranserObject;

import java.util.HashMap;
import java.util.Map;

public class MessageInformation {
    private String clientId;
    private Map<String, String> attributes = new HashMap<String, String>();

    public MessageInformation(){}

    public MessageInformation(String clientid, HashMap<String, String> attributes) {
        this.clientId = clientid;
        this.attributes = attributes;
    }

    public String getClientid() {
        return clientId;
    }


    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String toString() {
        String s = null;
        s.format("client ID: {0}, \nattributes Map: {2}", clientId, attributes.toString());
        return s;
    }

    public void setClientid(String clientId) {
        this.clientId = clientId;
    }

    public void setAttributes(String key, String value) {
        this.attributes.put(key, value);
    }
}
