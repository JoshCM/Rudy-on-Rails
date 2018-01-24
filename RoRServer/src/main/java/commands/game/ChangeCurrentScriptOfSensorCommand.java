package commands.game;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.game.Map;
import models.game.Placeable;
import models.game.Rail;
import models.game.Sensor;
import models.scripts.Script;
import models.session.GameSession;
import models.session.RoRSession;

public class ChangeCurrentScriptOfSensorCommand extends CommandBase {
	
	private UUID selectedModelId;
	private UUID scriptId;
	private UUID playerId;

	public ChangeCurrentScriptOfSensorCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		selectedModelId = messageInfo.getValueAsUUID("selectedModelId");
		scriptId = messageInfo.getValueAsUUID("scriptId");	
		playerId = messageInfo.getValueAsUUID("playerId");
	}

	@Override
	public void execute() {
		
		GameSession gameSession = (GameSession)session;
		Map map = gameSession.getMap();
		Placeable placeable = map.getPlaceableById(selectedModelId);
		
		// Es muss ein Rail sein
		if (placeable instanceof Rail) {
			Rail rail = (Rail)placeable;
			Sensor sensor = rail.getSensor();
			if (playerId.equals(sensor.getPlayerId())) {
				Script script = gameSession.getScripts().getScriptForId(scriptId);
				sensor.changeCurrentScriptFilename(script.getFilename());
			} else {
				throw new InvalidModelOperationException("Der Sensor gehört dir nicht");
			}

		} else {
			throw new InvalidModelOperationException("Keine Rail");
		}
	}
}
