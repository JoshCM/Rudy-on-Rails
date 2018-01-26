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
		initializeResourceStock();
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
	
	public Coal exchangeGoldToCoal(Gold g) {
		// Gold 1 : 1 Kohle
		if (g.getQuantity() >= 1) {
			Coal coal = new Coal(getSessionName(), g.getQuantity());
			return coal;
		} else {
			log.info("Tauschverhältnis Gold 1 : 1 Kohle. Der Spieler besitzt zu wenig Gold!");
		}
		return null;
	}
	
	public Gold exchangeCoalToGold(Coal c) {
		// Kohle 3 : 1 Gold
		if (c.getQuantity() >= 3) {
			Gold gold = new Gold(getSessionName(), (c.getQuantity() / 3));
			return gold;
		} else {
			log.info("Tauschverhältnis Gold 1 : 1 Kohle. Der Spieler besitzt zu wenig Gold!");
		}
		return null;
	}
	
	public PointContainer exchangeGoldToPoints(Gold g) {
		// Gold 2 : 1 Punkte
		if (g.getQuantity() >= 2) {
			PointContainer points = new PointContainer(getSessionName(), (g.getQuantity() / 2));
			return points;
		} else {
			log.info("Tauschverhältnis Gold 1 : 1 Kohle. Der Spieler besitzt zu wenig Gold!");
		}
		return null;
	}
}
