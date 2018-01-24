package commands.editor;

import java.util.UUID;

import commands.base.CreateTrainstationCommandBase;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.game.Map;
import models.game.Compass;
import models.game.Crane;
import models.game.Square;
import models.game.Stock;
import models.game.Publictrainstation;
import models.helper.Validator;
import models.session.EditorSession;
import models.session.RoRSession;

public class CreatePublictrainstationCommand extends CreateTrainstationCommandBase {

	private int xPos;
	private int yPos;
	private Compass alignment;
	private Map map;

	public CreatePublictrainstationCommand(RoRSession session, MessageInformation messageInfo) {
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

		if (!Validator.validateTrainstationOnMap(newSquare,alignment, editorSession.getMap())) {
			throw new InvalidModelOperationException(
					String.format("Trainstation(x:%s,y:%s) konnte nicht angelegt werden", xPos, yPos));
		} else {
			// generiere UUID für Trainstation
			UUID trainstationId = UUID.randomUUID();
			
			// neuer Stock wird erstellt 
			// y-1 da die anfangsausrichtung der trainstation immer EAST ist
			Square stockSquare = map.getSquare(xPos, yPos - 1);
			Stock newStock = new Stock(session.getSessionName(), stockSquare, trainstationId, alignment);
			stockSquare.setPlaceableOnSquare(newStock);
			
			// Trainstation wird erzeugt und auf Square gesetzt
			Publictrainstation trainstation = new Publictrainstation(session.getSessionName(), newSquare, 
					createTrainstationRails(map, newSquare, trainstationId, alignment), trainstationId, alignment, newStock);
			newSquare.setPlaceableOnSquare(trainstation);
			
			//der Kran wird erstellt
			Crane crane = createCrane(stockSquare, trainstation, map, alignment);
			trainstation.setCrane(crane);
		}
	}
}
