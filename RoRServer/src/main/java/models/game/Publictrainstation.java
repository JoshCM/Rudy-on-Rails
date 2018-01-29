package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import communication.MessageInformation;
import models.session.RoRSession;

public class Publictrainstation extends Trainstation {
	
	public Publictrainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id,
			Compass alignment, Stock stock) {
		super(sessionName, square, trainstationRailIds, id, alignment, stock);
		notifyCreatedPublictrainstation();
	}
	
	public Publictrainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id, Compass alignment,
			Stock stock, UUID playerId,Crane crane) {
		super(sessionName, square, trainstationRailIds, id, alignment, stock);
		this.setPlayerId(playerId);
		this.crane = crane;
		notifyCreatedPublictrainstation();
	}
	
	private void notifyCreatedPublictrainstation() {
		MessageInformation messageInfo = new MessageInformation("CreatePublictrainstation");
		messageInfo.putValue("trainstationId", getId());
		if(this.crane != null) {
			messageInfo.putValue("craneXPos", this.crane.getXPos());
			messageInfo.putValue("craneYPos", this.crane.getYPos());
		}
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
	
	public void showTradeMenu() {
		MessageInformation message = new MessageInformation("ShowTradeRelation");
		message.putValue("trainstationId", getId());
		message.putValue("playerId", getPlayerId());
		message.putValue("tradeable", true);
		notifyChange(message);
	}
	
	@Override
	public Publictrainstation loadFromMap(Square square, RoRSession session) {
		Publictrainstation oldTrainStation = (Publictrainstation) square.getPlaceableOnSquare();
		Publictrainstation newTrainStation = new Publictrainstation(session.getSessionName(), square,
				oldTrainStation.getTrainstationRailIds(), oldTrainStation.getId(), oldTrainStation.alignment, oldTrainStation.getStock());
		newTrainStation.setCrane(oldTrainStation.getCrane());
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
		double exchangeRate = 0.0;
		if (c.getQuantity() >= 3) {
			if ((c.getQuantity() % 3 == 0)) {
				exchangeRate = c.getQuantity() / 3;
			} else if (c.getQuantity() % 3 != 0) {
				exchangeRate = Math.floor(c.getQuantity() / 3);
			}
			Gold gold = new Gold(getSessionName(), (int)exchangeRate);
			return gold;
		} else {
			log.info("Tauschverhältnis Kohle 3 : 1 Gold. Der Spieler besitzt zu wenig Kohle!");
		}
		return null;
	}
	
	public PointContainer exchangeGoldToPoints(Gold g) {
		// Gold 2 : 1 Punkte
		double exchangeRate = 0.0;
		if (g.getQuantity() >= 2) {
			if (g.getQuantity() % 2 == 0) {
				exchangeRate = g.getQuantity() / 2;
			} else if (g.getQuantity() % 2 != 0) {
				exchangeRate = Math.floor(g.getQuantity() / 2);
			}
			PointContainer points = new PointContainer(getSessionName(), (int)exchangeRate);
			return points;
		} else {
			log.info("Tauschverhältnis Gold 2 : 1 Punkt. Der Spieler besitzt zu wenig Punkte!");
		}
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CLOCKWISE;
		result = prime * result + COUNTER_CLOCKWISE;
		result = prime * result + ((alignment == null) ? 0 : alignment.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result + ((trainstationRailIds == null) ? 0 : trainstationRailIds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publictrainstation other = (Publictrainstation) obj;
		if (CLOCKWISE != other.CLOCKWISE)
			return false;
		if (COUNTER_CLOCKWISE != other.COUNTER_CLOCKWISE)
			return false;
		if (alignment != other.alignment)
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		if (trainstationRailIds == null) {
			if (other.trainstationRailIds != null)
				return false;
		} else if (!trainstationRailIds.equals(other.trainstationRailIds))
			return false;
		return true;
	}
}
