package commands.game;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Player;
import models.session.GameSession;
import models.session.GameSessionManager;
import models.session.RoRSession;

public class LeaveGameCommand extends CommandBase {
	
	private UUID playerId;
	private boolean isHost;

	public LeaveGameCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		playerId = messageInfo.getValueAsUUID("playerId");
		isHost = messageInfo.getValueAsBoolean("isHost");
	}

	@Override
	public void execute() {
		Player player = session.getPlayerById(playerId);
		
		// Alle Spieler löschen wenn Spieler Host ist
		if(isHost) {
			GameSessionManager.getInstance().removeGameSession((GameSession)session);
		// Nur Spieler löschen
		} else {
			session.removePlayer(player);
		}
	}
}
