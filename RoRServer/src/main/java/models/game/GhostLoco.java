package models.game;

import java.util.Random;
import java.util.UUID;
import communication.MessageInformation;
import models.scripts.ProxyObject;
import models.scripts.ScriptableObject;
import models.session.GameSession;
import models.session.GameSessionManager;

/**
 * Dem Geisterzug kann ein Script hinterlegt werden, dass ihn steuert
 */
public class GhostLoco extends Loco {
	private final static int STEALING_AMOUNT = 10;
	private final static int CHANCE_TO_STEAL_POINT_CONTAINER_IN_PERCENT = 33;
	
	ScriptableObject scriptableObject;
	private boolean picksUpGoldContainerNextToRails = false;
	private boolean picksUpCoalContainerNextToRails = false;
	private boolean stealsGoldContainerFromOtherPlayers = false;
	private boolean stealsCoalContainerFromOtherPlayers = false;

	public GhostLoco(String sessionName, Square square, UUID playerId, Compass drivingDirection) {
		super(sessionName, square, playerId, drivingDirection);

		ProxyObject ghostLocoProxy = new GhostLocoProxy(this);
		scriptableObject = new ScriptableObject(ghostLocoProxy);
		GameSessionManager.getInstance().getGameSessionByName(sessionName).addScriptableObject(scriptableObject);

		NotifyLocoCreated();
		addCart();
		addCart();
	}

	private void NotifyLocoCreated() {
		MessageInformation messageInfo = new MessageInformation("CreateGhostLoco");
		messageInfo.putValue("locoId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("drivingDirection", getDrivingDirection().toString());
		messageInfo.putValue("playerId", getPlayerId());
		notifyChange(messageInfo);
	}

	public void changeCurrentScriptFilename(String currentScriptName) {
		scriptableObject.changeCurrentScriptFilename(currentScriptName);
	}

	@Override
	public void specificUpdate() {
		// Das eigentliche Fahren ist in der Oberklasse Loco geregelt
		super.specificUpdate();

		handleCartPickUps();
	}

	private void handleCartPickUps() {
		for (Cart cart : getCarts()) {
			if (cart.getResource() == null) {
				if (picksUpGoldContainerNextToRails || picksUpCoalContainerNextToRails) {
					handleContainerPickUpsNextToRail(cart);
				}
				if(stealsGoldContainerFromOtherPlayers || stealsCoalContainerFromOtherPlayers) {
					handleStealingContainers(cart);
				}
			}	
		}
	}
	
	private void handleStealingContainers(Cart cart) {
		if(cart.isNextToStock()) {
			UUID stockPlayerId = cart.getPlayerIdFromStockNextToCart();
			
			// Falls es sich nicht um den eigenen Bahnhof handelt
			if(!getPlayerId().equals(stockPlayerId)) {
				GameSession gameSession = GameSessionManager.getInstance().getGameSessionByName(sessionName);
				GamePlayer enemyPlayer = (GamePlayer)gameSession.getPlayerById(stockPlayerId);
				
				if(enemyPlayer != null) { // Der Bahnhof könnte auch keinem Spieler gehören, falls es mehr Bahnhöfe als Spieler gibt				
					Random rand = new Random();
					boolean stealPointContainerIfPossible = false;
					if(rand.nextInt(100) + 1 <= CHANCE_TO_STEAL_POINT_CONTAINER_IN_PERCENT) {
						stealPointContainerIfPossible = true;
					}
					
					if(stealPointContainerIfPossible && enemyPlayer.getPointCount() > 0) {
						enemyPlayer.removePoints(STEALING_AMOUNT);
						cart.loadResourceOntoCart(new PointContainer(getSessionName()));
					} else if(stealsGoldContainerFromOtherPlayers && enemyPlayer.getGoldCount() > 0) {
						enemyPlayer.removeGold(STEALING_AMOUNT);
						cart.loadResourceOntoCart(new Gold(getSessionName()));
					} else if(stealsCoalContainerFromOtherPlayers && enemyPlayer.getCoalCount() > 0) {
						enemyPlayer.removeGold(STEALING_AMOUNT);
						cart.loadResourceOntoCart(new Coal(getSessionName()));
					}
				}
			}
		}
	}
	
	private void handleContainerPickUpsNextToRail(Cart cart) {
		Resource resourceRight = cart.getResourceNextToCart(true);
		Resource resourceLeft = cart.getResourceNextToCart(false);

		if (picksUpGoldContainerNextToRails) {
			if (resourceRight != null) {
				if (resourceRight instanceof Gold) {
					loadResourceOnCartAndRemoveItFromSquare(cart, resourceRight);
					return;
				}
			} else if (resourceLeft != null) {
				if (resourceLeft instanceof Gold) {
					loadResourceOnCartAndRemoveItFromSquare(cart, resourceLeft);
					return;
				}
			}
		}
		if (picksUpCoalContainerNextToRails) {
			if (resourceRight != null) {
				if (resourceRight instanceof Coal) {
					loadResourceOnCartAndRemoveItFromSquare(cart, resourceRight);
				}
			} else if (resourceLeft != null) {
				if (resourceLeft instanceof Coal) {
					loadResourceOnCartAndRemoveItFromSquare(cart, resourceLeft);
				}
			}
		}
	}
	
	private void loadResourceOnCartAndRemoveItFromSquare(Cart cart, Resource resource) {
		cart.loadResourceOntoCart(resource);
		Square square = map.getSquareById(resource.getSquareId());
		square.deletePlaceable();
	}

	public boolean isPicksUpGoldContainerNextToRails() {
		return picksUpGoldContainerNextToRails;
	}

	public void setPicksUpGoldContainerNextToRails(boolean picksUpGoldContainerNextToRails) {
		this.picksUpGoldContainerNextToRails = picksUpGoldContainerNextToRails;
	}

	public boolean isPicksUpCoalContainerNextToRails() {
		return picksUpCoalContainerNextToRails;
	}

	public void setPicksUpCoalContainerNextToRails(boolean picksUpCoalContainerNextToRails) {
		this.picksUpCoalContainerNextToRails = picksUpCoalContainerNextToRails;
	}

	public boolean isStealsGoldContainerFromOtherPlayers() {
		return stealsGoldContainerFromOtherPlayers;
	}

	public void setStealsGoldContainerFromOtherPlayers(boolean stealsGoldContainerFromOtherPlayers) {
		this.stealsGoldContainerFromOtherPlayers = stealsGoldContainerFromOtherPlayers;
	}

	public boolean isStealsCoalContainerFromOtherPlayers() {
		return stealsCoalContainerFromOtherPlayers;
	}

	public void setStealsCoalContainerFromOtherPlayers(boolean stealsCoalContainerFromOtherPlayers) {
		this.stealsCoalContainerFromOtherPlayers = stealsCoalContainerFromOtherPlayers;
	}

	@Override
	public void spendCoal() {
		// Der Geisterzug verbraucht keine Resourcen zum Fahren
	}
	
	public boolean needsCoalToDrive() {
		return false;
	}
}