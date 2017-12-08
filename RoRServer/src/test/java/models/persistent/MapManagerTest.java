package models.persistent;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import models.dummy.DummySignal;
import models.editor.EditorSession;
import models.game.Map;
import models.game.Rail;
import models.game.RailSection;
import models.game.RailSectionPosition;
import persistent.MapManager;

public class MapManagerTest {
	
	MapManager manager = new MapManager();
	Map savedMap = new Map(new EditorSession("unit_test"));
	
	/*
	@Test
	public void mapManager_SavedAndLoadedMapAreEqual() {
		
		DummySignal signal = new DummySignal(savedMap.getSquare(0, 0));
		savedMap.getSquare(0, 0).setPlaceable(new Rail(savedMap.getSquare(0, 0), RailSectionPosition.NORTH, RailSectionPosition.WEST));
		savedMap.getSquare(0, 0).getPlaceableOnSquare().setPlaceableOnRail(signal);
		
		manager.saveMap(savedMap, "testMap");
		Map loadedMap = manager.loadMap("testMap");

		assertEquals(savedMap, loadedMap);
	
	}
	*/
	
	/*
	 * Testet nur in aufrufender Methode (nicht in weiteren Methoden, die aufgerufen werden -> private readFromFile)
	@Test (expected = FileNotFoundException.class)
	public void mapManager_FileNotFoundExceptionThrown() {
		
		manager.loadMap("NotExistingMap");
		
	}
	*/

}
