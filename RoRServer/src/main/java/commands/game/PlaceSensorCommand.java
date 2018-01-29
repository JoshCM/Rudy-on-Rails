package commands.game;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.game.GamePlayer;
import models.game.Loco;
import models.game.Map;
import models.game.Placeable;
import models.game.Rail;
import models.game.Sensor;
import models.game.Square;
import models.helper.Validator;
import models.session.GameSession;
import models.session.RoRSession;

/**
 * Command zum Setzen von Sensoren
 * @author apoeh001
 *
 */
public class PlaceSensorCommand extends CommandBase {

	private UUID selectedModelId;
	private UUID playerId;
	private GamePlayer player;
	
	public PlaceSensorCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.selectedModelId = messageInfo.getValueAsUUID("selectedModelId");
		this.playerId = messageInfo.getValueAsUUID("playerId");
		this.player = (GamePlayer)session.getPlayerById(playerId);
	}

	@Override
	public void execute() {
		
		GameSession gameSession = (GameSession)session;
		Map map = gameSession.getMap();	
		
		Placeable placeable = map.getPlaceableById(selectedModelId);
		// Es muss ein Rail sein
		if (placeable instanceof Rail) {
			
			Loco loco = gameSession.getPlayerLocoByPlayerId(playerId);
			Rail rail = (Rail)placeable;

			// Prüft, ob die Loco in der Nähe des Rails ist
			if(Validator.checkLocoInRangeOfRail(map, loco, rail)) {
				
				// Kein Sensor auf Rail
				if (rail.getSensor() == null) {
					player.removeGold(Sensor.SENSOR_COST);
					rail.placeSensor(playerId);
				}
				
			} else {
				throw new InvalidModelOperationException("Zug nicht in der Nähe");
			}
		} else {
			throw new InvalidModelOperationException("Keine Rail");
		}
	}
}
