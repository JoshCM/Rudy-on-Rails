package models.dataTranserObject;

import java.util.HashMap;
import java.util.Map;

public class MessageInformation {
    private String clientid;
    private Map<String, String> attributes = new HashMap<String, String>();

    public MessageInformation(){}

    public MessageInformation(String clientid, HashMap<String, String> attributes) {
        this.clientid = clientid;
        this.attributes = attributes;
    }

    public String getClientid() {
        return clientid;
    }


    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String toString() {
        String s = null;
        s.format("client ID: {0}, \nattributes Map: {2}", clientid, attributes.toString());
        return s;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
