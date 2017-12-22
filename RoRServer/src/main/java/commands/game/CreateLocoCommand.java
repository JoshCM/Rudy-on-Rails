package commands.game;

import java.util.UUID;

import commands.base.Command;
import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Loco;
import models.game.Map;
import models.game.Player;
import models.game.Rail;
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
	 * yPos, xPos m�ssen von den Bahnh�fen rausgelesen werden
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
		
		//pr�fen ob auf dem Square eine Rail liegt
		if(square.getPlaceableOnSquare() != null) {
			Rail rail = (Rail) square.getPlaceableOnSquare();
			Loco loco = new Loco(session.getSessionName(),square, map, playerId);
			((GameSession) session).addLocomotive(loco);
			rail.setPlaceableOnRail(loco);
		}
		
		
	}

}
