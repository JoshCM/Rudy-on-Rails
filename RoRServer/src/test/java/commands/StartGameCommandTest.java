package commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import models.game.Compass;
import models.game.Mine;
import models.game.Rail;
import models.game.Square;
import models.game.Stock;
import models.game.Trainstation;
import models.session.GameSession;
import models.session.GameSessionManager;

public class StartGameCommandTest {

	@Test
	public void testLoadedRailEqualsToCreatedRail() {

		// GameSession und Square erstellen
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "HostPlayer");
		Square square = gameSession.getMap().getSquare(0, 0);

		// Erstellen einer neuen Rail
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);

		Rail loadedRail = new Rail(gameSession.getSessionName(), square, railSectionPositions);
		square.setPlaceableOnSquare(loadedRail);

		Rail createdRail = loadedRail.loadFromMap(square, gameSession);

		assertEquals(loadedRail, createdRail);

	}

	@Test
	public void testLoadedTrainStationEqualsToCreatedTrainStation() {

		// GameSession und Square erstellen
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "HostPlayer");
		Square square = new Square(gameSession.getSessionName(), 1, 1);

		// Erstellen eines neuen Bahnhofes
		List<UUID> uuids = new ArrayList<UUID>();
		uuids.add(UUID.randomUUID());

		UUID trainstationId = UUID.randomUUID();
		Trainstation loadedTrainstation = new Trainstation(gameSession.getSessionName(), square, uuids, trainstationId,
				Compass.NORTH,
				new Stock(gameSession.getSessionName(), gameSession.getMap().getSquare(1, 0), trainstationId, Compass.NORTH));
		square.setPlaceableOnSquare(loadedTrainstation);

		Trainstation createdTrainstation = loadedTrainstation.loadFromMap(square, gameSession);
		// assertEquals(loadedTrainstation, createdTrainstation);
		assertEquals(loadedTrainstation, createdTrainstation);

	}

	@Test
	public void testLoadedMineEqualsToCreatedMine() {

		// GameSession und Square erstellen
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "HostPlayer");
		Square square = gameSession.getMap().getSquare(0, 0);

		// Neue Mine mit Rail erstellen
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);

		Rail rail = new Rail(gameSession.getSessionName(), square, railSectionPositions);
		square.setPlaceableOnSquare(rail);
		Mine loadedMine = new Mine(gameSession.getSessionName(), square, rail.getAlignment(), rail.getId());
		rail.setPlaceableOnRail(loadedMine);

		// Mine laden und vergleichen
		Mine createdMine = (Mine) loadedMine.loadFromMap(square, gameSession);
		assertEquals(loadedMine, createdMine);

	}

}
