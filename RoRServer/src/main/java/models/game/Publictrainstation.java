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
		Gold g = new Gold(getSessionName(), 100);
		Coal c = new Coal(getSessionName(), 100);
		PointContainer p = new PointContainer(getSessionName(), 100);
		resources.add(g);
		resources.add(c);
		resources.add(p);
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
		
		//müssen Ressourcen auch mitgegeben werden?

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
	
	private void exchangeResourcesAtPublictrainstation() {
		for (Rail rail : this.getTrainstationRails()) {
			Square trainstationSquare = rail.getSquareFromGameSession();
			if (trainstationSquare != null) {
				if (trainstationSquare.getPlaceableOnSquare() instanceof PlayerLoco) {
					Loco loco = (Loco) trainstationSquare.getPlaceableOnSquare();
					List<Cart> carts = loco.getCarts();
					Cart cart = carts.get(0);
					
					exchangeResource(cart);
				}
			}
		}
	}

	private void exchangeResource(Cart cart) {
		if (this.getTrainstationRails().contains(cart.getXPos()) && this.getTrainstationRails().contains(cart.getYPos())
				&& cart.getResource() != null && resources.size() != 0) {
			
			// Die Resource der Cart
			Resource resourceCart = cart.getResource();
			
			for (Resource resourceTrainstation : resources) {
				if (resourceCart instanceof Gold && resourceTrainstation instanceof Coal /* && ich will Kohle */) {
					// 1 Gold : 1 Kohle
					if (resourceCart.getQuantity() > 1) {
						resourceCart.setQuantity(resourceCart.getQuantity() - 1);
					} else if (resourceCart.getQuantity() == 1) {
						cart.removeResourceFromCart();
						Resource r = resourceTrainstation;
						r.setQuantity(1);
						cart.loadResourceOntoCart(r);
					} else {
						log.info("Tauschverhältnis Gold 1 : 1 Kohle. Der Spieler besitzt zu wenig Gold!");
					}
					
					resourceTrainstation.setQuantity(resourceTrainstation.getQuantity() + 1);
					// Eigentlich müsste Kohle noch aus Trainstation-Quantity gelöscht werden
					
					notifyResourceExchanged(resourceCart.getDescription());
				}
				if (resourceCart instanceof Coal && resourceTrainstation instanceof Gold /* && ich will Gold */) {
					// 3 Kohle : 1 Gold
					if (resourceCart.getQuantity() > 3) {
						resourceCart.setQuantity(resourceCart.getQuantity() - 1);
					} else if (resourceCart.getQuantity() == 3) {
						cart.removeResourceFromCart();
						Resource r = resourceTrainstation;
						r.setQuantity(1);
						cart.loadResourceOntoCart(r);
					} else {
						log.info("Tauschverhältnis Kohle 3 : 1 Gold. Der Spieler besitzt zu wenig Kohle!");
					}
					
					resourceTrainstation.setQuantity(resourceTrainstation.getQuantity() + 1);
						
					notifyResourceExchanged(resourceCart.getDescription());
				}
				if (resourceCart instanceof Gold && resourceTrainstation instanceof PointContainer /* && ich will Punkte */) {
					// 2 Gold : 1 Punkte
					if (resourceCart.getQuantity() > 2) {
						resourceCart.setQuantity(resourceCart.getQuantity() - 1);
					} else if (resourceCart.getQuantity() == 2) {
						cart.removeResourceFromCart();
						Resource r = resourceTrainstation;
						r.setQuantity(1);
						cart.loadResourceOntoCart(r);
					} else {
						log.info("Tauschverhältnis Gold 2 : 1 Punkt. Der Spieler besitzt zu wenig Gold!");
					}
					
					resourceTrainstation.setQuantity(resourceTrainstation.getQuantity() + 1);
					
					notifyResourceExchanged(resourceCart.getDescription());
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
