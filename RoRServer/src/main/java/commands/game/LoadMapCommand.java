package commands.game;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Map;
import models.game.Square;
import models.session.GameSession;
import models.session.GameSessionManager;
import models.session.RoRSession;
import persistent.MapManager;

public class LoadMapCommand extends CommandBase {

	public LoadMapCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

	}

	@Override
	public void execute() {
		System.out.print("Ich soll eine DefaultMap laden!");
		
		GameSession game = GameSessionManager.getInstance().getGameSession();
		Map map = MapManager.loadMap("DefaultGameMap");
		Square [][] squares = map.getSquares();

		
	}

}
