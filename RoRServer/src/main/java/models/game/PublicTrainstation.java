package models.game;

import java.util.List;
import java.util.UUID;

import models.session.RoRSession;

public class PublicTrainstation extends Trainstation {

	public PublicTrainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id,
			Compass alignment, Stock stock) {
		super(sessionName, square, trainstationRailIds, id, alignment, stock);
	}
	
	@Override
	public PublicTrainstation loadFromMap(Square square, RoRSession session) {
		PublicTrainstation trainStation = (PublicTrainstation) square.getPlaceableOnSquare();
		PublicTrainstation newTrainStation = new PublicTrainstation(session.getName(), square,
				trainStation.getTrainstationRailIds(), trainStation.getId(), trainStation.alignment,
				trainStation.getStock());

		// der sessionName muss neu gesetzt werden, damit der Observer Änderungen dieses
		// Objekts mitbekommen kann
		newTrainStation.setName(session.getName());

		log.info("TrainStation erstellt: " + newTrainStation.toString());
		return newTrainStation;
	}
}
