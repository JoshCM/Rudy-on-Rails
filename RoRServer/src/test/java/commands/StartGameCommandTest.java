package commands;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.session.GameSession;
import org.junit.Test;

import models.game.Rail;
import models.game.Square;
import models.game.Trainstation;
import models.session.GameSessionManager;
import models.game.Compass;

public class StartGameCommandTest {
	
	@Test
	public void testLoadedRailEqualsToCreatedRail() {
		
		// GameSession und Square erstellen
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		Square square = new Square(gameSession.getName(), 0, 0);
		
		// Erstellen einer neuen Rail
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);

		Rail loadedRail = new Rail(gameSession.getName(), square, railSectionPositions);
		square.setPlaceableOnSquare(loadedRail);
		
		Rail createdRail = loadedRail.loadFromMap(square, gameSession);
		
		assertEquals(loadedRail, createdRail);
		
		
	}
	
	@Test
	public void testLoadedTrainStationEqualsToCreatedTrainStation() {
		
		// GameSession und Square erstellen
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		Square square = new Square(gameSession.getName(), 0, 0);
		
		// Erstellen eines neuen Bahnhofes
		List<UUID> uuids = new ArrayList<UUID>();
		uuids.add(UUID.randomUUID());
		Trainstation loadedTrainstation = new Trainstation(gameSession.getName(), square, uuids, UUID.randomUUID(), Compass.NORTH);
		square.setPlaceableOnSquare(loadedTrainstation);
		
		Trainstation createdTrainstation = loadedTrainstation.loadFromMap(square, gameSession);
		//assertEquals(loadedTrainstation, createdTrainstation);
		assertEquals(loadedTrainstation, createdTrainstation);
		
	}

}
