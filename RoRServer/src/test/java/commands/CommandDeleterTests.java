package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import commands.base.Command;
import commands.editor.DeleteTrainstationCommand;
import communication.MessageInformation;
import models.game.Compass;
import models.game.Rail;
import models.game.Trainstation;
import models.session.EditorSessionManager;
import models.session.RoRSession;

public class CommandDeleterTests {
	@Test
	public void DeleteTrainstationCommandIsDeletedViaId() {
		UUID trainstationId = UUID.randomUUID();

		RoRSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");

		// generiere trainstationRails
		List<Rail> trainstationRails = Arrays.asList(
				new Rail(session.getName(), session.getMap().getSquare(2, 0),
						Arrays.asList(Compass.NORTH, Compass.SOUTH)),
				new Rail(session.getName(), session.getMap().getSquare(2, 1),
						Arrays.asList(Compass.NORTH, Compass.SOUTH)),
				new Rail(session.getName(), session.getMap().getSquare(2, 2),
						Arrays.asList(Compass.NORTH, Compass.SOUTH)));

		// setzt die rails als placeable und generiert trainstationRailIds
		List<UUID> trainstationRailIds = new ArrayList<UUID>();
		List<String> trainstationRailIdStrings = new ArrayList<String>();
		for(Rail trainstationRail : trainstationRails) {
			session.getMap().getSquare(trainstationRail.getXPos(), trainstationRail.getYPos()).setPlaceableOnSquare(trainstationRail);
			trainstationRailIds.add(trainstationRail.getUUID());
			trainstationRailIdStrings.add(trainstationRail.getUUID().toString());
		}

		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("id", trainstationId);
		messageInformation.putValue("trainstationRailIds", trainstationRailIdStrings);
		
		Trainstation trainstation = new Trainstation(session.getName(), session.getMap().getSquare(1, 1), trainstationRailIds, trainstationId, Compass.EAST);
		session.getMap().getSquare(1, 1).setPlaceableOnSquare(trainstation);
		DeleteTrainstationCommand command = new DeleteTrainstationCommand(session, messageInformation);

		String commandName = command.getClass().getName();
		Command deletedCommand = null;
		try {
			deletedCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {

		}

		assertNotNull(deletedCommand);
		assertEquals(commandName, deletedCommand.getClass().getName());
		assertEquals(command.getClass(), deletedCommand.getClass());
	}
}
