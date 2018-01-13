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
	
	private int VISIBLE_SQUARE_AMOUNT_SIDEWAYS = 2;
	private int VISIBLE_SQUARE_AMOUNT_FORWARD = 5;
	
	public GhostLocoProxy(Loco loco) {
		this.loco = loco;
		this.gameSession = GameSessionManager.getInstance().getGameSession();
		this.map = gameSession.getMap();
	};
	
	public void changeRandomSpeed() {
		Random rand = new Random();
		loco.changeSpeed(rand.nextInt(5));
	}
	
	public void changeSpeed(int speed) {
		if(speed >= 0 && speed <= 5) {
			System.out.println(speed);
			loco.changeSpeed(speed);
		}
	}
	
	public List<List<String>> getObjectsOnVisibleSquares() {
		List<List<String>> result = new ArrayList<List<String>>();
		
		for(int y = 1; y <= VISIBLE_SQUARE_AMOUNT_FORWARD; y++) {
			for(int x = -VISIBLE_SQUARE_AMOUNT_SIDEWAYS; x <= VISIBLE_SQUARE_AMOUNT_SIDEWAYS; x++) {
				result.add(getObjectsOnSquare(x, y));
			}
		}
		
		return result;
	}
	
	public List<String> getObjectsOnSquare(int sideways, int forward) {
		List<String> result = new ArrayList<String>();
		
		if(isSquareVisibleForProxy(sideways, forward)) {
			int squarePosX = getRealXForDrivingDirection(sideways, forward);
			int squarePosY = getRealYForDrivingDirection(sideways, forward);
			
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
							boolean activeSignal = rail.getSignals().isSignalActive(loco.getDirectionNegation(loco.getDrivingDirection()));
							if(activeSignal) {
								result.add("ActiveSignal");
							} else {
								result.add("InactiveSignal");
							}
						}
						
						if(rail.getPlaceableOnrail() != null && rail.getPlaceableOnrail() instanceof Mine) {
							result.add("Mine");
						}
						
						for(Loco loco : gameSession.getPlayerLocos()) {
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
	
	private int getRealXForDrivingDirection(int sideways, int forward) {
		switch(loco.getDrivingDirection()) {
		case NORTH:
			return loco.getXPos() + sideways;
		case EAST:
			return loco.getXPos() + forward;
		case SOUTH:
			return loco.getXPos() - sideways;
		case WEST:
			return loco.getXPos() - forward;
		default:
			return -1;
		}
	}
	
	private int getRealYForDrivingDirection(int sideways, int forward) {
		switch(loco.getDrivingDirection()) {
		case NORTH:
			return loco.getYPos() - forward;
		case EAST:
			return loco.getYPos() + sideways;
		case SOUTH:
			return loco.getYPos() + forward;
		case WEST:
			return loco.getYPos() - sideways;
		default:
			return -1;
		}
	}
	
	private boolean isSquareVisibleForProxy(int x, int y) {
		if(x >= -VISIBLE_SQUARE_AMOUNT_SIDEWAYS && x <= VISIBLE_SQUARE_AMOUNT_SIDEWAYS) {
			if(y <= VISIBLE_SQUARE_AMOUNT_FORWARD) {
				return true;
			}
		}
		return false;
	}
	
	public long getSpeed() {
		return loco.getSpeed();
	}
}
