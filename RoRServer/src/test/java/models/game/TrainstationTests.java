package models.game;

import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import commands.CommandCreator;
import commands.base.Command;
import commands.editor.CreateTrainstationCommand;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.session.EditorSessionManager;
import models.session.RoRSession;

public class TrainstationTests {
	
	RoRSession session;
	
	public void initValidTrainstationCommand() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 1);
		
		session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		CreateTrainstationCommand command = new CreateTrainstationCommand(session, messageInformation);
		
		String commandName = command.getClass().getName();
		Command createdCommand = null;
		try {
			createdCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {
			
		}
		createdCommand.execute();
	}
	
	@Test
	public void TrainstationIsCreatedWithRightValuesOverCommand() {
		initValidTrainstationCommand();
		Square square = session.getMap().getSquare(0, 1);
		Assert.assertNotNull(square.getPlaceableOnSquare());
		Assert.assertEquals(Trainstation.class, square.getPlaceableOnSquare().getClass());
		List<UUID> railIds = ((Trainstation)square.getPlaceableOnSquare()).getTrainstationRailIds();
		for(UUID railId : railIds) {
			Rail rail = (Rail)session.getMap().getPlacableById(railId);
			Assert.assertEquals(RailSectionPosition.NORTH, rail.getSection().getNode1());
			Assert.assertEquals(RailSectionPosition.SOUTH, rail.getSection().getNode2());
		}
		Assert.assertEquals(3, railIds.size());
	}
	
	@Test(expected = InvalidModelOperationException.class)
	public void TrainstationIsOnTopEdge() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 0);
		
		session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		CreateTrainstationCommand command = new CreateTrainstationCommand(session, messageInformation);
		
		String commandName = command.getClass().getName();
		Command createdCommand = null;
		try {
			createdCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {
			
		}
		createdCommand.execute();
	}

	@Test(expected = InvalidModelOperationException.class)
	public void TrainstationTopRailIsOnRail() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 1);
		
		// setzen der rail die die exception verursacht
		session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Square square = session.getMap().getSquare(1, 0);
		square.setPlaceable(new Rail(session.getName(),square,RailSectionPosition.NORTH, RailSectionPosition.SOUTH));

		CreateTrainstationCommand command = new CreateTrainstationCommand(session, messageInformation);
		
		String commandName = command.getClass().getName();
		Command createdCommand = null;
		try {
			createdCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {
			
		}
		createdCommand.execute();
	}
	
	@Test(expected = InvalidModelOperationException.class)
	public void TrainstationMidRailIsOnRail() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 1);
		
		// setzen der rail die die exception verursacht
		session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Square square = session.getMap().getSquare(1, 1);
		square.setPlaceable(new Rail(session.getName(),square,RailSectionPosition.NORTH, RailSectionPosition.SOUTH));

		CreateTrainstationCommand command = new CreateTrainstationCommand(session, messageInformation);
		
		String commandName = command.getClass().getName();
		Command createdCommand = null;
		try {
			createdCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {
			
		}
		createdCommand.execute();
	}
	
	@Test(expected = InvalidModelOperationException.class)
	public void TrainstationBottomRailIsOnRail() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 1);
		
		// setzen der rail die die exception verursacht
		session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Square square = session.getMap().getSquare(1, 2);
		square.setPlaceable(new Rail(session.getName(),square,RailSectionPosition.NORTH, RailSectionPosition.SOUTH));

		CreateTrainstationCommand command = new CreateTrainstationCommand(session, messageInformation);
		
		String commandName = command.getClass().getName();
		Command createdCommand = null;
		try {
			createdCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {
			
		}
		createdCommand.execute();
	}
	
	
}
