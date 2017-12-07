package models.dummy;

import models.game.Map;
import models.game.Rail;
import models.game.RailSection;

public class MainModels {
	
	public static void main(String [] args) {
		DummyGame game = new DummyGame();
		DummyGame game1 = new DummyGame();
		
		Map map = new Map(null);
		map.getSquare(0, 0).setPlaceable(new Rail(map.getSquare(0, 0), RailSection.STRAIGHT_HORIZONTAL));
		map.getSquare(0, 0).getPlaceableOnSquare().setPlaceableOnRail(new DummySignal(map.getSquare(0, 0)));
		map.getSquare(0, 1).setPlaceable(new Rail(map.getSquare(0, 1), RailSection.CURVE_ES));
		map.getSquare(0, 2).setPlaceable(new DummyContainer());
		map.getSquare(1, 1).setPlaceable(new Rail(map.getSquare(1, 1), RailSection.STRAIGHT_HORIZONTAL));
		map.getSquare(2, 0).setPlaceable(new Rail(map.getSquare(2, 0), RailSection.STRAIGHT_HORIZONTAL));
		map.getSquare(2, 1).setPlaceable(new Rail(map.getSquare(2, 1), RailSection.STRAIGHT_HORIZONTAL));
		
		game.setMap(map);
		game.saveMap("Map1");
		
		game1.loadMap("Map1");
		System.out.println(game1.getMap().getSquare(0, 0).getPlaceableOnSquare());
		System.out.println(game1.getMap().getSquare(0, 1).getPlaceableOnSquare());
		game1.saveMap("Map2");
	}
}
