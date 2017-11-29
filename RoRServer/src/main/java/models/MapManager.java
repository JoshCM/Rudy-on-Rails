package models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MapManager {

	private Gson gson;
	private Gson gson2;

	public MapManager() {
		// gson = new Gson();
		gson = new GsonBuilder().registerTypeAdapter(Convertable.class, new ConvertableDeserializer<Convertable>()).setPrettyPrinting().create();
		gson2 = new GsonBuilder().registerTypeAdapter(PlaceableOnSquare.class, new ConvertableDeserializer<PlaceableOnSquare>()).create();
	}

	public Map loadMap(String mapName) {
	    String jsonMap = readFromFile(mapName);
	    System.out.println("Eingelesene Map: " + jsonMap);
		Map map = gson2.fromJson(jsonMap, Map.class);
		return map;
	}
	
	private String readFromFile(String mapName){
		
		String jsonMap = "";	
		BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(mapName));
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

	public void saveMap(Map map, String mapName) {
		String jsonMap = gson.toJson(map);
		System.out.println("Gespeicherte Map: "+ jsonMap);
		saveToFile(jsonMap, mapName);
	}
	
	private void saveToFile(String jsonMap, String mapName){
		try (PrintWriter out = new PrintWriter("Maps/"+ mapName + ".map")) {
			out.println(jsonMap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
