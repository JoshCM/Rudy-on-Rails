package commands;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import models.game.Rail;
import models.game.Square;
import models.game.Stock;
import models.game.Trainstation;
import models.session.EditorSession;
import models.session.EditorSessionManager;
import models.session.GameSession;
import models.session.GameSessionManager;
import models.game.Compass;

public class StartEditorCommandTest {
	
	@Test
	public void testLoadedRailEqualsToCreatedRail() {
		
		// EditorSession und Square erstellen
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		Square square = new Square(editorSession.getName(), 0, 0);
		
		// Erstellen einer neuen Rail
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);

		Rail loadedRail = new Rail(editorSession.getName(), square, railSectionPositions);
		square.setPlaceableOnSquare(loadedRail);
		
		Rail createdRail = loadedRail.loadFromMap(square, editorSession);
		
		assertEquals(loadedRail, createdRail);
	}
	
	@Test
	public void testLoadedTrainStationEqualsToCreatedTrainStation() {
		
		// EditorSession und Square erstellen
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		Square square = new Square(editorSession.getName(), 1, 1);
		
		// Erstellen eines neuen Bahnhofes
		List<UUID> uuids = new ArrayList<UUID>();
		uuids.add(UUID.randomUUID());
		
		UUID trainstationId = UUID.randomUUID();
		Trainstation loadedTrainstation = new Trainstation(editorSession.getName(), square, uuids, trainstationId, Compass.NORTH, new Stock(editorSession.getName(), editorSession.getMap().getSquare(1, 0), trainstationId, Compass.NORTH));
		square.setPlaceableOnSquare(loadedTrainstation);
		
		Trainstation createdTrainstation = loadedTrainstation.loadFromMap(square, editorSession);
		
		assertEquals(loadedTrainstation, createdTrainstation);
	}

}