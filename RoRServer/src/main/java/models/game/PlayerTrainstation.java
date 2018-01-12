package models.game;

import java.util.List;
import java.util.UUID;

import models.session.RoRSession;

public class PlayerTrainstation extends Trainstation {
	private Square spawnPointForLoco;

	public PlayerTrainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id, Compass alignment,
			Stock stock) {
		super(sessionName, square, trainstationRailIds, id, alignment, stock);
	}

	/**
	 * Setzt den SpanPoint für die Loco
	 * @param square
	 */
	public void setSpawnPointforLoco(Square square) {
		spawnPointForLoco = square;
	}

	/**
	 * Gibt den SpawnPoint für die Loco zurück
	 * @return Square als Startposition
	 */
	public Square getSpawnPointforLoco() {
		return spawnPointForLoco;
	}

	@Override
	public PlayerTrainstation loadFromMap(Square square, RoRSession session) {
		PlayerTrainstation trainStation = (PlayerTrainstation) square.getPlaceableOnSquare();
		PlayerTrainstation newTrainStation = new PlayerTrainstation(session.getName(), square,
				trainStation.getTrainstationRailIds(), trainStation.getId(), trainStation.alignment,
				trainStation.getStock());

		// der sessionName muss neu gesetzt werden, damit der Observer Änderungen dieses
		// Objekts mitbekommen kann
		newTrainStation.setName(session.getName());

		log.info("TrainStation erstellt: " + newTrainStation.toString());
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
		PlayerTrainstation other = (PlayerTrainstation) obj;
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
		return "Trainstation [trainstationRailIds=" + trainstationRailIds + ", alignment=" + alignment
				+ ", spawnPointForLoco=" + spawnPointForLoco + "]";
	}
}
