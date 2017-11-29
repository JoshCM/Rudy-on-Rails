package models.DataTranserObject;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class RequestInformation {
    private String clientid;
    private String request;
    private Map<String, String> attributes = new HashMap<String, String>();

    public RequestInformation (String clientid, String request, HashMap<String, String> attributes) {
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
}
