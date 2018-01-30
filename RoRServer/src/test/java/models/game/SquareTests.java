package models.game;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import models.session.GameSession;
import models.session.GameSessionManager;

public class SquareTests {
	
	@Test
	public void testGetNeighbouringSquaresAtCorner() {
		
		// GameSession und Square erstellen
		String gameSessionName = UUID.randomUUID().toString();
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(gameSessionName, UUID.randomUUID(), "HostPlayer");
		Map map = new Map(gameSessionName,50);
		map.setSessionNameForMapAndSquares(gameSessionName);
		gameSession.setMap(map);
				
		Square squareAtCorner = map.getSquare(0, 0);
		List<Square> squaresAroundCorner = squareAtCorner.getNeighbouringSquares();	
		assertEquals(squaresAroundCorner.size(), 3);
	}
	
	@Test
	public void testGetNeighbouringSquaresAtEdge() {
		
		
		// GameSession und Square erstellen
		String gameSessionName = UUID.randomUUID().toString();
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(gameSessionName, UUID.randomUUID(), "HostPlayer");
		Map map = new Map(gameSessionName,50);
		map.setSessionNameForMapAndSquares(gameSessionName);
		gameSession.setMap(map);
		
		Square squareAtEdge = map.getSquare(0, 1);
		List<Square> squaresAroundEdge = squareAtEdge.getNeighbouringSquares();	
		assertEquals(squaresAroundEdge.size(), 5);
	}
	
	@Test
	public void testGetNeighbouringSquaresNotOnEdge() {
		
		// GameSession und Square erstellen
		String gameSessionName = UUID.randomUUID().toString();
		
		GameSession gameSession = GameSessionManager.getInstance().createNewGameSession(gameSessionName, UUID.randomUUID(), "HostPlayer");
		Map map = new Map(gameSessionName,50);
		map.setSessionNameForMapAndSquares(gameSessionName);
		gameSession.setMap(map);
		
		Square squareNotOnEdge = map.getSquare(5, 5);
		List<Square> squaresAroundNotOnEdge = squareNotOnEdge.getNeighbouringSquares();	
		assertEquals(squaresAroundNotOnEdge.size(), 8);
	}

}
