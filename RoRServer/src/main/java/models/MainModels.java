package models;

public class MainModels {
	
	public static void main(String [] args) {
		DummyGame game = new DummyGame();		
		Map map = new Map();
		map.getSquare(0, 0).setPlaceable(new Rail(map.getSquare(0, 0), RailSection.STRAIGHT_HORIZONTAL));
		map.getSquare(0, 0).getPlaceableOnSquare().setPlaceableOnRail(new DummySignal(map.getSquare(0, 0)));
		map.getSquare(0, 1).setPlaceable(new Rail(map.getSquare(0, 1), RailSection.CURVE_ES));
		map.getSquare(0, 2).setPlaceable(new DummyContainer());
		game.setMap(map);
		game.saveMap("Map1");
		
		DummyGame game1 = new DummyGame();
		game1.loadMap("Maps/Map1.map");
		System.out.println(game1.getMap().getSquare(0, 0).getPlaceableOnSquare());
		System.out.println(game1.getMap().getSquare(0, 1).getPlaceableOnSquare());

	}

}
