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
import models.game.GamePlayer;
import models.game.Color;
import models.game.Compass;
import models.game.GhostLoco;
import models.game.Loco;
import models.game.Map;
import models.game.Mine;
import models.game.Player;
import models.game.PlayerLoco;
import models.game.Playertrainstation;
import models.game.Rail;
import models.game.Square;
import models.game.Stock;
import models.game.Trainstation;
import models.game.Playertrainstation;
import models.game.Publictrainstation;
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
		GameSession gameSession = (GameSession) session;
		Map map;
		try {
			map = MapManager.loadMap(gameSession.getMapName());
			startLoadedMap(map);
		} catch (MapNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void startLoadedMap(Map map) {
		GameSession gameSession = (GameSession) session;
		log.info("loading map: " + gameSession.getMapName());
		map.setSessionNameForMapAndSquares(gameSession.getSessionName());
		map.addObserver(TopicMessageQueue.getInstance());
		map.notifySize();
		gameSession.setMap(map);

		// hier mÃ¼ssen die Rails zuerst erstellt werden, danach die Trainstations
		// da die Trainstations die Referenzen der noch nicht vorhandenen Rails an den
		// Client schicken wÃ¼rden
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
			Rail rail = (Rail) railSquare.getPlaceableOnSquare();
			Rail newRail = (Rail) railSquare.getPlaceableOnSquare().loadFromMap(railSquare, session);
			// liegt auf einer Rail eine Mine, muss diese darauf erzeugt werden
			if (rail.getPlaceableOnrail() instanceof Mine) {
				Mine mine = (Mine) rail.getPlaceableOnrail();
				Mine newMine = (Mine) mine.loadFromMap(railSquare, session);
				newRail.setPlaceableOnRail(newMine);
			} else if (rail.getPlaceableOnrail() instanceof Crane) {
				// liegt ein Crane auf einer Rail muss diese ebenfalls neu erzeugt werden
				Crane crane = (Crane) rail.getPlaceableOnrail();
				Crane newCrane = (Crane) crane.loadFromMap(railSquare, session);
				rail.setPlaceableOnRail(newCrane);
			}
			railSquare.setPlaceableOnSquare(newRail);

			if (newRail.getSignals() != null) {
				((GameSession) session).registerTickableGameObject(newRail.getSignals());
			}
			generatedRails.add(newRail);
		}

		Iterator<Player> playerIterator = gameSession.getPlayers().iterator();
		int colorCounter = 0;
		// erzeugen der neuen Trainstations auf deren Squares
		for (Square trainstationSquare : trainstationSquaresToCreate) {

			// Alte Trainstation holen und neue Trainstation damit erstellen
			Trainstation oldTrainStation = (Trainstation) trainstationSquare.getPlaceableOnSquare();
			Trainstation newTrainStation;
			
			if (oldTrainStation instanceof Playertrainstation) {
				UUID playerId = null;
				Playertrainstation oldPlayerTrainstation = (Playertrainstation)oldTrainStation;
				Rail oldLocoSpawnPointRail = (Rail) map.getPlaceableOnSquareById(oldPlayerTrainstation.getSpawnPointforLoco());
				if (playerIterator.hasNext()) {
					GamePlayer player = (GamePlayer)playerIterator.next();
					// setzt die colorCount für einen Player
					Color color = Color.values()[colorCounter%4];
					player.setColor(color);
					playerId = player.getId();
					generateLoco(gameSession, playerId, oldPlayerTrainstation, oldLocoSpawnPointRail.getSquareFromGameSession());
				}
				// setzen der PlayerId dem alten PlayerTrainstation, damit der neue PlayerTrainstation sich diese im loadFromMap nehmen kann,
				// ohne das man die Struktur des Interfaces, woraus das loadFromMap kommt, verändert
				oldPlayerTrainstation.setPlayerId(playerId);
				
				// Trainstation ist eine PlayerTrainstation
				Playertrainstation newPlayerTrainStation = (Playertrainstation) oldPlayerTrainstation.loadFromMap(trainstationSquare, gameSession);
				Rail rail = (Rail) map.getPlaceableOnSquareById(newPlayerTrainStation.getSpawnPointforLoco());
				//Square locoSpawnPointSquare = rail.getSquareFromGameSession();
				newTrainStation = newPlayerTrainStation;
				
				// setzt die farbe des naechsten spieler eins weiter
				colorCounter += 1;
			} else {
				// Trainstation ist eine PublicTrainstation
				newTrainStation = (Publictrainstation) oldTrainStation.loadFromMap(trainstationSquare, gameSession);
			}
			// Neue Trainstation auf Square setzen
			trainstationSquare.setPlaceableOnSquare(newTrainStation);
			
			
		}

		// generiert an den erzeugenten rails resourcen
		for (Rail generatedRail : generatedRails) {
			generatedRail.generateResourcesNextToRail();
		}

		gameSession.start();
	}
	
	private void generateLoco(GameSession gameSession, UUID playerId, Playertrainstation trainstation, Square locoSpawnPointSquare) {
		// Loco wird erstellt und zur Liste der Locos hinzugefügt
		
		gameSession.addLoco(new PlayerLoco(gameSession.getSessionName(), locoSpawnPointSquare, playerId,
				getLocoDirectionbyTrainstation(trainstation.getAlignment())));
		GhostLoco ghostLoco = new GhostLoco(gameSession.getSessionName(), locoSpawnPointSquare, playerId,
				getLocoDirectionbyTrainstation(trainstation.getAlignment()));
		gameSession.addLoco(ghostLoco);
	}

	private Compass getLocoDirectionbyTrainstation(Compass compass) {
		switch (compass) {
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
