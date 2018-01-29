package commands.game;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.GamePlayer;
import models.game.Map;
import models.game.Publictrainstation;
import models.session.GameSession;
import models.session.RoRSession;

/**
 * Merkt sich die aktuell ausgewählte Publictrainstation des Players
 * @author irott001
 *
 */
public class SelectPublictrainstationCommand extends CommandBase {
	
	private UUID playerId;
	private UUID publicTrainstationId;

	public SelectPublictrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		playerId = messageInfo.getValueAsUUID("playerId");
		publicTrainstationId = messageInfo.getValueAsUUID("publicTrainstationId");
	}

	@Override
	public void execute() {
		
		GameSession gameSession = (GameSession)session;
		Map map = gameSession.getMap();
		Publictrainstation publicTrainstation = (Publictrainstation)map.getPlaceableOnSquareById(publicTrainstationId);
		GamePlayer player = (GamePlayer)gameSession.getPlayerById(playerId);
		player.setCurrentSelectedPublictrainstation(publicTrainstation);
	}

}
