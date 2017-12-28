package commands.game;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.session.GameSession;
import models.session.RoRSession;

/**
 * Setzt den neuen MapName der GameSession
 */
public class ChangeMapNameCommand extends CommandBase{

	private String mapName;
	
	public ChangeMapNameCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.mapName = messageInfo.getValueAsString("mapName");
	}

	@Override
	public void execute() {
		GameSession gameSession = (GameSession)session;
		gameSession.setMapName(mapName);
	}

}
