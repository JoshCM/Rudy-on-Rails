package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import communication.MessageInformation;
import models.session.RoRSession;

public class Playertrainstation extends Trainstation {
	private UUID spawnPointForLoco;
	private UUID spawnPointForCart;

	public Playertrainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id, Compass alignment,
			Stock stock, UUID playerId) {
		super(sessionName, square, trainstationRailIds, id, alignment, stock);
		this.setPlayerId(playerId);
		notifyCreatedPlayertrainstation();
	}
	
	public Playertrainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id, Compass alignment,
			Stock stock, UUID playerId,Crane crane) {
		super(sessionName, square, trainstationRailIds, id, alignment, stock);
		this.setPlayerId(playerId);
		this.crane = crane;
		notifyCreatedPlayertrainstation();
	}

	public void setSpawnPointforLoco(UUID railId) {
		spawnPointForLoco = railId;
	}

	public UUID getSpawnPointforLoco() {
		return spawnPointForLoco;
	}

	/**
	 * Gibt den SpawnPoint für neue Carts zurück
	 * @return
	 */
	public UUID getSpawnPointForCart() {
		return spawnPointForCart;
	}

	/**
	 * Setzt den SpawnPoint für neue Carts
	 * @param spawnPointForCart
	 */
	public void setSpawnPointForCart(UUID spawnPointForCart) {
		this.spawnPointForCart = spawnPointForCart;
	}
	
	private void notifyCreatedPlayertrainstation() {
		MessageInformation messageInfo = new MessageInformation("CreatePlayertrainstation");
		messageInfo.putValue("playerId", getPlayerId());
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
	
	@Override
	public Playertrainstation loadFromMap(Square square, RoRSession session) {
		Playertrainstation oldTrainStation = (Playertrainstation) square.getPlaceableOnSquare();
		Playertrainstation newTrainStation = new Playertrainstation(session.getSessionName(), square,
				oldTrainStation.getTrainstationRailIds(), oldTrainStation.getId(), oldTrainStation.alignment, oldTrainStation.getStock(), oldTrainStation.getPlayerId(), oldTrainStation.crane);
		// der sessionName muss neu gesetzt werden, damit der Observer Änderungen dieses Objekts mitbekommen kann
		newTrainStation.setSessionName(session.getSessionName());
		
		// setze den alten SpawnPoint für die neue Trainstation
		newTrainStation.setSpawnPointforLoco(oldTrainStation.getSpawnPointforLoco());

		log.info("PlayerTrainStation erstellt: " + newTrainStation.toString());
		return newTrainStation;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CLOCKWISE;
		result = prime * result + COUNTER_CLOCKWISE;
		result = prime * result + ((alignment == null) ? 0 : alignment.hashCode());
		result = prime * result + ((spawnPointForLoco == null) ? 0 : spawnPointForLoco.hashCode());
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
		Playertrainstation other = (Playertrainstation) obj;
		if (CLOCKWISE != other.CLOCKWISE)
			return false;
		if (COUNTER_CLOCKWISE != other.COUNTER_CLOCKWISE)
			return false;
		if (alignment != other.alignment)
			return false;
		if (spawnPointForLoco == null) {
			if (other.spawnPointForLoco != null)
				return false;
		} else if (!spawnPointForLoco.equals(other.spawnPointForLoco))
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

	@Override
	public String toString() {
		return "PlayerTrainstation [trainstationRailIds=" + trainstationRailIds + ", alignment=" + alignment
				+ ", spawnPointForLoco=" + spawnPointForLoco + "]";
	}
}
