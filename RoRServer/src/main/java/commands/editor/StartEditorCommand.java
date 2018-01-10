package commands.editor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import commands.base.CommandBase;
import communication.MessageInformation;
import communication.queue.receiver.QueueReceiver;
import communication.topic.TopicMessageQueue;
import models.game.Map;
import models.game.PlaceableOnSquare;
import models.game.Rail;
import models.game.Square;
import models.game.Stock;
import models.game.Trainstation;
import models.session.EditorSession;
import models.session.GameSession;
import models.session.RoRSession;
import persistent.MapManager;

public class StartEditorCommand extends CommandBase {
	static Logger log = Logger.getLogger(QueueReceiver.class.getName());

	public StartEditorCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
	}

	private void startNewMap() {
		Map map = new Map(session.getName());
		session.setMap(map);
		log.info("create new map");
	}

	private void startLoadedMap(String mapName) {
		Map map = MapManager.loadMap(mapName);
		log.info("loading map: " + mapName);

		map.setSessionNameForMapAndSquares(session.getName());
		map.addObserver(TopicMessageQueue.getInstance());
		session.setMap(map);

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
				square.setName(session.getName());
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

		// erzeugen der neuen Rails auf deren Squares
		for (Square railSquare : railSquaresToCreate) {
			railSquare.setPlaceableOnSquare(railSquare.getPlaceableOnSquare().loadFromMap(railSquare, session));
		}

		// erzeugen der neuen Trainstations auf deren Squares
		for (Square trainstationSquare : trainstationSquaresToCreate) {
			trainstationSquare.setPlaceableOnSquare(
					trainstationSquare.getPlaceableOnSquare().loadFromMap(trainstationSquare, session));
		}
	}

	@Override
	public void execute() {
		String mapName = ((EditorSession) session).getMapName();

		if (mapName.startsWith("#")) {
			// eine neue map wird erstellt
			startNewMap();
		} else {
			// eine map wird geladen
			startLoadedMap(mapName);
		}
		session.start();
	}

}
