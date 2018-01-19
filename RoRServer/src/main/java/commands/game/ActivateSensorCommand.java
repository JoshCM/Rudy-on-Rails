package commands.game;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.game.Map;
import models.game.Rail;
import models.session.RoRSession;

public class ActivateSensorCommand extends CommandBase {

	private UUID railId;
	
	public ActivateSensorCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.railId = messageInfo.getValueAsUUID("railId");
	}

	@Override
	public void execute() {
		Map map = this.session.getMap();
		Rail rail = (Rail)map.getPlaceableOnSquareById(railId);
		if (rail.railIsStraight()) {
			rail.activateSensor();
		} else {
			throw new InvalidModelOperationException("Rail ist nicht gerade");
		}
		
	}

}
