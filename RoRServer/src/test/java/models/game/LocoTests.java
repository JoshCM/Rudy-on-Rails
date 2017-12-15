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
	@Test
	public void LocoHasInitialCart() {
		int squarePosX = 0;
		int squarePosY = 0;
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString());
		Player player = new Player(gameSession.getName(),"Hans");
		
		
		Map map = gameSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Loco loco = new Loco(gameSession.getName(), square,player);
		
		assertEquals(1, loco.getCarts().size());

	}
	
	/**
	 * Findet die Loco die Rail, auf der sie steht?
	 */
	@Test
	public void LocoFindsRailItStandsOn() {
		int squarePosX = 0;
		int squarePosY = 0;
		RailSectionPosition node1 = RailSectionPosition.NORTH;
		RailSectionPosition node2 = RailSectionPosition.SOUTH;
		List<RailSectionPosition> railSectionPositions = new ArrayList<>();
		railSectionPositions.add(node1);
		railSectionPositions.add(node2);
		
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(UUID.randomUUID().toString());
		Player player = new Player(gameSession.getName(),"Hans");
		Map map = gameSession.getMap();
		Square square = map.getSquare(squarePosX, squarePosY);
		Rail rail = new Rail(gameSession.getName(), square, railSectionPositions);

		square.setPlaceable(rail);
		Loco loco = new Loco(gameSession.getName(), square,player);
		
		assertEquals(rail,loco.getRail());
		
	}

}
