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
import models.game.Stock;
import models.game.Trainstation;
import models.game.Playertrainstation;
import models.session.EditorSessionManager;
import models.session.RoRSession;

public class CommandDeleterTests {
	@Test
	public void DeleteTrainstationCommandIsDeletedViaId() {
		UUID trainstationId = UUID.randomUUID();
		UUID stockId = UUID.randomUUID();

		RoRSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");

		// kann für den Test des Commands leer sein, da kein Trainstation erstellt werden soll
		List<String> trainstationRailIdStrings = new ArrayList<String>();

		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("trainstationId", trainstationId);
		messageInformation.putValue("stockId", stockId);
		messageInformation.putValue("trainstationRailIds", trainstationRailIdStrings);
		
		Playertrainstation trainstation = new Playertrainstation(session.getName(), session.getMap().getSquare(1, 1), new ArrayList<UUID>(), trainstationId, Compass.EAST, new Stock(session.getName(), session.getMap().getSquare(1, 0), trainstationId, stockId, Compass.EAST));
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
