package models.game;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.UUID;

import models.session.GameSession;
import org.junit.Test;

import models.session.GameSessionManager;

public class SquareTests {
	
	@Test
	public void testGetNeighbouringSquaresAtCorner() {
		
		// GameSession und Square erstellen
		GameSession gameSession = GameSessionManager.getInstance().createGameSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		
		Square squareAtCorner = gameSession.getMap().getSquare(0, 0);
		List<Square> squaresAroundCorner = squareAtCorner.getNeighbouringSquares();	
		assertEquals(squaresAroundCorner.size(), 2);
	}
	
	@Test
	public void testGetNeighbouringSquaresAtEdge() {
		
		// GameSession und Square erstellen
		GameSession gameSession = GameSessionManager.getInstance().createGameSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		
		Square squareAtEdge = gameSession.getMap().getSquare(0, 1);
		List<Square> squaresAroundEdge = squareAtEdge.getNeighbouringSquares();	
		assertEquals(squaresAroundEdge.size(), 3);
	}
	
	@Test
	public void testGetNeighbouringSquaresNotOnEdge() {
		
		// GameSession und Square erstellen
		GameSession gameSession = GameSessionManager.getInstance().createGameSession(UUID.randomUUID().toString(), UUID.randomUUID(), "HostPlayer");
		
		Square squareNotOnEdge = gameSession.getMap().getSquare(5, 5);
		List<Square> squaresAroundNotOnEdge = squareNotOnEdge.getNeighbouringSquares();	
		assertEquals(squaresAroundNotOnEdge.size(), 4);
	}

}
