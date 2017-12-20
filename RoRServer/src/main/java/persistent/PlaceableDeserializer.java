package persistent;

import java.lang.reflect.Type;

import org.apache.log4j.Logger;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import communication.queue.receiver.QueueReceiver;
import models.game.Placeable;

/**
 * Deserializer, der für Placeables genutzt wird, damit sie mit dem richtigen Typ (z.B. Rail) deserialisiert werden
 */
public class PlaceableDeserializer<T extends Placeable> implements JsonDeserializer<T>{

	static Logger log = Logger.getLogger(QueueReceiver.class.getName());
	private static final String CLASS_NAME = "className";
	
	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject jsonObject = json.getAsJsonObject();
		JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASS_NAME);
		String className = prim.getAsString();
		Class<T> myClass = getClassInstance(className);
		return context.deserialize(jsonObject, myClass);
	}
	
	@SuppressWarnings("unchecked")
	public Class<T> getClassInstance(String className){
		try {
			return (Class<T>) Class.forName(className);
		} catch (ClassNotFoundException cnfe) {
			throw new JsonParseException(cnfe.getMessage());
		}
	}

}
