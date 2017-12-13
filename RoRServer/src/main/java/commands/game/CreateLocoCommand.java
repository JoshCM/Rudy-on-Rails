package commands.game;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Loco;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
import models.session.EditorSession;
import models.session.GameSession;
import models.session.RoRSession;

public class CreateLocoCommand extends CommandBase {

	private int xPos;
	private int yPos;
	
	public CreateLocoCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		
		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
	}

	@Override
	public void execute() {
		GameSession gameSession = (GameSession)session;
		
		Map map = gameSession.getMap();
		Square square = map.getSquare(xPos, yPos);
		
		//prüfen ob auf dem Square eine Rail liegt
		if(square.getPlaceableOnSquare() != null) {
			Rail rail = (Rail) square.getPlaceableOnSquare();
			Loco loco = new Loco(session.getName(),square);
			rail.setPlaceableOnRail(loco);
		}
		
		
	}

}
