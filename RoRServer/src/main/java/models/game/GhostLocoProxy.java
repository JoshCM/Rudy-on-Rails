package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.session.GameSession;
import models.session.GameSessionManager;

public class GhostLocoProxy {
	private Loco loco;
	private GameSession gameSession;
	private Map map;
	
	private int visibleSquareAmountSideways;
	private int visibleSquareAmountForward;
	
	public GhostLocoProxy(Loco loco) {
		this.loco = loco;
		this.gameSession = GameSessionManager.getInstance().getGameSession();
		this.map = gameSession.getMap();
	};
	
	public void changeRandomSpeed() {
		Random rand = new Random();
		loco.changeSpeed(rand.nextInt(5));
		System.out.println("neuer Speed: " + loco.getSpeed());
	}
	
	public void changeSpeed(int speed) {
		if(speed >= 0 && speed <= 5) {
			loco.changeSpeed(speed);
		}
	}
	
	public List<List<String>> getObjectsOnVisibleSquares() {
		List<List<String>> result = new ArrayList<List<String>>();
		
		for(int y = 1; y < visibleSquareAmountForward; y++) {
			for(int x = loco.getXPos() - visibleSquareAmountSideways; x < visibleSquareAmountSideways; x++) {
				result.add(getObjectsOnSquare(x, y));
			}
		}
		
		return result;
	}
	
	public List<String> getObjectsOnSquare(int x, int y) {
		List<String> result = new ArrayList<String>();
		
		if(isSquareVisibleForProxy(x, y)) {
			int squarePosX = loco.getXPos() + x;
			int squarePosY = loco.getYPos() + y;
			
			if(squarePosX <= map.getMapSize() && squarePosY <= map.getMapSize()) {
				Square square = map.getSquare(squarePosX, squarePosY);
				PlaceableOnSquare placeableOnSquare = square.getPlaceableOnSquare();
				
				if(placeableOnSquare != null) {
					if(placeableOnSquare instanceof Trainstation) {
						result.add("Trainstation");
					} else if(placeableOnSquare instanceof Rail) {
						result.add("Rail");
						
						Rail rail = (Rail)placeableOnSquare;
						if(rail.getSignals() != null) {
							boolean activeSignal = rail.getSignals().isSignalActive(loco.getDirectionNegation());
							if(activeSignal) {
								result.add("ActiveSignal");
							} else {
								result.add("InactiveSignal");
							}
						}
						
						if(rail.getPlaceableOnrail() != null && rail.getPlaceableOnrail() instanceof Mine) {
							result.add("Mine");
						}
						
						for(Loco loco : gameSession.getLocomotives()) {
							if(loco.getRail().getId().equals(rail.getId())) {
								result.add("Loco");
							}
						}
					} else if(placeableOnSquare instanceof Resource) {
						Resource resource = (Resource)placeableOnSquare;
						result.add(resource.getName());
					}
				}
			} else {
				result.add("NotOnMap");
			}
		} else {
			result.add("NotVisible");
		}
		
		return result;
	}
	
	private boolean isSquareVisibleForProxy(int x, int y) {
		if(x >= -visibleSquareAmountSideways && x <= visibleSquareAmountSideways) {
			if(y <= visibleSquareAmountForward) {
				return true;
			}
		}
		return false;
	}
}
