package models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import models.game.Map;
import models.game.Rail;
import models.game.RailSection;
import models.game.RailSectionPosition;

public class MapManagerTest {
	
	MapManager manager = new MapManager();
	Map savedMap = new Map(null);
	
	@Test
	public void mapManager_SavedAndLoadedMapAreEqual() {
		
		RailSection section = new RailSection(RailSectionPosition.EAST, RailSectionPosition.WEST);
		DummySignal signal = new DummySignal(savedMap.getSquare(0, 0));
		savedMap.getSquare(0, 0).setPlaceable(new Rail(savedMap.getSquare(0, 0), section));
		savedMap.getSquare(0, 0).getPlaceableOnSquare().setPlaceableOnRail(signal);
		
		
		manager.saveMap(savedMap, "testMap");
		Map loadedMap = manager.loadMap("testMap");

		assertEquals(savedMap, loadedMap);
	
	}
	
	/*
	 * Testet nur in aufrufender Methode (nicht in weiteren Methoden, die aufgerufen werden -> private readFromFile)
	@Test (expected = FileNotFoundException.class)
	public void mapManager_FileNotFoundExceptionThrown() {
		
		manager.loadMap("NotExistingMap");
		
	}
	*/

}
