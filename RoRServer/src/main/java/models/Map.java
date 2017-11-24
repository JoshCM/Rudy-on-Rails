package models;



public class Map {
	
	private final int mapSize = 3;
	private static Map map = null;
	private Square squares [][];
	
	private Map() {
		squares = new Square[mapSize][mapSize];
	}
	
	public static Map getInstance() {
		if (map == null) {
			map = new Map();
		}
		return map;
	}
	
	public void fillMap() {
		for(int i= 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				Square s = new Square(i);
				map.squares[i][j] = s;
			}
		}
	}

}
