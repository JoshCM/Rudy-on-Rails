package commands.editor;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Player;
import models.session.EditorSession;
import models.session.EditorSessionManager;
import models.session.RoRSession;

public class StartDirectGameCommand extends CommandBase {

	private UUID playerId;
	private boolean isHost;
	public StartDirectGameCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		playerId = messageInfo.getValueAsUUID("playerId");
		isHost = messageInfo.getValueAsBoolean("isHost");
	}

	@Override
	public void execute() {
		Player player = session.getPlayerById(playerId);

		// Alle Spieler löschen wenn Spieler Host ist
		if (isHost) {
			EditorSessionManager.getInstance().removeEditorSession((EditorSession) session, true);
		} 
	}

}
