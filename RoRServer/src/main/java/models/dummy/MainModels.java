package models.dummy;

import org.apache.log4j.Logger;

import communication.queue.receiver.QueueReceiver;
import models.game.Map;
import models.game.Rail;
import models.game.RailSection;
import models.game.RailSectionPosition;

public class MainModels {
	static Logger log = Logger.getLogger(QueueReceiver.class.getName());
	
	public static void main(String [] args) {
		DummyGame game = new DummyGame();
		DummyGame game1 = new DummyGame();
		
		Map map = new Map(null);
		map.getSquare(0, 0).setPlaceable(new Rail(map.getSquare(0, 0), RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
		map.getSquare(0, 0).getPlaceableOnSquare().setPlaceableOnRail(new DummySignal(map.getSquare(0, 0)));
		map.getSquare(0, 1).setPlaceable(new Rail(map.getSquare(0, 1), RailSectionPosition.NORTH, RailSectionPosition.EAST));
		map.getSquare(0, 2).setPlaceable(new DummyContainer());
		map.getSquare(1, 1).setPlaceable(new Rail(map.getSquare(1, 1), RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
		map.getSquare(2, 0).setPlaceable(new Rail(map.getSquare(2, 0), RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
		map.getSquare(2, 1).setPlaceable(new Rail(map.getSquare(1, 1), RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
		
		game.setMap(map);
		game.saveMap("Map1");
		
		game1.loadMap("Map1");
		log.info(game1.getMap().getSquare(0, 0).getPlaceableOnSquare());
		log.info(game1.getMap().getSquare(0, 1).getPlaceableOnSquare());
		game1.saveMap("Map2");
	}
}
