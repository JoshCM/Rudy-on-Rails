package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.Test;
import com.google.gson.JsonObject;
import commands.base.Command;
import commands.editor.CreateRailCommand;
import commands.editor.CreatePlayertrainstationCommand;
import commands.editor.StartEditorCommand;
import commands.game.CreateCartCommand;
import communication.MessageInformation;
import exceptions.MapNotFoundException;
import models.game.Cart;
import models.game.Compass;
import models.game.GamePlayer;
import models.game.Map;
import models.game.PlayerLoco;
import models.game.Rail;
import models.game.Square;
import models.game.Trainstation;
import models.game.Playertrainstation;
import models.session.EditorSession;
import models.session.EditorSessionManager;
import models.session.GameSession;
import models.session.GameSessionManager;
import models.session.RoRSession;
import persistent.MapManager;

public class CommandCreatorTests {
	@Test
	public void CreateRailCommandIsCreatedViaName() {
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", 0);
		messageInformation.putValue("yPos", 0);
		messageInformation.putValue("railSectionList", new ArrayList<JsonObject>());

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
		int trainstationX = 0;
		int trainstationY = 4;
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("xPos", trainstationX);
		messageInformation.putValue("yPos", trainstationY);
		messageInformation.putValue("alignment", Compass.EAST.toString());

		RoRSession session = EditorSessionManager.getInstance().createNewEditorSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		CreatePlayertrainstationCommand command = new CreatePlayertrainstationCommand(session, messageInformation);

		String commandName = command.getClass().getName();
		Command createdCommand = null;
		try {
			createdCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {

		}
		createdCommand.execute();
		
		assertNotNull(createdCommand);
		assertEquals(commandName, createdCommand.getClass().getName());
		assertEquals(command.getClass(), createdCommand.getClass());

		Class<?> actualClass = session.getMap().getSquare(trainstationX, trainstationY).getPlaceableOnSquare().getClass();
		
		assertEquals(Playertrainstation.class, actualClass);
	}
	
	@Test
	public void testEmptyMapIsCreated() {
		String editorName = UUID.randomUUID().toString();
		EditorSession editorSession = EditorSessionManager.getInstance().createNewEditorSession(editorName, UUID.randomUUID(), "Player");
		editorSession.setMapName("#test");
		
		MessageInformation messageInformation = new MessageInformation("StartEditor");

		StartEditorCommand startEditorCommand = new StartEditorCommand(editorSession, messageInformation);
		String commandName = startEditorCommand.getClass().getName();
		Command startedCommand = null;
		try {
			startedCommand = CommandCreator.createCommandForName(commandName, editorSession, messageInformation);
		} catch (Exception e) {

		}
		
		assertNotNull(startedCommand);
		for(Square[] squares : editorSession.getMap().getSquares()) {
			for(Square square : squares) {
				assertNull(square.getPlaceableOnSquare());
				assertEquals(editorName, square.getSessionName());
			}
		}
	}
	
	@Test
	public void testCartIsCreatedAndDecreasePlayerGold() throws MapNotFoundException {
		
		GameSession session = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		
		Map map = MapManager.loadMap("GameDefaultMapWithTrainstations");
		session.setMap(map);
		
		GamePlayer player = (GamePlayer) session.getPlayers().get(0);
		int beforePlayerGoldCount = player.getGoldCount();
		session.addLoco(new PlayerLoco(session.getSessionName(), map.getSquare(5, 7), player.getId(), Compass.NORTH));
		
		int xPosCartSpawnPoint = 5;
		int yPosCartSpawnPoint = 5;
		Compass compass = Compass.NORTH;
		UUID playerId = player.getId();
		
		MessageInformation messageInformation = new MessageInformation();
		messageInformation.putValue("posX", xPosCartSpawnPoint);
		messageInformation.putValue("posY", yPosCartSpawnPoint);
		messageInformation.putValue("compass", compass.toString());
		messageInformation.setClientid(playerId.toString());

		CreateCartCommand command = new CreateCartCommand(session, messageInformation);
		String commandName = command.getClass().getName();
		Command createdCommand = null;
		try {
			createdCommand = CommandCreator.createCommandForName(commandName, session, messageInformation);
		} catch (Exception e) {

		}
		createdCommand.execute();
		
		assertNotNull(createdCommand);
		assertEquals(commandName, createdCommand.getClass().getName());
		assertEquals(command.getClass(), createdCommand.getClass());
		assertEquals(beforePlayerGoldCount - 3, player.getGoldCount());
		
		Rail cartSpawnPointRail = (Rail)map.getSquare(xPosCartSpawnPoint, yPosCartSpawnPoint).getPlaceableOnSquare();
		assertEquals(Cart.class, cartSpawnPointRail.getPlaceableOnrail().getClass());
	}
}
