package models.game;

import exceptions.InvalidModelOperationException;
import models.helper.CompassHelper;
import models.session.GameSession;
import models.session.GameSessionManager;
import models.session.RoRSession;

import java.util.UUID;

import communication.MessageInformation;

/**
 * 
 * @author Isabel Rott, Michelle Le
 * Klasse fuer ein Cart (Waggon), das an einer Loco haengt
 * Auf einem Cart koennen Container geladen werden
 */
public class Cart extends TickableGameObject implements PlaceableOnRail {
	
	private Resource resource;
	private Compass drivingDirection;
	private UUID playerId;
	private Rail rail;
	private UUID currentLocoId;
	


	/**
	 * Konstruktor eines Carts
	 * @param square auf dem der Wagon steht wird mitgegeben
	 */
	public Cart(String sessionName, Square square, Compass drivingDirection, UUID playerId, boolean addToLoco, UUID currentLocoId) {
		super(sessionName, square);
		this.setDrivingDirection(drivingDirection);
		this.playerId = playerId;
		Rail rail = (Rail)square.getPlaceableOnSquare();
		this.setRail(rail);
		this.currentLocoId = currentLocoId;
		notifyAddedCart();
	}
	
	/**
	 * notifiziert, wenn ein Wagon erstellt wurde
	 * 
	 * @param square
	 *            Feld auf dem der Wagon steht
	 * @param cartId
	 *            Id des Wagons
	 */
	private void notifyAddedCart() {
		MessageInformation messageInfo = new MessageInformation("CreateCart");
		messageInfo.putValue("playerId", this.playerId);
		messageInfo.putValue("cartId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		if(currentLocoId == null) {
			messageInfo.putValue("currentLocoId", "");
		}
		else {
			messageInfo.putValue("currentLocoId", currentLocoId);
		}
		
		messageInfo.putValue("drivingDirection", drivingDirection);
		notifyChange(messageInfo);
	}
	
	/**
	 * Cart mit einer Ressource beladen
	 * @param resource zu beladene Resource
	 */
	public void loadResourceOntoCart(Resource resource) {
		if (resource != null) {
			this.resource = resource;
			notifyResourceLoadedOntoCart();
		} else {
			throw new InvalidModelOperationException("Cart bereits beladen");
		}
	}
	
	private void notifyResourceLoadedOntoCart() {
		MessageInformation message = new MessageInformation("UpdateResourceLoadedOntoCart");
		message.putValue("resourceType", resource.getDescription());
		message.putValue("resourceId", resource.getId());
		message.putValue("locoId", currentLocoId);
		message.putValue("cartId", getId());
		message.putValue("xPos", this.getXPos());
		message.putValue("yPos", this.getYPos());
		notifyChange(message);
	}
	
	/**
	 * Cart wird geleert
	 */
	public void removeResourceFromCart() {
		resource = null;
		notifyRemoveResourceFromCart();
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public void notifyRemoveResourceFromCart() {
		MessageInformation message = new MessageInformation("RemoveResourceFromCart");
		message.putValue("locoId", currentLocoId);
		message.putValue("cartId", getId());
		notifyChange(message);
	}
	
	/**
	 * Entfernt die Ressource auf dem Cart und gibt diese zurück
	 * @return Ressource auf auf dem Cart
	 */
	public Resource unloadResourceFromCart() {
		Resource unloadedResource = resource;
		resource = null;
		notifyUnloadCart();
		return unloadedResource;
	}
	
	private void notifyUnloadCart() {
		MessageInformation message = new MessageInformation("UpdateUnloadCart");
		message.putValue("locoId", currentLocoId);
		message.putValue("cartId", getId());
		message.putValue("xPos", this.getXPos());
		message.putValue("yPos", this.getYPos());
		notifyChange(message);
	}

	@Override
	public void specificUpdate() {
		// TODO Auto-generated method stub
	}
	
	public PlaceableOnRail loadFromMap(Square square, RoRSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	public Compass getDrivingDirection() {
		return drivingDirection;
	}

	public void setDrivingDirection(Compass compass) {
		this.drivingDirection = compass;
	}
	
	public void setCurrentLocoId(UUID currentLocoId) {
		this.currentLocoId = currentLocoId;
	}
	
	/**
	 * notifiziert wenn die Position des Wagons ge�ndert wurde
	 */
	public void notifyUpdatedCart() {
		MessageInformation messageInfo = new MessageInformation("UpdateCartPosition");
		messageInfo.putValue("cartId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("playerId", this.playerId);
		messageInfo.putValue("currentLocoId", currentLocoId);
		messageInfo.putValue("drivingDirection", drivingDirection.toString());
		notifyChange(messageInfo);
	}

	public Rail getRail() {
		return rail;
	}

	public void setRail(Rail rail) {
		this.rail = rail;
	}
	
	public UUID getCurrentLocoId() {
		return currentLocoId;
	}

	
	public UUID getPlayerId() {
		return playerId;
	}
	
	public Resource getResourceNextToCart(boolean right) {
		PlaceableOnSquare placeableOnSquare = getPlaceableOnSquareNextToCart(right);
		if(placeableOnSquare != null && placeableOnSquare instanceof Resource) {
			return (Resource)placeableOnSquare;
		}
		return null;
	}
	
	private Stock getStockNextToCart() {
		PlaceableOnSquare placeableOnSquare = getPlaceableOnSquareNextToCart(true);
		
		// Wenn auf der rechten Seite nicht gefunden, dann prüfe auf der linken
		if((placeableOnSquare == null) || (placeableOnSquare != null && !(placeableOnSquare instanceof Stock))) {
			placeableOnSquare = getPlaceableOnSquareNextToCart(false);
		}
		
		if (placeableOnSquare != null && placeableOnSquare instanceof Stock) {
			return (Stock)placeableOnSquare;
		}
		return null;
	}
	
	public boolean isNextToStock() {
		return getStockNextToCart() != null;
	}
	
	public UUID getPlayerIdFromStockNextToCart() {
		Stock stock = getStockNextToCart();
		if (stock != null) {
			GameSession gameSession = GameSessionManager.getInstance().getGameSessionByName(sessionName);
			Map map = gameSession.getMap();
			Trainstation trainstation = (Trainstation)map.getPlaceableOnSquareById(stock.getTrainstationId());
		    return trainstation.getPlayerId();
		}
		return null;
	}
	
	private PlaceableOnSquare getPlaceableOnSquareNextToCart(boolean right) {
		int sideways = right ? 1 : -1;
		int squarePosX = CompassHelper.getRealXForDirection(getDrivingDirection(), getXPos(),
				getYPos(), sideways, 0);
		int squarePosY = CompassHelper.getRealYForDirection(getDrivingDirection(), getXPos(),
				getYPos(), sideways, 0);

		GameSession gameSession = GameSessionManager.getInstance().getGameSessionByName(sessionName);
		Map map = gameSession.getMap();
		
		if (squarePosX <= map.getMapSize() && squarePosY <= map.getMapSize()) {
			Square square = gameSession.getMap().getSquare(squarePosX, squarePosY);
			
			if(square != null) {
				if(square.getPlaceableOnSquare() != null) {
					return square.getPlaceableOnSquare();
				}
			}
		}
		
		return null;
	}
}
