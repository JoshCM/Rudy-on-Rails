package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import commands.base.Command;
import commands.editor.MoveTrainstationCommand;
import commands.editor.StartEditorCommand;
import communication.MessageInformation;
import junit.framework.Assert;
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
	public void EditorShouldStartEmptyMap() {
		MessageInformation messageInformation = new MessageInformation();
		EditorSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		// name mit '#' am anfang legt eine neue leere Map fest
		session.setMapName("#");

		StartEditorCommand command = new StartEditorCommand(session, messageInformation);

		String commandName = command.getClass().getName();
		Command startCommand = null;
		try {
			startCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {

		}
		startCommand.execute();

		for (Square[] squares : session.getMap().getSquares()) {
			for (Square square : squares) {
				assertEquals(null, square.getPlaceableOnSquare());
			}
		}
	}

	@Test
	public void editorShouldStartLoadedMap() {
		MessageInformation messageInformation = new MessageInformation();
		EditorSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		// name mit '#' am anfang legt eine neue leere Map fest
		session.setMapName("GameDefaultMap");

		StartEditorCommand command = new StartEditorCommand(session, messageInformation);

		String commandName = command.getClass().getName();
		Command startCommand = null;
		try {
			startCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {

		}
		startCommand.execute();

		List<Integer> placeableOnSquareXs = Arrays.asList(6, 7, 8, 9, 9, 9, 8, 7, 6, 6);
		List<Integer> placeableOnSquareYs = Arrays.asList(3, 3, 3, 3, 4, 5, 5, 5, 5, 4);

		for (Square[] squares : session.getMap().getSquares()) {
			for (Square square : squares) {
				boolean contains = false;
				for(int i = 0; i < placeableOnSquareXs.size(); i++) {
					if (placeableOnSquareXs.get(i) == square.getXIndex()
							&& placeableOnSquareYs.get(i) == square.getYIndex()) {
						assertEquals(Rail.class, square.getPlaceableOnSquare().getClass());
						contains = true;
						i++;
					} 
				}
				if(!contains) {
					assertNull(square.getPlaceableOnSquare());
				}
			}
		}
	}

	@Test
	public void testLoadedRailEqualsToCreatedRail() {

		// EditorSession und Square erstellen
		EditorSession editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
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
		EditorSession editorSession = EditorSessionManager.getInstance()
				.createNewEditorSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		Square square = new Square(editorSession.getName(), 1, 1);

		// Erstellen eines neuen Bahnhofes
		List<UUID> uuids = new ArrayList<UUID>();
		uuids.add(UUID.randomUUID());

		UUID trainstationId = UUID.randomUUID();
		Trainstation loadedTrainstation = new Trainstation(editorSession.getName(), square, uuids, trainstationId,
				Compass.NORTH, new Stock(editorSession.getName(), editorSession.getMap().getSquare(1, 0),
						trainstationId, Compass.NORTH));
		square.setPlaceableOnSquare(loadedTrainstation);

		Trainstation createdTrainstation = loadedTrainstation.loadFromMap(square, editorSession);

		assertEquals(loadedTrainstation, createdTrainstation);
	}

}