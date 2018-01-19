package models.game;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import commands.CommandCreator;
import commands.base.Command;
import commands.editor.CreateTrainstationCommand;
import commands.editor.MoveTrainstationCommand;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import exceptions.NotMoveableException;
import models.session.EditorSessionManager;
import models.session.RoRSession;

public class TrainstationTests {

	RoRSession session;
	
	public void initValidTrainstationCommand(int x, int y) {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", x);
		messageInformation.putValue("yPos", y);
		messageInformation.putValue("alignment", Compass.EAST.toString());

		session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
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
		int x = 0;
		int y = 4;
		initValidTrainstationCommand(x, y);
		Square square = session.getMap().getSquare(x, y);
		Assert.assertNotNull(square.getPlaceableOnSquare());
		Assert.assertEquals(Trainstation.class, square.getPlaceableOnSquare().getClass());
		List<UUID> railIds = ((Trainstation) square.getPlaceableOnSquare()).getTrainstationRailIds();
		Assert.assertEquals(14, railIds.size());
	}

	@Test(expected = InvalidModelOperationException.class)
	public void TrainstationIsOnTopEdge() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 0);
		messageInformation.putValue("alignment", Compass.EAST.toString());

		session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
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
	public void TrainstationRailBlockedByExistingRail() {
		int trainstationX = 0;
		int trainstationY = 4;
		
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", trainstationX);
		messageInformation.putValue("yPos", trainstationY);
		messageInformation.putValue("alignment", Compass.EAST.toString());

		// setzen der rail die die exception verursacht
		session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		Square square = session.getMap().getSquare(trainstationX + 1, trainstationY);
		square.setPlaceableOnSquare(new Rail(session.getDescription(),square,Arrays.asList(Compass.NORTH, Compass.SOUTH)));

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
		int trainstationX = 4;
		int trainstationY = 4;
		
		initValidTrainstationCommand(trainstationX, trainstationY);
		Trainstation createdTrainstation = (Trainstation) session.getMap().getSquare(trainstationX, trainstationY).getPlaceableOnSquare();
		
		Assert.assertTrue(createdTrainstation.validateRotation(true));
	}

	@Test
	public void TrainstationRotationCounterClockwiseShouldBeValid() {
		int trainstationX = 4;
		int trainstationY = 4;
		
		initValidTrainstationCommand(trainstationX, trainstationY);
		Trainstation createdTrainstation = (Trainstation) session.getMap().getSquare(trainstationX, trainstationY).getPlaceableOnSquare();
		
		Assert.assertTrue(createdTrainstation.validateRotation(false));
	}

	@Test
	public void TrainstationRotationClockwiseShouldBeDone() {
		int trainstationX = 4;
		int trainstationY = 4;
		
		initValidTrainstationCommand(trainstationX, trainstationY);
		Trainstation createdTrainstation = (Trainstation) session.getMap().getSquare(trainstationX, trainstationY).getPlaceableOnSquare();
		
		// rotiere 4 mal damit die Ausgangsposition wieder besteht
		for(int i = 0; i < 4; i++) {
			createdTrainstation.rotate(true);
		}
		
		// testen des Stocks ob an richtiger stelle
		Square stockSquare = session.getMap().getSquare(trainstationX + 1, trainstationY);
		Assert.assertNotNull(stockSquare);
		
		List<Integer> Xs = Arrays.asList(5,5,5,5,5,5,6,6,6,6,6,6,6,6); 
		List<Integer> Ys = Arrays.asList(1,2,3,4,5,6,0,1,2,3,4,5,6,7); 
		
		int expectedRailCount = 14;
		int railCount = 0;
		// testen der einzelnen squares ob die richtigen placeables draufstehen
		for (int i = 0; i < createdTrainstation.getTrainstationRails().size(); i++) {
			Rail trainstationRail = createdTrainstation.getTrainstationRails().get(i);
			for(int j = 0; j < Xs.size(); j++) {
				if(Xs.get(j) == trainstationRail.getXPos() && Ys.get(j) == trainstationRail.getYPos()) {
					railCount++;
				}
			}
			
		}
		assertEquals(expectedRailCount, railCount);

	}

	@Test
	public void TrainstationRotationCounterClockwiseShouldBeDone() {
		int trainstationX = 4;
		int trainstationY = 4;
		
		initValidTrainstationCommand(trainstationX, trainstationY);
		Trainstation createdTrainstation = (Trainstation) session.getMap().getSquare(trainstationX, trainstationY).getPlaceableOnSquare();
		
		// rotiere 4 mal damit die Ausgangsposition wieder besteht
		for(int i = 0; i < 4; i++) {
			createdTrainstation.rotate(false);
		}
		
		// testen des Stocks ob an richtiger stelle
		Square stockSquare = session.getMap().getSquare(trainstationX + 1, trainstationY);
		Assert.assertNotNull(stockSquare);
		
		List<Integer> Xs = Arrays.asList(5,5,5,5,5,5,6,6,6,6,6,6,6,6); 
		List<Integer> Ys = Arrays.asList(1,2,3,4,5,6,0,1,2,3,4,5,6,7); 
		
		int expectedRailCount = 14;
		int railCount = 0;
		// testen der einzelnen squares ob die richtigen placeables draufstehen
		for (int i = 0; i < createdTrainstation.getTrainstationRails().size(); i++) {
			Rail trainstationRail = createdTrainstation.getTrainstationRails().get(i);
			for(int j = 0; j < Xs.size(); j++) {
				if(Xs.get(j) == trainstationRail.getXPos() && Ys.get(j) == trainstationRail.getYPos()) {
					railCount++;
				}
			}
			
		}
		assertEquals(expectedRailCount, railCount);
	}	
	
	@Test
	public void TrainstationMoveShouldBeDone() {
		int trainstationX = 0;
		int trainstationY = 4;
		int movedTrainstationX = 4;
		int movedTrainstationY = 4;
		
		initValidTrainstationCommand(trainstationX, trainstationY);
		Trainstation createdTrainstation = (Trainstation) session.getMap().getSquare(trainstationX, trainstationY).getPlaceableOnSquare();
		
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("newXPos", movedTrainstationX);
		messageInformation.putValue("newYPos", movedTrainstationY);
		messageInformation.putValue("id", createdTrainstation.getId());
		
		MoveTrainstationCommand command = new MoveTrainstationCommand(session, messageInformation);

		String commandName = command.getClass().getName();
		Command moveCommand = null;
		try {
			moveCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {

		}
		moveCommand.execute();
		
		Assert.assertEquals(session.getMap().getSquare(movedTrainstationX, movedTrainstationY).getId(), createdTrainstation.getSquareId());
		
		List<Integer> Xs = Arrays.asList(5,5,5,5,5,5,6,6,6,6,6,6,6,6); 
		List<Integer> Ys = Arrays.asList(1,2,3,4,5,6,0,1,2,3,4,5,6,7); 
		
		int expectedRailCount = 14;
		int railCount = 0;
		// testen der einzelnen squares ob die richtigen placeables draufstehen
		for (int i = 0; i < createdTrainstation.getTrainstationRails().size(); i++) {
			Rail trainstationRail = createdTrainstation.getTrainstationRails().get(i);
			for(int j = 0; j < Xs.size(); j++) {
				if(Xs.get(j) == trainstationRail.getXPos() && Ys.get(j) == trainstationRail.getYPos()) {
					railCount++;
				}
			}
		}
		assertEquals(expectedRailCount, railCount);
	}
	
	@Test(expected = NotMoveableException.class)
	public void TrainstationMoveShouldNotBeDone() {
		int trainstationX = 0;
		int trainstationY = 4;
		int movedTrainstationX = 4;
		// darf nicht verschoben werden, da die Rails nach oben keinen Platz haben
		int movedTrainstationY = 3;
		
		initValidTrainstationCommand(trainstationX, trainstationY);
		Trainstation createdTrainstation = (Trainstation) session.getMap().getSquare(trainstationX, trainstationY).getPlaceableOnSquare();
		
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("newXPos", movedTrainstationX);
		messageInformation.putValue("newYPos", movedTrainstationY);
		messageInformation.putValue("id", createdTrainstation.getId());
		
		MoveTrainstationCommand command = new MoveTrainstationCommand(session, messageInformation);

		String commandName = command.getClass().getName();
		Command moveCommand = null;
		try {
			moveCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {

		}
		moveCommand.execute();
	}
}
