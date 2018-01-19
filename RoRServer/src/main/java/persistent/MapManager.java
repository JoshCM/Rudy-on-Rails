package persistent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import communication.queue.receiver.QueueReceiver;
import exceptions.MapNotFoundException;
import models.game.Map;
import models.game.Placeable;
import models.game.PlaceableOnRail;
import models.game.PlaceableOnSquare;

/**
 * 
 * @author Andreas Pöhler, Juliane Lies, Jakob Liskow Der MapManager kann für
 *         den Editor und das Spiel verwendet werden, um Maps zu
 *         laden/speichern.
 *
 */

public class MapManager {
	static Logger log = Logger.getLogger(QueueReceiver.class.getName());
	private final static String OUTPUT_DIR_PATH = "maps\\";
	private final static String ext = ".map";
	private final static Gson gsonLoader = new GsonBuilder()
			.registerTypeAdapter(Placeable.class, new PlaceableDeserializer<Placeable>())
			.registerTypeAdapter(PlaceableOnSquare.class, new PlaceableDeserializer<PlaceableOnSquare>())
			.registerTypeAdapter(PlaceableOnRail.class, new PlaceableDeserializer<PlaceableOnRail>())
			.setPrettyPrinting().create();
	private final static Gson gsonSaver = new GsonBuilder().setPrettyPrinting().create();

	private MapManager() {
	}

	/**
	 * Es wird eine Map über den gegebenen Dateinamen erstellt und zurückgegeben.
	 * 
	 * @param mapName
	 *            Dateiname der Map
	 * @return Gibt eine Map für ein Spiel zurück
	 * @throws MapNotFoundException 
	 */
	public static Map loadMap(String mapName) throws MapNotFoundException {
		if(mapName == null)
			throw new MapNotFoundException(String.format("MapName ist %s", mapName));
		
		String jsonMap = readFromFile(mapName);
		Map map = convertJsonToMap(jsonMap);
		return map;
	}

	/**
	 * Liest eine Datei über den gegebenen Namen ein und gibt ein JsonObjekt zurück
	 * 
	 * @param mapName:
	 *            Dateiname der Map
	 * @return Gibt ein JsonObjekt zurück
	 * 
	 */
	private static String readFromFile(String mapName) {
		String jsonMap = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(OUTPUT_DIR_PATH + mapName + ext));
			String line;
			while ((line = br.readLine()) != null) {
				jsonMap += line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return jsonMap;
	}

	/**
	 * Speichert die übergebene Map unter dem übergebenen Namen
	 * 
	 * @param map:
	 *            Das zu speichernde Map-Objekt
	 * @param mapName:
	 *            Der Name der Map für das Dateisystem
	 */
	public static void saveMap(Map map) {
		String jsonMap = convertMapToJson(map);
		log.info("Gespeicherte Map: " + jsonMap);
		saveToFile(jsonMap, map.getDescription());
	}

	public static String convertMapToJson(Map map) {
		String jsonMap = gsonSaver.toJson(map);
		return jsonMap;
	}

	public static Map convertJsonToMap(String mapAsJson) {
		Map map = gsonLoader.fromJson(mapAsJson, Map.class);
		return map;
	}
	
	public static int loadAvailablePlayerSlots(String mapName) throws MapNotFoundException {
		return loadMap(mapName).getAvailablePlayerSlots();
	}

	/**
	 * Speichert das JsonObjekt im Dateisystem ab
	 * 
	 * @param jsonMap:
	 *            Genereiertes JsonObjekt
	 * @param mapName:
	 *            Dateiname zum Speichern
	 */
	private static void saveToFile(String jsonMap, String mapName) {

		// erzeugt den maps-Ordner wenn er noch nicht existiert
		File mapsDir = new File(OUTPUT_DIR_PATH);
		if (!mapsDir.exists()) {
			mapsDir.mkdir();
		}

		try (PrintWriter out = new PrintWriter(OUTPUT_DIR_PATH + mapName + ext)) {
			out.println(jsonMap);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static List<String> readMapNames() {
		List<String> mapList = new ArrayList<String>();
		File folder = new File(OUTPUT_DIR_PATH);
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isFile())
				mapList.add(fileEntry.getName().replace(ext, ""));
		}
		return mapList;
	}
}
