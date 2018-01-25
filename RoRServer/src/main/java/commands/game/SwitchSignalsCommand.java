package commands.game;

import java.util.UUID;
import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.GamePlayer;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
import models.session.GameSession;
import models.session.RoRSession;

/**
 * Schaltet ein Signal um, sofern nicht ein anderer Spieler das Signal zuletzt
 * umgestellt hat und der Spieler genug Gold hat, um die Kosten zu decken
 */
public class SwitchSignalsCommand extends CommandBase {
	private int xPos;
	private int yPos;
	private UUID playerId;

	public SwitchSignalsCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
		playerId = messageInfo.getValueAsUUID("playerId");
	}

	@Override
	public void execute() {
		GameSession gameSession = (GameSession) session;
		Map map = gameSession.getMap();
		Square square = map.getSquare(xPos, yPos);

		if (square.getPlaceableOnSquare() instanceof Rail) {
			Rail rail = (Rail) square.getPlaceableOnSquare();
			GamePlayer player = (GamePlayer) gameSession.getPlayerById(playerId);

			if (rail.getSignals() != null && player != null) {
				int switchCost = rail.getSignals().getSwitchCost();

				if (switchCost <= player.getGoldCount()) {
					player.removeGold(rail.getSignals().getSwitchCost());
					rail.getSignals().switchSignalsManually();
				}
			}
		}
	}
}
