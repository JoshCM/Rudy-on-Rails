package commands.game;

import org.apache.log4j.Logger;

import commands.base.CommandBase;
import communication.MessageInformation;
import communication.queue.receiver.QueueReceiver;
import communication.topic.TopicMessageQueue;
import models.game.Map;
import models.game.PlaceableOnSquare;
import models.game.Player;
import models.game.Square;
import models.session.GameSession;
import models.session.RoRSession;
import persistent.MapManager;

public class StartGameCommand extends CommandBase {
	static Logger log = Logger.getLogger(QueueReceiver.class.getName());
	
	public StartGameCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
	}

	@Override
	public void execute() {
		log.info("loading map: " + ((GameSession)session).getMapName());
		// Map laden
		Map map = MapManager.loadMap(((GameSession)session).getMapName());
		map.setSessionName(session.getName());
		map.addObserver(TopicMessageQueue.getInstance());
		session.setMap(map);
		
		// Jedes Square durchgehen
		Square [][] squares = map.getSquares();
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				
				// Square holen 
				Square square = squares[i][j];
				// Wenn etwas auf dem Square liegt
				if (square.getPlaceableOnSquare() != null) {
					PlaceableOnSquare placeableOnSquare = square.getPlaceableOnSquare();
					placeableOnSquare.loadFromMap(square, session);
				}
			}
		}

		createLocoForPlayers(session);
		
		session.start();
	}

	/**
	 * Sobald ein Player der GameSession gejoined ist, soll eine Loco erstellt werden, die dem Player zugeordnet ist
	 * @param messageInformation
	 */
	
	private void createLocoForPlayers(RoRSession session) {
		for(Player p : session.getPlayers()) {		
			CreateLocoCommand createLocoCommand = new CreateLocoCommand(session, p.getId());
			createLocoCommand.execute();
		}
	}
}
