package commands.game;
import commands.base.CommandBase;
import communication.MessageInformation;
import communication.topic.TopicMessageQueue;
import models.game.Coal;
import models.game.Compass;
import models.game.Gold;
import models.game.Map;
import models.game.PlaceableOnSquare;
import models.game.Player;
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
		
		// Wenn der MapName f�r das Laden mitgeschickt wird
		// String mapName = messageInfo.getValueAsString("mapName");
		
		// Map: Zug f�hrt im Kreis
		//String mapName = "GameDefaultMap";
		
		// Map: Zug steht + Bahnh�fe
		String mapName = "GameDefaultMap2";
		
		// Map laden
		Map map = MapManager.loadMap(mapName);
		map.setSessionNameForMapAndSquares(session.getName());
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

		// Loco erstellen
		createLocoForPlayers(session);
		
		// Ein paar Ressourcen verteilen
		// constructAdditionalPylons(session);
		
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
	
	private void constructAdditionalPylons(RoRSession session) {
		Square goldSquare = session.getMap().getSquare(0, 0);
		Square coalSquare = session.getMap().getSquare(0, 1);
		Gold gold = new Gold(session.getName(), goldSquare);
		Coal coal = new Coal(session.getName(), coalSquare);
	}
}
