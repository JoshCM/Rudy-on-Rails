package models.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import commands.CommandCreator;
import commands.base.Command;
import commands.editor.CreateTrainstationCommand;
import commands.editor.DeleteTrainstationCommand;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.session.EditorSessionManager;
import models.session.RoRSession;

public class TrainstationTests {
	
	RoRSession session;
	
	public void initValidTrainstationCommand() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 1);
		messageInformation.putValue("yPos", 2);
		messageInformation.putValue("alignment", Compass.EAST.toString());
		
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
		Square square = session.getMap().getSquare(1, 2);
		Assert.assertNotNull(square.getPlaceableOnSquare());
		Assert.assertEquals(Trainstation.class, square.getPlaceableOnSquare().getClass());
		List<UUID> railIds = ((Trainstation)square.getPlaceableOnSquare()).getTrainstationRailIds();
		for(UUID railId : railIds) {
			Rail rail = (Rail)session.getMap().getPlaceableById(railId);
			Assert.assertEquals(Compass.NORTH, rail.getFirstSection().getNode1());
			Assert.assertEquals(Compass.SOUTH, rail.getFirstSection().getNode2());
		}
		Assert.assertEquals(3, railIds.size());
	}
	
	@Test(expected = InvalidModelOperationException.class)
	public void TrainstationIsOnTopEdge() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 0);
		messageInformation.putValue("alignment", Compass.EAST.toString());
		
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
		messageInformation.putValue("alignment", Compass.EAST.toString());
		
		// setzen der rail die die exception verursacht
		session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Square square = session.getMap().getSquare(1, 0);
		square.setPlaceableOnSquare(new Rail(session.getName(),square,Arrays.asList(Compass.NORTH, Compass.SOUTH)));

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
		messageInformation.putValue("alignment", Compass.EAST.toString());
		
		// setzen der rail die die exception verursacht
		session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Square square = session.getMap().getSquare(1, 1);
		square.setPlaceableOnSquare(new Rail(session.getName(),square,Arrays.asList(Compass.NORTH, Compass.SOUTH)));

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
		messageInformation.putValue("alignment", Compass.EAST.toString());
		
		// setzen der rail die die exception verursacht
		session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		Square square = session.getMap().getSquare(1, 2);
		square.setPlaceableOnSquare(new Rail(session.getName(),square,Arrays.asList(Compass.NORTH, Compass.SOUTH)));

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
	public void TrainstationRotationClockwiseShouldBeValid() {
		UUID trainstationId = UUID.randomUUID();
		
		RoRSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		
		// generiere trainstationRails
		List<Rail> trainstationRails = Arrays.asList(new Rail(session.getName(), session.getMap().getSquare(2, 0), Arrays.asList(Compass.NORTH, Compass.SOUTH)),
				new Rail(session.getName(), session.getMap().getSquare(2, 1), Arrays.asList(Compass.NORTH, Compass.SOUTH)),
				new Rail(session.getName(), session.getMap().getSquare(2, 2), Arrays.asList(Compass.NORTH, Compass.SOUTH)));
		
		// setzt die rails als placeable und generiert trainstationRailIds
		List<UUID> trainstationRailIds = new ArrayList<UUID>();
		List<String> trainstationRailIdStrings = new ArrayList<String>();
		for(Rail trainstationRail : trainstationRails) {
			session.getMap().getSquare(trainstationRail.getXPos(), trainstationRail.getYPos()).setPlaceableOnSquare(trainstationRail);
			trainstationRailIds.add(trainstationRail.getId());
			trainstationRailIdStrings.add(trainstationRail.getId().toString());
		}

		Trainstation trainstation = new Trainstation(session.getName(), session.getMap().getSquare(1, 1), trainstationRailIds, trainstationId, Compass.EAST);
		session.getMap().getSquare(1, 1).setPlaceableOnSquare(trainstation);
		
		Assert.assertTrue(trainstation.validateRotation(true));
	}
	
	@Test
	public void TrainstationRotationCounterClockwiseShouldBeValid() {
		UUID trainstationId = UUID.randomUUID();
		
		RoRSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		
		// generiere trainstationRails
		List<Rail> trainstationRails = Arrays.asList(new Rail(session.getName(), session.getMap().getSquare(2, 0), Arrays.asList(Compass.NORTH, Compass.SOUTH)),
				new Rail(session.getName(), session.getMap().getSquare(2, 1), Arrays.asList(Compass.NORTH, Compass.SOUTH)),
				new Rail(session.getName(), session.getMap().getSquare(2, 2), Arrays.asList(Compass.NORTH, Compass.SOUTH)));
		
		// setzt die rails als placeable und generiert trainstationRailIds
		List<UUID> trainstationRailIds = new ArrayList<UUID>();
		List<String> trainstationRailIdStrings = new ArrayList<String>();
		for(Rail trainstationRail : trainstationRails) {
			session.getMap().getSquare(trainstationRail.getXPos(), trainstationRail.getYPos()).setPlaceableOnSquare(trainstationRail);
			trainstationRailIds.add(trainstationRail.getId());
			trainstationRailIdStrings.add(trainstationRail.getId().toString());
		}

		Trainstation trainstation = new Trainstation(session.getName(), session.getMap().getSquare(1, 1), trainstationRailIds, trainstationId, Compass.EAST);
		session.getMap().getSquare(1, 1).setPlaceableOnSquare(trainstation);
		
		Assert.assertTrue(trainstation.validateRotation(false));
	}
	
	@Test
	public void TrainstationRotationClockwiseShouldBeDone() {
		UUID trainstationId = UUID.randomUUID();
		
		RoRSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		
		// generiere trainstationRails
		List<Rail> trainstationRails = Arrays.asList(new Rail(session.getName(), session.getMap().getSquare(2, 0), Arrays.asList(Compass.NORTH, Compass.SOUTH)),
				new Rail(session.getName(), session.getMap().getSquare(2, 1), Arrays.asList(Compass.NORTH, Compass.SOUTH)),
				new Rail(session.getName(), session.getMap().getSquare(2, 2), Arrays.asList(Compass.NORTH, Compass.SOUTH)));
		
		// geklonte liste um alte werte zu haben
		List<Rail> beforeRotationTrainstationRails = new ArrayList<>();
		for(Rail trainstationRail : trainstationRails) {
			beforeRotationTrainstationRails.add(new Rail(session.getName(), session.getMap().getSquare(20, 20), Arrays.asList(trainstationRail.getFirstSection().getNode1(), trainstationRail.getFirstSection().getNode2())));
		}
		// setzt die rails als placeable und generiert trainstationRailIds
		List<UUID> trainstationRailIds = new ArrayList<UUID>();
		List<String> trainstationRailIdStrings = new ArrayList<String>();
		for(Rail trainstationRail : trainstationRails) {
			session.getMap().getSquare(trainstationRail.getXPos(), trainstationRail.getYPos()).setPlaceableOnSquare(trainstationRail);
			trainstationRailIds.add(trainstationRail.getId());
			trainstationRailIdStrings.add(trainstationRail.getId().toString());
		}

		Trainstation trainstation = new Trainstation(session.getName(), session.getMap().getSquare(1, 1), trainstationRailIds, trainstationId, Compass.EAST);
		session.getMap().getSquare(1, 1).setPlaceableOnSquare(trainstation);
		
		trainstation.rotate(true);
		
		// testen der einzelnen squares ob die richtigen placeables draufstehen
		for(int i = 0; i <= 2; i++) {
			Assert.assertEquals(session.getMap().getSquare((2-i), 2).getPlaceableOnSquare().getId(), trainstationRailIds.get(i));
			Compass notExpectedNode1 = beforeRotationTrainstationRails.get(i).getFirstSection().getNode1();
			Compass actualNode1 = trainstationRails.get(i).getFirstSection().getNode1();
			Assert.assertNotEquals(notExpectedNode1, actualNode1);
			
			Compass notExpectedNode2 = beforeRotationTrainstationRails.get(i).getFirstSection().getNode2();
			Compass actualNode2 = trainstationRails.get(i).getFirstSection().getNode2();
			Assert.assertNotEquals(notExpectedNode2, actualNode2);
		}
		
	}
	
	@Test
	public void TrainstationRotationCounterClockwiseShouldBeDone() {
		UUID trainstationId = UUID.randomUUID();
		
		RoRSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString());
		
		// generiere trainstationRails
		List<Rail> trainstationRails = Arrays.asList(new Rail(session.getName(), session.getMap().getSquare(2, 0), Arrays.asList(Compass.NORTH, Compass.SOUTH)),
				new Rail(session.getName(), session.getMap().getSquare(2, 1), Arrays.asList(Compass.NORTH, Compass.SOUTH)),
				new Rail(session.getName(), session.getMap().getSquare(2, 2), Arrays.asList(Compass.NORTH, Compass.SOUTH)));
		
		// geklonte liste um alte werte zu haben
		List<Rail> beforeRotationTrainstationRails = new ArrayList<>();
		for(Rail trainstationRail : trainstationRails) {
			beforeRotationTrainstationRails.add(new Rail(session.getName(), session.getMap().getSquare(20, 20), Arrays.asList(trainstationRail.getFirstSection().getNode1(), trainstationRail.getFirstSection().getNode2())));
		}
		// setzt die rails als placeable und generiert trainstationRailIds
		List<UUID> trainstationRailIds = new ArrayList<UUID>();
		List<String> trainstationRailIdStrings = new ArrayList<String>();
		for(Rail trainstationRail : trainstationRails) {
			session.getMap().getSquare(trainstationRail.getXPos(), trainstationRail.getYPos()).setPlaceableOnSquare(trainstationRail);
			trainstationRailIds.add(trainstationRail.getId());
			trainstationRailIdStrings.add(trainstationRail.getId().toString());
		}

		Trainstation trainstation = new Trainstation(session.getName(), session.getMap().getSquare(1, 1), trainstationRailIds, trainstationId, Compass.EAST);
		session.getMap().getSquare(1, 1).setPlaceableOnSquare(trainstation);
		
		trainstation.rotate(false);
		
		// testen der einzelnen squares ob die richtigen placeables draufstehen
		for(int i = 0; i <= 2; i++) {
			Assert.assertEquals(session.getMap().getSquare(i, 0).getPlaceableOnSquare().getId(), trainstationRailIds.get(i));
			Compass notExpectedNode1 = beforeRotationTrainstationRails.get(i).getFirstSection().getNode1();
			Compass actualNode1 = trainstationRails.get(i).getFirstSection().getNode1();
			Assert.assertNotEquals(notExpectedNode1, actualNode1);
			
			Compass notExpectedNode2 = beforeRotationTrainstationRails.get(i).getFirstSection().getNode2();
			Compass actualNode2 = trainstationRails.get(i).getFirstSection().getNode2();
			Assert.assertNotEquals(notExpectedNode2, actualNode2);
		}
		
	}
	
}
