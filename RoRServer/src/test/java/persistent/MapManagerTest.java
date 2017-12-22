package persistent;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
import models.game.Compass;
import persistent.MapManager;

public class MapManagerTest {
	@Test
	public void mapManager_SavedAndLoadedMapAreEqual() {
		String sessionName = "TestSession";
		Map map = new Map(sessionName);
		Square railSquare = map.getSquare(0, 0);
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);
		Rail rail = new Rail(sessionName, railSquare, railSectionPositions);
		
		String mapAsJson = MapManager.convertMapToJson(map);
		Map loadedMap = MapManager.convertJsonToMap(mapAsJson);

		assertEquals(map, loadedMap);
	}
}
