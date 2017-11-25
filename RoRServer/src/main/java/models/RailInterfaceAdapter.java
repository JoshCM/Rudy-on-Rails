package models;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RailInterfaceAdapter implements JsonSerializer<Rail>, JsonDeserializer<Rail> {
	
	@Override
	public JsonElement serialize(Rail src, Type typeOfSrc, JsonSerializationContext context) {
		// TODO Auto-generated method stub
		System.out.println(src);
		System.out.println(typeOfSrc);
		System.out.println(context);
		
		final JsonObject jsonObject = new JsonObject();
		
		return null;
	}

	@Override
	public Rail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		// TODO Auto-generated method stub
		System.out.print(json);
		return null;
	}

}
