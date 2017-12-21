package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.google.gson.JsonObject;

import commands.base.Command;
import commands.editor.CreateRailCommand;
import commands.editor.CreateTrainstationCommand;
import commands.editor.DeleteTrainstationCommand;
import communication.MessageInformation;
import models.game.Compass;
import models.game.Rail;
import models.game.Trainstation;
import models.session.EditorSessionManager;
import models.session.RoRSession;

public class CommandCreatorTests {
	@Test
	public void CreateRailCommandIsCreatedViaName() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 0);
		messageInformation.putValue("railSections", new ArrayList<JsonObject>());

		RoRSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		CreateRailCommand command = new CreateRailCommand(session, messageInformation);

		String commandName = command.getClass().getName();
		Command createdCommand = null;
		try {
			createdCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {

		}

		assertNotNull(createdCommand);
		assertEquals(commandName, createdCommand.getClass().getName());
		assertEquals(command.getClass(), createdCommand.getClass());
	}

	@Test
	public void CreateTrainstationCommandIsCreatedViaName() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 1);
		messageInformation.putValue("alignment", Compass.NORTH.toString());

		RoRSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		CreateTrainstationCommand command = new CreateTrainstationCommand(session, messageInformation);

		String commandName = command.getClass().getName();
		Command createdCommand = null;
		try {
			createdCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {

		}

		assertNotNull(createdCommand);
		assertEquals(commandName, createdCommand.getClass().getName());
		assertEquals(command.getClass(), createdCommand.getClass());
	}
}
