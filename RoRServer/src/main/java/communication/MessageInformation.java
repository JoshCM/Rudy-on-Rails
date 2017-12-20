package communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

/**
 * DTO, das serialisiert in einer Nachricht mitgesendet wird. Hier stehen nachrichtspezifischen
 * Informationen der Nachricht drin.
 */
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
    
    public List<JsonObject> getValueAsList(String key) {
    	Gson gson = new Gson();
    	List<LinkedTreeMap> list = (List<LinkedTreeMap>)attributes.get(key);
    	List<JsonObject> result = new ArrayList<JsonObject>();
    	for(LinkedTreeMap map : list) {
    		result.add(gson.toJsonTree(map).getAsJsonObject());
    	}
    	return result;
    }
    
    public <T> List<T> getValueAsObjectList(String key){
    	List<Object> oList = (List<Object>)attributes.get(key);;
    	List<T> tList = new ArrayList<T>();
    	for(Object obj : oList) {
    		tList.add((T) obj);
    	}
		return tList;
    }
}
