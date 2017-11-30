package models.dataTranserObject;

import java.util.HashMap;
import java.util.Map;

public class MessageInformation {
    private String clientid;
    private String request;
    private Map<String, String> attributes = new HashMap<String, String>();

    public MessageInformation(){}

    public MessageInformation(String clientid, String request, HashMap<String, String> attributes) {
        this.clientid = clientid;
        this.request = request;
        this.attributes = attributes;
    }

    public String getClientid() {
        return clientid;
    }

    public String getRequest() {
        return request;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String toString() {
        String s = null;
        s.format("client ID: {0}, \nrequest: {1} \nattributes Map: {2}", clientid, request, attributes.toString());
        return s;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
