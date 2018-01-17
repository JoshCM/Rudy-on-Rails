package models.game;

import java.util.List;
import java.util.UUID;

import models.session.RoRSession;

public class Publictrainstation extends Trainstation {

	public Publictrainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id,
			Compass alignment, Stock stock) {
		super(sessionName, square, trainstationRailIds, id, alignment, stock);
	}
	
	@Override
	public Publictrainstation loadFromMap(Square square, RoRSession session) {
		Publictrainstation trainStation = (Publictrainstation) square.getPlaceableOnSquare();
		Publictrainstation newTrainStation = new Publictrainstation(session.getName(), square,
				trainStation.getTrainstationRailIds(), trainStation.getId(), trainStation.alignment,
				trainStation.getStock());

		// der sessionName muss neu gesetzt werden, damit der Observer Änderungen dieses
		// Objekts mitbekommen kann
		newTrainStation.setName(session.getName());

		log.info("TrainStation erstellt: " + newTrainStation.toString());
		return newTrainStation;
	}
}
