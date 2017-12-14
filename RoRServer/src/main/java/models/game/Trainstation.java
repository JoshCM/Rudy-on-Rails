package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import communication.MessageInformation;

public class Trainstation extends InteractiveGameObject implements PlaceableOnSquare {

	private List<UUID> trainstationRailIds;
	public Trainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id) {
		super(sessionName, square, id);
		this.trainstationRailIds = trainstationRailIds;
		
		notifyCreatedTrainstation();
	}
	
	private void notifyCreatedTrainstation() {
		MessageInformation messageInfo = new MessageInformation("CreateTrainstation");
		messageInfo.putValue("trainstationId", getId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		
		List<JsonObject> rails = new ArrayList<JsonObject>();
		for(UUID railId : getTrainstationRailIds()) {
			JsonObject json = new JsonObject();
			json.addProperty("railId", railId.toString());
			rails.add(json);
		}
		
		messageInfo.putValue("trainstationRails", rails);

		notifyChange(messageInfo);
	}

	public List<UUID> getTrainstationRailIds() {
		return trainstationRailIds;
	}
}
