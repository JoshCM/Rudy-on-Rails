package commands.game;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Loco;
import models.game.Map;
import models.game.Publictrainstation;
import models.helper.Validator;
import models.session.GameSession;
import models.session.RoRSession;

public class ActivateTradeMenuCommand extends CommandBase {
	
	private UUID playerId;
	private UUID publicTrainstationId;

	public ActivateTradeMenuCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		playerId = messageInfo.getValueAsUUID("playerId");
		publicTrainstationId = messageInfo.getValueAsUUID("publicTrainstationId");
	}

	@Override
	public void execute() {
		
		GameSession gameSession = (GameSession)session;
		Map map = gameSession.getMap();
		Loco loco = gameSession.getLocomotiveByPlayerId(playerId);
		Publictrainstation publicTrainstation = (Publictrainstation)map.getPlaceableOnSquareById(publicTrainstationId);
		if (Validator.checkLocoInRangeOfSquare(map, loco, map.getSquareById(publicTrainstation.getSquareId()), 100)){
			publicTrainstation.showTradeMenu();
		}
	}

}
