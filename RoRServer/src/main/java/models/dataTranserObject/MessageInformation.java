package models.dataTranserObject;

import java.util.HashMap;
import java.util.Map;

public class MessageInformation {
    private String clientId;
    private Map<String, Object> attributes = new HashMap<String, Object>();

    public MessageInformation(){}

    public MessageInformation(String clientid, HashMap<String, Object> attributes) {
        this.clientId = clientid;
        this.attributes = attributes;
    }

    public String getClientid() {
        return clientId;
    }

    public String toString() {
        return String.format("client ID: {0}, \nattributes Map: {2}", clientId, attributes.toString());
    }

    public void setClientid(String clientId) {
        this.clientId = clientId;
    }

    public void putValue(String key, Object value) {
    	attributes.put(key, value);
    }
    
    public String getValueAsString(String key) {
    	return (String)attributes.get(key);
    }
    
    public int getValueAsInt(String key) {
    	return (int)attributes.get(key);
    }
    
    public double getValueAsDouble(String key) {
    	return (double)attributes.get(key);
    }
    
    public boolean getValueAsBoolean(String key) {
    	return (boolean)attributes.get(key);
    }
}
