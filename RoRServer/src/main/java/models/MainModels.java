package models;

public class MainModels {
	
	public static void main(String [] args) {
		DummyGame game = new DummyGame();
		game.setMap(new Map());
		game.saveMap("Map1");
		
		Map map = new Map();
		map.getSquare(0, 0).setPlaceable(new Rail(map.getSquare(0, 0), RailSection.STRAIGHT_HORIZONTAL));
		game.setMap(map);
		game.saveMap("Map1");
		
		DummyGame game1 = new DummyGame();
		game1.loadMap("Maps/Map1.map");
		System.out.println(game1.getMap().getSquare(0, 0).getPlaceableOnSquare());

	}

}
