package commands.game;

import java.util.UUID;
import commands.base.Command;
import models.game.Map;
import models.game.PlayerLoco;
import models.game.Square;
import models.session.GameSession;
import models.session.RoRSession;
/**
 * 
 * @author Isabel Rott, Michelle Le
 * Command fuer das Erstellen einer Lok
 */
public class CreateLocoCommand implements Command {

	private int xPos;
	private int yPos;
	private UUID playerId;
	protected RoRSession session;
	/**
	 * yPos, xPos müssen von den Bahnhöfen rausgelesen werden
	 * @param session
	 * @param messageInfo
	 */
	public CreateLocoCommand(RoRSession session, UUID playerId) {
		xPos = 8;
		yPos = 3;
		this.session = session;
		//xPos = messageInfo.getValueAsInt("xPos");
		//yPos = messageInfo.getValueAsInt("yPos");
		this.playerId = playerId;
	}

	@Override
	public void execute() {
		GameSession gameSession = (GameSession)session;
		
		Map map = gameSession.getMap();
		Square square = map.getSquare(xPos, yPos);
		
		//prüfen ob auf dem Square eine Rail liegt
		if(square.getPlaceableOnSquare() != null) {
			PlayerLoco loco = new PlayerLoco(session.getName(), square, playerId);
			((GameSession) session).addLoco(loco);
		}
	}
}
