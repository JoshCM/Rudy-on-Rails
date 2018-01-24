package commands.editor;

import java.util.UUID;

import commands.base.CreateTrainstationCommandBase;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.game.Map;
import models.game.Rail;
import models.game.Compass;
import models.game.Crane;
import models.game.Square;
import models.game.Stock;
import models.game.Playertrainstation;
import models.helper.Validator;
import models.session.EditorSession;
import models.session.RoRSession;

public class CreatePlayertrainstationCommand extends CreateTrainstationCommandBase {
	private int xPos;
	private int yPos;
	private Compass alignment;
	private Map map;

	public CreatePlayertrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
		alignment = Compass.valueOf(messageInfo.getValueAsString("alignment"));
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		this.map = editorSession.getMap();
		Square newSquare = map.getSquare(xPos, yPos);

		if (!Validator.validateTrainstationOnMap(newSquare, alignment, editorSession.getMap())) {
			throw new InvalidModelOperationException(
					String.format("Trainstation(x:%s,y:%s) konnte nicht angelegt werden", xPos, yPos));
		} else {
			// generiere UUID für Trainstation
			UUID trainstationId = UUID.randomUUID();

			// neuer Stock wird erstellt
			// y-1 da die anfangsausrichtung der trainstation immer EAST ist
			Square stockSquare = map.getSquare(xPos, yPos - 1);
			Stock newStock = new Stock(session.getDescription(), stockSquare, trainstationId, alignment);
			stockSquare.setPlaceableOnSquare(newStock);

			// Trainstation wird erzeugt und auf Square gesetzt
			Playertrainstation trainstation = new Playertrainstation(session.getDescription(), newSquare,
					createTrainstationRails(map, newSquare, trainstationId, alignment), trainstationId, alignment, newStock);
			this.setSpawnPoint(trainstation);
			newSquare.setPlaceableOnSquare(trainstation);

			// der Kran wird erstellt
			Crane crane = createCrane(stockSquare, trainstation, map, alignment);
			trainstation.setCrane(crane);
		}
	}
	
	/**
	 * Sucht das richtige Rail und setze es als Spawnpoint
	 * 
	 * @param trainstation
	 *            Der neu erzeugte Trainstation
	 */
	private void setSpawnPoint(Playertrainstation trainstation) {
		int spawnPointX;
		int spawnPointY;
		switch (trainstation.getAlignment()) {
		case EAST:
			spawnPointX = 1;
			spawnPointY = 0;
		case SOUTH:
			spawnPointX = 0;
			spawnPointY = 1;
		case WEST:
			spawnPointX = -1;
			spawnPointY = 0;
		case NORTH:
			spawnPointX = 0;
			spawnPointY = -1;
		default:
			spawnPointX = 1;
			spawnPointY = 0;
		}

		for (Rail trainstationRail : trainstation.getTrainstationRails()) {
			if (trainstationRail.getXPos() == trainstation.getXPos() + spawnPointX)
				if (trainstationRail.getYPos() == trainstation.getYPos() + spawnPointY)
					trainstation.setSpawnPointforLoco(trainstationRail.getId());
		}
	}
}
