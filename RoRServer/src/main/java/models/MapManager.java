package models;

import com.google.gson.Gson;

public class MapManager {
	
	private Gson gson;
	
	public MapManager(){
		
	}
	
	public Map loadMap(String jsonMap){
		Map map = gson.fromJson(jsonMap, Map.class);
		return map;
	}
	
	public void saveMap(Map map){
		String jsonMap = gson.toJson(map);
		System.out.println(map);
	}

}
