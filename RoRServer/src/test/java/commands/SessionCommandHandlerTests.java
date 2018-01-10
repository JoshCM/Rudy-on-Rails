package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.UUID;

import models.session.RoRSession;
import org.junit.Test;

import com.google.gson.JsonObject;

import commands.base.Command;
import communication.MessageInformation;
import models.game.Compass;
import models.session.EditorSessionManager;

public class SessionCommandHandlerTests {
	@Test
	public void CreateRailCommandIsCreatedViaName() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 0);
		messageInformation.putValue("railSectionList", new ArrayList<JsonObject>());

		RoRSession session = EditorSessionManager.getInstance().createEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		CreateRailCommand command = new CreateRailCommand(session, messageInformation);

		String commandName = command.getClass().getName();
		Command createdCommand = null;
		try {
			createdCommand = SessionCommandHandler.createCommandForName(commandName, session, messageInformation);
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

		RoRSession session = EditorSessionManager.getInstance().createEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		CreateTrainstationCommand command = new CreateTrainstationCommand(session, messageInformation);

		String commandName = command.getClass().getName();
		Command createdCommand = null;
		try {
			createdCommand = SessionCommandHandler.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {

		}

		assertNotNull(createdCommand);
		assertEquals(commandName, createdCommand.getClass().getName());
		assertEquals(command.getClass(), createdCommand.getClass());
	}
}
