package communication;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageInformation {
    private String clientId;
    private String messageType;
    private String messageId;
    private Map<String, Object> attributes = new HashMap<String, Object>();

    public MessageInformation(){}

    public MessageInformation(String messageType) {
    	messageId = UUID.randomUUID().toString();
        this.messageType = messageType;
    }

    public String getClientid() {
        return clientId;
    }
    
    public String getMessageType() {
    	return messageType;
    }
    
    public String getMessageId() {
    	return messageId;
    }
    
    public UUID getMessageIdAsUUID() {
    	return UUID.fromString(messageId);
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
    
    public UUID getValueAsUUID(String key) {
    	return UUID.fromString(getValueAsString(key));
    }
    
    public String getValueAsString(String key) {
    	return String.valueOf(attributes.get(key));
    }
    
    public int getValueAsInt(String key) {
    	Object obj = attributes.get(key);
    	if(obj instanceof Double) {
    		Double objAsDouble = (Double)obj;
    		return objAsDouble.intValue();
    	}
    	return (int)obj;
    }
    
    public double getValueAsDouble(String key) {
    	return (double)attributes.get(key);
    }
    
    public boolean getValueAsBoolean(String key) {
    	return (boolean)attributes.get(key);
    }
}
