package commands;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import commands.game.StartGameCommand;
import communication.MessageInformation;
import models.game.Map;
import models.game.Rail;
import models.game.RailSection;
import models.game.Square;
import models.session.GameSession;
import models.session.GameSessionManager;
import models.session.RoRSession;
import models.game.Compass;
import persistent.MapManager;

public class StartGameCommandTest {
	
	@Test
	public void testLoadedRailEqualsToCreatedRail() {
		
		// GameSession und Square erstellen
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		Square square = new Square(gameSession.getSessionName(), 0, 0);
		
		// Erstellen einer neuen Rail
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);

		Rail loadedRail = new Rail(gameSession.getSessionName(), square, railSectionPositions);
		square.setPlaceable(loadedRail);
		
		Rail createdRail = loadedRail.loadFromMap(square, gameSession);
		
		assertEquals(loadedRail, createdRail);
		
		
	}
	
	@Test
	public void testLoadedTrainStationEqualsToCreatedTrainStation() {
		
	}

}
