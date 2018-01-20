package commands.game;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.game.Map;
import models.game.Placeable;
import models.game.PlaceableOnSquare;
import models.game.Rail;
import models.session.RoRSession;

public class PlaceSensorCommand extends CommandBase {

	private UUID selectedModelId;
	
	public PlaceSensorCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.selectedModelId = messageInfo.getValueAsUUID("selectedModelId");
	}

	@Override
	public void execute() {
		
		Map map = this.session.getMap();
		Placeable placeable = map.getPlaceableById(selectedModelId);
		
		// Es muss ein Rail sein
		if (placeable instanceof Rail) {
			Rail rail = (Rail)placeable;
			
			// Kein Sensor auf Rail
			if (rail.getSensor() == null) {
				rail.placeSensor();
			}
		} else {
			throw new InvalidModelOperationException("Keine Rail");
		}
	}
}
