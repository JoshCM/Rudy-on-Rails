package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import communication.MessageInformation;
import models.session.RoRSession;

public class Publictrainstation extends Trainstation {

	private List<Resource> resources;
	
	public Publictrainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id,
			Compass alignment, Stock stock) {
		super(sessionName, square, trainstationRailIds, id, alignment, stock);
		//initializeResourceStock();
		notifyCreatedPublictrainstation();
	}
	
	public List<Resource> getResources() {
		return resources;
	}
	
	private void initializeResourceStock() {
		resources = new ArrayList<Resource>();
		for (int i = 0; i < 20; i++) {
			Gold g = new Gold(getSessionName());
			Coal c = new Coal(getSessionName());
			PointContainer p = new PointContainer(getSessionName());
			resources.add(g);
			resources.add(c);
			resources.add(p);
		}
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
	
	private void locoAtPublictrainstation() {
		for (Rail rail : this.getTrainstationRails()) {
			Square trainstationSquare = rail.getSquareFromGameSession();
			if (trainstationSquare != null) {
				if (trainstationSquare.getPlaceableOnSquare() instanceof PlayerLoco) {
					Loco loco = (Loco) trainstationSquare.getPlaceableOnSquare();
					exchangeResource(loco);
				}
			}
		}
	}

	private void exchangeResource(Loco loco) {
		List<Cart> carts = loco.getCarts();
		for (Cart cart : carts) {
			if (cart.getXPos() == this.getXPos() && cart.getYPos() == this.getYPos()
					&& cart.getResource() != null && resources.size() != 0) {
				
				// Die Resource der Cart
				Resource resourceCart = cart.getResource();
				
				for (Resource resourceTrainstation : resources) {
					if (resourceCart instanceof Gold && resourceTrainstation instanceof Coal /* && ich will Kohle */) {
						// 1 Gold : 1 Kohle
						Resource r = cart.getResource();
						cart.removeResourceFromCart();
						resources.remove(resourceTrainstation);
						resources.add(r);
						cart.loadResourceOntoCart(resourceTrainstation);
						
						notifyResourceExchanged(resourceCart.getDescription());
					}
					if (resourceCart instanceof Coal && resourceTrainstation instanceof Gold /* && ich will Gold */) {
						// 3 Kohle : 1 Gold
						if (carts.size() >= 3 /* && 3x Coal */) {
							for (Cart c : carts) {
								if (c.getResource().getDescription() == "Coal") {
									Resource r = c.getResource();
									c.removeResourceFromCart();
									resources.add(r);
								}
							}
							resources.remove(resourceTrainstation);
							cart.loadResourceOntoCart(resourceTrainstation);
							
							notifyResourceExchanged(resourceCart.getDescription());
						}
					}
					if (resourceCart instanceof Gold && resourceTrainstation instanceof PointContainer /* && ich will Punkte */) {
						// 2 Gold : 1 Punkte
						if (carts.size() >= 2 /* && 2x Gold */) {
							for (Cart c : carts) {
								if (c.getResource().getDescription() == "Gold") {
									Resource r = c.getResource();
									c.removeResourceFromCart();
									resources.add(r);
								}
							}
							resources.remove(resourceTrainstation);
							cart.loadResourceOntoCart(resourceTrainstation);
							
							notifyResourceExchanged(resourceCart.getDescription());
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
