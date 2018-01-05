package models.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import models.session.GameSession;
import models.session.GameSessionManager;

public class LocoTests {

	/**
	 * Besitzt die erzeugte Loco initial ein Cart?
	 */
	/* es müssten die Rails erstellt werden um keine NullPointerExceptions zu bekommen
	@Test
	public void LocoHasInitialCart() {
		int squarePosX = 0;
		int squarePosY = 0;
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		Player player = new Player(gameSession.getSessionName(), "Hans", UUID.randomUUID(), true);

		Map map = gameSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Loco loco = new Loco(gameSession.getSessionName(), square, map, player.getUUID());

		assertEquals(1, loco.getCarts().size());

	}*/
	
	/**
	 * Findet die Loco die Rail, auf der sie steht?
	 */
	@Test
	public void LocoFindsRailItStandsOn() {
		int squarePosX = 0;
		int squarePosY = 0;
		Compass node1 = Compass.NORTH;
		Compass node2 = Compass.SOUTH;
		List<Compass> directions = new ArrayList<>();
		directions.add(node1);
		directions.add(node2);

		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString(),
				UUID.randomUUID(), "Player");
		Player player = new Player(gameSession.getName(), "Hans", UUID.randomUUID(), true);
		Map map = gameSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(gameSession.getName(), square, directions);

		square.setPlaceableOnSquare(rail);
		Loco loco = new Loco(gameSession.getName(), square, map, player.getUUID());

		assertEquals(rail, loco.getRail());

	}

}
