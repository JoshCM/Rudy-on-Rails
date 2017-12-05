package models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import models.game.Map;
import models.game.Placeable;
import models.game.PlaceableOnRail;
import models.game.PlaceableOnSquare;


/**
 * 
 * @author Andreas Pöhler, Juliane Lies, Jakob Liskow
 * Der MapManager kann für den Editor und das Spiel verwendet werden, um Maps zu laden/speichern.
 *
 */

public class MapManager {

	private final String dir = "Maps/";
	private final String ext = ".map";
	private Gson gsonLoader;
	private Gson gsonSaver;
	
	public MapManager() {
		
		// Loader: Hier werden die erforderlichen Adapter zum Deserialisieren für Gson bereitgestellt.
		gsonLoader = new GsonBuilder()
		.registerTypeAdapter(Placeable.class, new PlaceableDeserializer<Placeable>())
		.registerTypeAdapter(PlaceableOnSquare.class, new PlaceableDeserializer<PlaceableOnSquare>())
		.registerTypeAdapter(PlaceableOnRail.class, new PlaceableDeserializer<PlaceableOnRail>())
		.setPrettyPrinting().create();
		
		// Saver
		gsonSaver = new GsonBuilder().setPrettyPrinting().create();
		
	}

	/**
	 * Es wird eine Map über den gegebenen Dateinamen erstellt und zugegeben.
	 * @param mapName Dateiname der Map
	 * @return Gibt eine Map für ein Spiel zurück
	 */
	public Map loadMap(String mapName) {
	    String jsonMap = readFromFile(mapName);
	    System.out.println("Eingelesene Map: " + jsonMap);
		Map map = gsonLoader.fromJson(jsonMap, Map.class);
		return map;
	}
	
	/**
	 * Lies eine Datei über den gegebenen Namen ein und gibt ein JsonObjekt zurück
	 * @param mapName: Dateiname der Map
	 * @return Gibt ein JsonObjekt zurück
	 * 
	 */
	private String readFromFile(String mapName){
		
		String jsonMap = "";	
		BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dir +  mapName + ext));
            String line;
            while ((line = br.readLine()) != null) {
                jsonMap += line;
            }
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
	 * @param map: Das zu speichernde Map-Objekt
	 * @param mapName: Der Name der Map für das Dateisystem
	 */
	public void saveMap(Map map, String mapName) {
		String jsonMap = gsonSaver.toJson(map);
		System.out.println("Gespeicherte Map: "+ jsonMap);
		saveToFile(jsonMap, mapName);
	}
	
	/**
	 * Speichert das JsonObjekt im Dateisystem ab
	 * @param jsonMap: Genereiertes JsonObjekt
	 * @param mapName: Dateiname zum Speichern
	 */ 
	private void saveToFile(String jsonMap, String mapName){
		try (PrintWriter out = new PrintWriter(dir + mapName + ext)) {
			out.println(jsonMap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
