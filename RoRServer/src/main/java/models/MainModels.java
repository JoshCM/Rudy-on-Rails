package models;

public class MainModels {
	
	public static void main(String [] args) {
		DummyGame game = new DummyGame();
		game.setMap(new Map());
		game.saveMap();

	}

}
