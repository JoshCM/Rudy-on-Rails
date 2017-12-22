package commands.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import communication.topic.TopicMessageQueue;
import models.game.Compass;
import models.game.Map;
import models.game.PlaceableOnSquare;
import models.game.Player;
import models.game.Rail;
import models.game.RailSection;
import models.game.Square;
import models.session.GameSession;
import models.session.RoRSession;
import persistent.MapManager;

public class StartGameCommand extends CommandBase {
	
	MessageInformation messageInfo;

	public StartGameCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.messageInfo = messageInfo;
	}

	@Override
	public void execute() {
		System.out.println("Map laden ...");
		
		// Wenn der MapName für das Laden mitgeschickt wird
		// String mapName = messageInfo.getValueAsString("mapName");
		String mapName = "NeueTolleMap";
		
		// Map laden
		Map map = MapManager.loadMap(mapName);
		map.setSessionName(session.getSessionName());
		map.addObserver(TopicMessageQueue.getInstance());
		session.setMap(map);
		
		// Jedes Square durchgehen
		Square [][] squares = map.getSquares();
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				
				// Square holen 
				Square square = squares[i][j];
				// Wenn ein Rail auf dem Square liegt
				if (square.getPlaceableOnSquare() != null) {
					PlaceableOnSquare placeableOnSquare = square.getPlaceableOnSquare();
					placeableOnSquare.loadFromMap(square, session);
				}
			}
		}

		createLocoForPlayers(session);
		
		((GameSession)session).startGame();
	}

	/**
	 * Sobald ein Player der GameSession gejoined ist, soll eine Loco erstellt werden, die dem Player zugeordnet ist
	 * @param messageInformation
	 */
	
	private void createLocoForPlayers(RoRSession session) {
		for(Player p : session.getPlayers()) {		
			CreateLocoCommand createLocoCommand = new CreateLocoCommand(session, p.getId());
			System.out.println();
			createLocoCommand.execute();
		}
	}
}
