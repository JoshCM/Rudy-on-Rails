package models;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class PlaceableDeserializer<T extends Placeable> implements JsonDeserializer<T>{

	private static final String CLASS_NAME = "className";
	
	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		
		System.out.println("json: " + json);
		System.out.println("type: " + typeOfT);
		System.out.println("context: " + context);
		JsonObject jsonObject = json.getAsJsonObject();
		System.out.println("jsonObject: "+ jsonObject);
		System.out.println("json className: " + jsonObject.get(CLASS_NAME));
		JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASS_NAME);
		System.out.println("json prim: " + prim);
		String className = prim.getAsString();
		System.out.println("className: " + className);
		Class<T> myClass = getClassInstance(className);
		System.out.println("Klasse: " + myClass);
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
