package persistent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
	private final static String MAP_PLAYER_SLOTS = "maps";
	private final static String ext = ".map";
	private final static String slots = ".slots";
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
		
		String jsonMap = readFromFile(mapName, ext);
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
	private static String readFromFile(String mapName, String ending) {
		String jsonMap = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(OUTPUT_DIR_PATH + mapName + ending));
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
	public static void saveMap(Map map, String ending) {
		String jsonMap = convertMapToJson(map);
		log.info("Gespeicherte Map: " + jsonMap);
		
		String mapSizeDescription = getMapSizeDescriptionForMap(map);
		String filename = map.getName() + " (" + mapSizeDescription+ ", " + map.getAvailablePlayerSlots() + " Spieler)";
		saveToFile(jsonMap, filename, ending);
	}

	private static String getMapSizeDescriptionForMap(Map map) {
		String mapSizeDescription = "";
		if(map.getMapSize() <= 30) {
			mapSizeDescription = "Sehr klein";
		} else if(map.getMapSize() <= 50) {
			mapSizeDescription = "Klein";
		} else if(map.getMapSize() <= 50) {
			mapSizeDescription = "Normal";
		} else {
			mapSizeDescription = "Gro�";
		}
		return mapSizeDescription;
	}

	public static String convertMapToJson(Map map) {
		String jsonMap = gsonSaver.toJson(map);
		return jsonMap;
	}

	public static Map convertJsonToMap(String mapAsJson) {
		Map map = gsonLoader.fromJson(mapAsJson, Map.class);
		return map;
	}
	
	/**
	 * Erstellt anhand der MapNames im maps Ordner die Einträge für die maps.slots
	 * Wenn eine Map darin schon enthalten ist, dann wird nicht berücksichtigt
	 */
	public static void initAvailablePlayerSlots() {
		for(String mapName : readMapNames()) {
			if(getAvailablePlayerSlotsByMapName(mapName) < 0) {
				String jsonMap = readFromFile(mapName, ext);
				String searchString = "availablePlayerSlots\": ";
				int indexOfAvailablePlayerSlots = jsonMap.indexOf(searchString) + searchString.length();
				int indexOfEndOfAvailablePlayerSlots = jsonMap.substring(indexOfAvailablePlayerSlots).indexOf(",") + indexOfAvailablePlayerSlots;
				int availablePlayerSlots = Integer.parseInt(jsonMap.substring(indexOfAvailablePlayerSlots, indexOfEndOfAvailablePlayerSlots));
				setAvailablePlayerSlotsForMapName(mapName, availablePlayerSlots);
			}
		}
	}
	
	/**
	 * Setzt die AvailablePlayerSlots Anzahl in der HashMap für diesen MapName
	 * @param mapName
	 * @param availablePlayerSlots
	 */
	public static void setAvailablePlayerSlotsForMapName(String mapName, int availablePlayerSlots) {
		double availablePlayerSlotsDouble = Double.valueOf(availablePlayerSlots);
		HashMap<String, Double> availablePlayerSlotsMap = getAvailablePlayerSlotsMap();
		boolean exists = false;
		for(Entry<String, Double> entry : availablePlayerSlotsMap.entrySet()) {
			if (entry.getKey().equals(mapName)) {
				entry.setValue(availablePlayerSlotsDouble);
				exists = true;
			}
		}
		if(!exists) {
			availablePlayerSlotsMap.put(mapName, availablePlayerSlotsDouble);
		}
		String availablePlayerSlotsMapJson = gsonSaver.toJson(availablePlayerSlotsMap, availablePlayerSlotsMap.getClass());
		saveToFile(availablePlayerSlotsMapJson, MAP_PLAYER_SLOTS, slots);
	}
	
	/**
	 * Gibt die Anzahl der AvailablePlayers der Map mit diesem Namen zurück
	 * @param mapName
	 * @return
	 */
	public static int getAvailablePlayerSlotsByMapName(String mapName) {
		HashMap<String, Double> availablePlayerSlotsMap = getAvailablePlayerSlotsMap();
		for(Entry<String, Double> entry : availablePlayerSlotsMap.entrySet()) {
			if (entry.getKey().equals(mapName)) {
				// workaround, da in dem entry immer ein double ist
	    		return entry.getValue().intValue();
			}
		}
		return -1;
	}
	
	/**
	 * Gibt die HashMap der MapNames und AvailablePlayerSlots zurück
	 * Falls sie nicht existiert wird sie neu erzeugt
	 * @return Die Hashmap mit MapName und AvailablePlayerSlots
	 */
	@SuppressWarnings("unchecked")
	private static HashMap<String, Double> getAvailablePlayerSlotsMap(){
		File folder = new File(OUTPUT_DIR_PATH);
		if(folder.exists()) {
			File availablePlayerSlotsMapFile = new File(OUTPUT_DIR_PATH + MAP_PLAYER_SLOTS + slots);
			if(availablePlayerSlotsMapFile.exists()) {
				HashMap<String, Double> availablePlayerSlotsMap = new HashMap<>();
				availablePlayerSlotsMap = gsonLoader.fromJson(readFromFile(MAP_PLAYER_SLOTS, slots),availablePlayerSlotsMap.getClass());
				if(availablePlayerSlotsMap != null)
					return availablePlayerSlotsMap;
				else
					return new HashMap<String, Double>();
			}else {
				return new HashMap<String, Double>();
			}
		}
		return null;
	}

	/**
	 * Speichert das JsonObjekt im Dateisystem ab
	 * 
	 * @param jsonMap:
	 *            Genereiertes JsonObjekt
	 * @param mapName:
	 *            Dateiname zum Speichern
	 */
	private static void saveToFile(String jsonMap, String mapName, String ending) {

		// erzeugt den maps-Ordner wenn er noch nicht existiert
		File mapsDir = new File(OUTPUT_DIR_PATH);
		if (!mapsDir.exists()) {
			mapsDir.mkdir();
		}

		try (PrintWriter out = new PrintWriter(OUTPUT_DIR_PATH + mapName + ending)) {
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
		if (folder.exists()) { 
			for (File fileEntry : folder.listFiles()) {
				if (fileEntry.isFile() && fileEntry.getName().endsWith(ext))
					mapList.add(fileEntry.getName().replace(ext, ""));
			}
		}
		return mapList;
	}
}
