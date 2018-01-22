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
		Publictrainstation trainStation = (Publictrainstation) square.getPlaceableOnSquare();
		Publictrainstation newTrainStation = new Publictrainstation(session.getName(), square,
				trainStation.getTrainstationRailIds(), trainStation.getId(), trainStation.alignment,
				trainStation.getStock());

		// der sessionName muss neu gesetzt werden, damit der Observer �nderungen dieses
		// Objekts mitbekommen kann
		newTrainStation.setName(session.getName());

		log.info("PublicTrainStation erstellt: " + newTrainStation.toString());
		return newTrainStation;
	}
}
