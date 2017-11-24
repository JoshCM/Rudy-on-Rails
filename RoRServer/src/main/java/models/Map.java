package models;

public class Map {
	
	private static Map map = null;
	private Square squares [][];
	
	private Map() {
		
	}
	
	public static Map getInstance() {
		if (map == null) {
			map = new Map();
		}
		return map;
	}

}