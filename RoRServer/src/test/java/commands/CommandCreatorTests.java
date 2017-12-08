package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Test;

import commands.base.Command;
import commands.editor.CreateRailCommand;
import communication.MessageInformation;
import models.editor.EditorSessionManager;
import models.editor.RoRSession;
import models.game.RailSectionPosition;

public class CommandCreatorTests {
	@Test
	public void CreateRailCommandIsCreatedViaName() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 0);
		messageInformation.putValue("railSectionPositionNode1", RailSectionPosition.NORTH.toString());
		messageInformation.putValue("railSectionPositionNode2", RailSectionPosition.SOUTH.toString());
		
		RoRSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
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
}
