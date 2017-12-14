package models.persistent;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import models.dummy.DummySignal;
import models.game.Map;
import models.game.Rail;
import models.game.RailSection;
import models.game.RailSectionPosition;
import models.session.EditorSession;
import persistent.MapManager;

public class MapManagerTest {
	
	Map savedMap = new Map("test");
	
	@Test
	public void mapManager_SavedAndLoadedMapAreEqual() {
		
		DummySignal signal = new DummySignal(savedMap.getSquare(0, 0));
		savedMap.getSquare(0, 0).setPlaceable(new Rail("TestSession", savedMap.getSquare(0, 0), RailSectionPosition.NORTH, RailSectionPosition.WEST));
		((Rail)savedMap.getSquare(0, 0).getPlaceableOnSquare()).setPlaceableOnRail(signal);
		
		String mapAsJson = MapManager.convertMapToJson(savedMap);
		Map loadedMap = MapManager.convertJsonToMap(mapAsJson);

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
