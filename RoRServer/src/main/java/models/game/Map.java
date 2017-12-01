package models.game;

/**
 * Klasse, die das Spielfeld darstellt und aus Feldern (Squares) besteht
 */
public class Map extends ModelBase {
	
	private Square squares [][];
	private final int mapSize = 3;
	
	/**
	 * Jedes Square auf der Map braucht einen Index,
	 * um jedem Objekt, das auf einem Square platziert wird, ein eindeutiges Objekt zuzuordnen
	 */
	public Map() {
		squares = new Square[mapSize][mapSize];
		
		for(int i= 0; i < mapSize; i++) {
			for(int j = 0; j < mapSize; j++) {
				Square s = new Square(i, j);
				squares[i][j] = s;
			}
		}
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
