package commands.game;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.GhostLoco;
import models.game.Loco;
import models.game.Script;
import models.session.GameSession;
import models.session.RoRSession;

public class ChangeCurrentScriptOfGhostLocosCommand extends CommandBase {
	private UUID scriptId;
	private UUID playerId;
	
	public ChangeCurrentScriptOfGhostLocosCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		
		scriptId = messageInfo.getValueAsUUID("scriptId");
		playerId = messageInfo.getValueAsUUID("playerId");
	}

	@Override
	public void execute() {
		GameSession gameSession = (GameSession)session;
		for(Loco loco : gameSession.getLocos()) {
			if(loco instanceof GhostLoco && loco.getPlayerId().equals(playerId)) {
				Script script = gameSession.getScripts().getGhostLocoScriptForId(scriptId);
				((GhostLoco)loco).changeCurrentScriptName(script.getScriptName());
			}
		}
	}
}
