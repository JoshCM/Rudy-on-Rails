package communication.helper;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import models.DummyGame;

public class Serializer {
	private static Gson gson = new Gson();
	
	// Der Typ der Objekt-Serialisierung
	private static Type commandType = new TypeToken<Command>(){}.getType();
	
	/**
	 * Das Command-Objekt in einen String in JSON-Format verwandeln
	 * @param cmd
	 * @return
	 */
	public static String serialize(Command cmd) {
		return gson.toJson(cmd, commandType);
	}
	
	/**
	 * Aus dem String in JSON-Format ein Command-Objekt erzeugen
	 * @param jsonString
	 * @return
	 */
	public static Command deserialize(String jsonString) {
		return gson.fromJson(jsonString, commandType);
	}
}
