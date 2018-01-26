package commands.game;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Loco;
import models.session.GameSession;
import models.session.RoRSession;

public class ExchangeResourceCommand extends CommandBase {
	
	private UUID playerId;
	private String resourcentausch;

	public ExchangeResourceCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.playerId = messageInfo.getValueAsUUID("playerId");
		this.resourcentausch = messageInfo.getValueAsString("resourcentausch");
	}

	@Override
	public void execute() {
        GameSession gameSession = (GameSession)session;
        Loco loco = gameSession.getLocomotiveByPlayerId(playerId);
        
        
		
	}
	
	private String getResourceFromString(String resourcentausch) {
		String[] r = resourcentausch.split(" ");
		return "hallo";
	}

}
