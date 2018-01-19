package commands.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import commands.base.CommandBase;
import communication.MessageInformation;
import communication.queue.receiver.QueueReceiver;
import communication.topic.TopicMessageQueue;
import exceptions.MapNotFoundException;
import models.game.Crane;
import models.game.Compass;
import models.game.GhostLoco;
import models.game.Map;
import models.game.Mine;
import models.game.Player;
import models.game.PlayerLoco;
import models.game.Rail;
import models.game.Square;
import models.game.Stock;
import models.game.Trainstation;
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
		GameSession gameSession = (GameSession)session;
		Map map;
		try {
			map = MapManager.loadMap(gameSession.getMapName());
			startLoadedMap(map);
		} catch (MapNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void startLoadedMap(Map map) {
		GameSession gameSession = (GameSession)session;
		log.info("loading map: " + gameSession.getMapName());
		map.setSessionNameForMapAndSquares(gameSession.getSessionName());
		map.addObserver(TopicMessageQueue.getInstance());
		gameSession.setMap(map);

		// hier müssen die Rails zuerst erstellt werden, danach die Trainstations
		// da die Trainstations die Referenzen der noch nicht vorhandenen Rails an den
		// Client schicken würden
		List<Square> railSquaresToCreate = new ArrayList<Square>();
		List<Square> trainstationSquaresToCreate = new ArrayList<Square>();
		List<Square> stockSquaresToCreate = new ArrayList<Square>();
		
		// Jedes Square durchgehen
		Square[][] squares = map.getSquares();
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {

				// Square holen
				Square square = squares[i][j];
				// square bekommt sessionName und observer
				square.setSessionName(gameSession.getSessionName());
				square.addObserver(TopicMessageQueue.getInstance());

				// Wenn etwas auf dem Square liegt
				if (square.getPlaceableOnSquare() != null) {
					if (square.getPlaceableOnSquare() instanceof Rail)
						railSquaresToCreate.add(square);
					if (square.getPlaceableOnSquare() instanceof Stock)
						stockSquaresToCreate.add(square);
					if (square.getPlaceableOnSquare() instanceof Trainstation)
						trainstationSquaresToCreate.add(square);
				}
			}
		}

		// erzeugen der neuen Stocks auf deren Squares
		for (Square stockSquare : stockSquaresToCreate) {
			stockSquare.setPlaceableOnSquare(stockSquare.getPlaceableOnSquare().loadFromMap(stockSquare, session));
		}
	
		List<Rail> generatedRails = new ArrayList<Rail>();
		// erzeugen der neuen Rails auf deren Squares
		for (Square railSquare : railSquaresToCreate) {
			Rail rail = (Rail)railSquare.getPlaceableOnSquare();
			Rail newRail = (Rail)railSquare.getPlaceableOnSquare().loadFromMap(railSquare, session);
			// liegt auf einer Rail eine Mine, muss diese darauf erzeugt werden
			if (rail.getPlaceableOnrail() instanceof Mine) {
				Mine mine = (Mine)rail.getPlaceableOnrail();
				Mine newMine = (Mine)mine.loadFromMap(railSquare, session);
				newRail.setPlaceableOnRail(newMine);
			}else if(rail.getPlaceableOnrail() instanceof Crane) {
			//liegt ein Crane auf einer Rail muss diese ebenfalls neu erzeugt werden
				Crane crane = (Crane) rail.getPlaceableOnrail();
				Crane newCrane = (Crane)crane.loadFromMap(railSquare, session);
				rail.setPlaceableOnRail(newCrane);
			}
			railSquare.setPlaceableOnSquare(newRail);
			
			if(newRail.getSignals() != null) {
				((GameSession)session).registerTickableGameObject(newRail.getSignals());
			}
			generatedRails.add(newRail);
		}

		Iterator<Player> playerIterator = gameSession.getPlayers().iterator();
		
		// erzeugen der neuen Trainstations auf deren Squares
		for (Square trainstationSquare : trainstationSquaresToCreate) {
			
			// Alte Trainstation holen und neue Trainstation damit erstellen
			Trainstation oldTrainStation = (Trainstation) trainstationSquare.getPlaceableOnSquare();
			Trainstation newTrainStation = oldTrainStation.loadFromMap(trainstationSquare, gameSession);
			
			// Neue Trainstation auf Square setzen
			trainstationSquare.setPlaceableOnSquare(newTrainStation);
			
			// Square für Spawnpoint holen
			Rail rail = (Rail)map.getPlaceableOnSquareById(newTrainStation.getSpawnPointforLoco());
			Square locoSpawnPointSquare = rail.getSquareFromGameSession();
			//Square locoSpawnPointSquare = map.getSquareById(newTrainStation.getSpawnPointforLoco());
			
			// Für jeden Spieler eine Lok erstellen
			if(playerIterator.hasNext()) {
				// Loco wird erstellt und zur Liste der Locos hinzugefügt
				UUID playerId = playerIterator.next().getId();
				gameSession.addLoco(new PlayerLoco(gameSession.getSessionName(), locoSpawnPointSquare, playerId));
				GhostLoco ghostLoco = new GhostLoco(gameSession.getSessionName(), locoSpawnPointSquare, playerId);
				gameSession.addLoco(new PlayerLoco(gameSession.getName(), locoSpawnPointSquare, playerId, getLocoDirectionbyTrainstation(newTrainStation.getAlignment())));
				GhostLoco ghostLoco = new GhostLoco(gameSession.getName(), locoSpawnPointSquare, playerId, getLocoDirectionbyTrainstation(newTrainStation.getAlignment()));
				gameSession.addLoco(ghostLoco);
				ghostLoco.init();
				newTrainStation.setPlayerId(playerId);
			}
		}
		
		// generiert an den erzeugenten rails resourcen
		for(Rail generatedRail : generatedRails) {
			generatedRail.generateResourcesNextToRail();
		}

		gameSession.start();
	}
	
	private Compass getLocoDirectionbyTrainstation(Compass compass) {
		switch(compass) {
		case NORTH:
			return Compass.EAST;
		case EAST:
			return Compass.SOUTH;
		case SOUTH:
			return Compass.WEST;
		case WEST:
			return Compass.NORTH;
		}
		return null;
	}
}
