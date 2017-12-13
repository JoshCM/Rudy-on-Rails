package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Test;

import commands.base.Command;
import commands.editor.CreateRailCommand;
import commands.editor.CreateTrainstationCommand;
import communication.MessageInformation;
import models.game.RailSectionPosition;
import models.session.EditorSessionManager;
import models.session.RoRSession;

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
	
	@Test
	public void CreateTrainstationCommandIsCreatedViaName() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 1);
		
		RoRSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
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
