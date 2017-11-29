package models.Game;


public class Map {
	
	private static Map map = null;
	private Square squares [][];
	private final int mapSize = 3;
	
	private Map() {
		squares = new Square[mapSize][mapSize];
		
		for(int i= 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				Square s = new Square(i, j);
				squares[i][j] = s;
			}
		}
	}
	
	public static Map getInstance() {
		if (map == null) {
			map = new Map();
		}
		return map;
	}
	
	public Square getSquare(int i, int j) {
		return squares[i][j];
	}
	
	public Square[][] getSquares() {
		return squares;
	}
	
	public void setSquares(Square squares[][]) {
		this.squares = squares;
	}

}
