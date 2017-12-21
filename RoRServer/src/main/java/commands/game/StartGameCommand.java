package commands.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import communication.topic.MessageQueue;
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

	public StartGameCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
	}

	@Override
	public void execute() {
		System.out.println("Ich soll eine DefaultMap laden!");
		
		// Map laden
		Map map = MapManager.loadMap("GameDefaultMap");
		map.setSessionName(session.getSessionName());
		map.addObserver(MessageQueue.getInstance());
		session.setMap(map);
		
		// Jedes Square durchgehen
		Square [][] squares = map.getSquares();
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				
				// Square holen 
				Square square = squares[i][j];
				// Wenn ein Rail auf dem Square liegt
				if (square.getPlaceableOnSquare() != null) {
					Rail rail = (Rail)square.getPlaceableOnSquare();
					// Hole die SectionPositions aus den RailSections und speichere in Liste
					List<Compass> railSectionPosition = new ArrayList<Compass>();
					for (RailSection section : rail.getRailSectionList()) {
						railSectionPosition.add(section.getNode1());
						railSectionPosition.add(section.getNode2());
					}
					// Neues Rail erstellen und damit an den Client schicken
					Rail newRail = new Rail(session.getSessionName(), square, railSectionPosition);
					System.out.println("Neue Rail erstellt auf " + i + " " + j + ": " + newRail.toString());
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
