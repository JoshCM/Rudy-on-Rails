package commands.game;

import java.util.UUID;

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
 * Command f�r das Erstellen einer Lok
 */
public class CreateLocoCommand extends CommandBase {

	private int xPos;
	private int yPos;
	private UUID playerId;
	
	public CreateLocoCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		xPos = 7;
		yPos = 3;
		//xPos = messageInfo.getValueAsInt("xPos");
		//yPos = messageInfo.getValueAsInt("yPos");
		playerId = messageInfo.getValueAsUUID("playerId");
	}

	@Override
	public void execute() {
		GameSession gameSession = (GameSession)session;
		
		Map map = gameSession.getMap();
		Square square = map.getSquare(xPos, yPos);
		
		//pr�fen ob auf dem Square eine Rail liegt
		if(square.getPlaceableOnSquare() != null) {
			Rail rail = (Rail) square.getPlaceableOnSquare();
			Loco loco = new Loco(session.getName(),square, map);
			((GameSession) session).add(loco);
			rail.setPlaceableOnRail(loco);
		}
		
		
	}

}
