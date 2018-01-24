package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import communication.MessageInformation;
import models.session.GameSession;
import models.session.GameSessionManager;
import models.session.RoRSession;

public class Publictrainstation extends Trainstation {
	
	private GameSession gameSession;
	
	public Publictrainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id,
			Compass alignment, Stock stock) {
		super(sessionName, square, trainstationRailIds, id, alignment, stock);
		notifyCreatedPublictrainstation();
	}
	
	private void notifyCreatedPublictrainstation() {
		MessageInformation messageInfo = new MessageInformation("CreatePublictrainstation");
		messageInfo.putValue("trainstationId", getId());
		messageInfo.putValue("alignment", alignment);
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		List<JsonObject> rails = new ArrayList<JsonObject>();
		for (UUID railId : getTrainstationRailIds()) {
			JsonObject json = new JsonObject();
			json.addProperty("railId", railId.toString());
			rails.add(json);
		}
		messageInfo.putValue("trainstationRails", rails);
		messageInfo.putValue("stockId", getStock().getId());

		notifyChange(messageInfo);
	}
	
	@Override
	public Publictrainstation loadFromMap(Square square, RoRSession session) {
		Publictrainstation oldTrainStation = (Publictrainstation) square.getPlaceableOnSquare();
		Publictrainstation newTrainStation = new Publictrainstation(session.getSessionName(), square,
				oldTrainStation.getTrainstationRailIds(), oldTrainStation.getId(), oldTrainStation.alignment, oldTrainStation.getStock());
		
		// der sessionName muss neu gesetzt werden, damit der Observer Änderungen dieses Objekts mitbekommen kann
		newTrainStation.setSessionName(session.getSessionName());

		log.info("PublicTrainStation erstellt: " + newTrainStation.toString());
		return newTrainStation;
	}
	
	@Override
	public String toString() {
		return "PublicTrainstation [trainstationRailIds=" + trainstationRailIds + ", alignment=" + alignment + "]";
	}

	private void exchangeResource() {
		// ToDo: Beim Cart der Loco muss analysiert werden,
		// welche Resource er haelt.
		// Daraufhin muss dann nach der zum Verhaeltnis passenden Resource
		// in der Trainstation gesucht werden,
		// diese wird aus dem Stock entfernt, der Loco hinzugefuegt und
		// die Resource der Loco wird dem Stock hinzugefuegt
		gameSession = GameSessionManager.getInstance().getGameSessionByName(sessionName);
		List<Loco> locos = gameSession.getLocos();
		for (Loco loco : locos) {
			List<Cart> carts = loco.getCarts();
			for (Cart cart : carts) {
				if (cart.getXPos() == this.getXPos() && cart.getYPos() == this.getYPos()
						&& cart.getResource() != null && stock.getResources().size() != 0) {
					// Die Resource der Cart
					String resourceType = cart.getResource().getDescription();
					
					// Die Resources im Stock der Trainstation
					List<Resource> resources = stock.getResources();
					for (Resource resource : resources) {
						if (resourceType == "Gold" && resource.getDescription() == "Coal") {
							// 1 Gold : 1 Kohle
							Resource r = cart.getResource();
							cart.removeResourceFromCart();
							resources.remove(resource);
							resources.add(r);
							cart.loadResourceOntoCart(resource);
							
							notifyResourceExchanged(resourceType);
						}
						if (resourceType == "Coal" && resource.getDescription() == "Gold") {
							// 3 Kohle : 1 Gold
							if (carts.size() == 3 /* && 3x Coal */) {
								for (Cart c : carts) {
									Resource r = c.getResource();
									c.removeResourceFromCart();
									resources.add(r);
								}
								resources.remove(resource);
								cart.loadResourceOntoCart(resource);
								
								notifyResourceExchanged(resourceType);
							}
						}
					}
				}
			}
		}
	}
	
	private void notifyResourceExchanged(String resourceType) {
		MessageInformation messageInfo = new MessageInformation("ExchangeResourceInPublictrainstation");
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		messageInfo.putValue("resourceType", resourceType);
		notifyChange(messageInfo);
	}
}
