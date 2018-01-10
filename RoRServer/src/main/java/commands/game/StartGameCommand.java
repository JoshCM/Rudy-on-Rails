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
import models.game.Loco;
import models.game.Map;
import models.game.Mine;
import models.game.Player;
import models.game.Rail;
import models.game.Square;
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
		
		log.info("loading map: " + gameSession.getMapName());
		// Map laden
		Map map = MapManager.loadMap(gameSession.getMapName());
		map.setSessionNameForMapAndSquares(gameSession.getName());
		map.addObserver(TopicMessageQueue.getInstance());
		gameSession.setMap(map);

		// hier müssen die Rails zuerst erstellt werden, danach die Trainstations
		// da die Trainstations die Referenzen der noch nicht vorhandenen Rails an den
		// Client schicken würden
		List<Square> railSquaresToCreate = new ArrayList<Square>();
		List<Square> trainstationSquaresToCreate = new ArrayList<Square>();
		List<Square> mineSquaresToCreate = new ArrayList<Square>();
		
		// Jedes Square durchgehen
		Square[][] squares = map.getSquares();
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {

				// Square holen
				Square square = squares[i][j];
				// square bekommt sessionName und observer
				square.setName(gameSession.getName());
				square.addObserver(TopicMessageQueue.getInstance());

				// Wenn etwas auf dem Square liegt
				if (square.getPlaceableOnSquare() != null) {
					if (square.getPlaceableOnSquare() instanceof Rail) {
						railSquaresToCreate.add(square);
					}
					if (square.getPlaceableOnSquare() instanceof Trainstation)
						trainstationSquaresToCreate.add(square);
				}
			}
		}

		// erzeugen der neuen Rails auf deren Squares
		for (Square railSquare : railSquaresToCreate) {
			Rail rail = (Rail)railSquare.getPlaceableOnSquare();
			Rail newRail = (Rail)railSquare.getPlaceableOnSquare().loadFromMap(railSquare, session);
			// liegt auf einer Rail eine Mine, muss diese darauf erzeugt werden
			if (rail.getPlaceableOnrail() instanceof Mine) {
				Mine mine = (Mine)rail.getPlaceableOnrail();
				Mine newMine = (Mine)mine.loadFromMap(railSquare, session);
				newRail.setPlaceableOnRail(newMine);
			}
			railSquare.setPlaceableOnSquare(newRail);

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
			//Rail rail = (Rail)map.getPlaceableOnSquareById(newTrainStation.getSpawnPointforLoco());
			//Square locoSpawnPointSquare = rail.getSquareFromGameSession();
			Square locoSpawnPointSquare = map.getSquareById(newTrainStation.getSpawnPointforLoco());
			
			// Für jeden Spieler eine Lok erstellen
			if(playerIterator.hasNext()) {
				// Loco wird erstellt und zur Liste der Locos hinzugefügt
				gameSession.addLocomotive(new Loco(gameSession.getName(), locoSpawnPointSquare, playerIterator.next().getId()));
			}
		}

		gameSession.start();
	}
}
