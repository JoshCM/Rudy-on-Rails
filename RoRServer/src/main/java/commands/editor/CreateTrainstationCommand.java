package commands.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.game.Map;
import models.game.Rail;
import models.game.Compass;
import models.game.Square;
import models.game.Trainstation;
import models.helper.Validator;
import models.session.EditorSession;
import models.session.RoRSession;

public class CreateTrainstationCommand extends CommandBase {
	private int xPos;
	private int yPos;
	private Compass alignment;
	private final static int TRAINSTATION_MARGIN = 1;
	private Square spawnPointforLoco;

	public CreateTrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
		alignment = Compass.valueOf(messageInfo.getValueAsString("alignment"));
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		Map map = editorSession.getMap();
		Square newSquare = map.getSquare(xPos, yPos);

		if (!Validator.validateTrainstationOnMap(newSquare,alignment, editorSession.getMap())) {
			throw new InvalidModelOperationException(String.format("Trainstation(x:%s,y:%s) konnte nicht angelegt werden", xPos, yPos));
		} else {
			// generiere UUID für Trainstation
			UUID trainstationId = UUID.randomUUID();
			// Trainstation wird erzeugt und auf Square gesetzt
			Trainstation trainstation = new Trainstation(session.getName(), newSquare, createTrainstationRails(map, newSquare, trainstationId), trainstationId, alignment);
			trainstation.setSpawnPointforLoco(spawnPointforLoco);
			newSquare.setPlaceableOnSquare(trainstation);
		}
	}

	private List<UUID> createTrainstationRails(Map map, Square square, UUID trainstationId) {
		List<UUID> trainstationRailIds = new ArrayList<UUID>();

		// Railsection werden erstellt
		Compass railSectionPositionNode1 = Compass.NORTH;
		Compass railSectionPositionNode2 = Compass.SOUTH;
		List<Enum> railSectionPositions = Arrays.asList(railSectionPositionNode1, railSectionPositionNode2);
		
		// Squares für die Rails der Trainstation werden gefunden
		Square squareTop = map.getSquare(square.getXIndex() + TRAINSTATION_MARGIN,
				square.getYIndex() - TRAINSTATION_MARGIN);
		Square squareMid = map.getSquare(square.getXIndex() + TRAINSTATION_MARGIN, square.getYIndex());
		spawnPointforLoco = squareMid;
		Square squareBottom = map.getSquare(square.getXIndex() + TRAINSTATION_MARGIN,
				square.getYIndex() + TRAINSTATION_MARGIN);
		
		// Squares werden in eine Liste geschrieben
		List<Square> trainstationRailSquares = Arrays.asList(squareTop, squareMid, squareBottom);

		// Rails werden erstellt und auf die jeweiligen Squares gesetzt
		for(int i = 0; i < 3; i++) {
			Square trainstationRailSquare = trainstationRailSquares.get(i);
			Rail rail = new Rail(session.getName(), trainstationRailSquare, railSectionPositions);
			rail.setSquareId(trainstationRailSquare.getId());
			trainstationRailSquare.setPlaceableOnSquare(rail);
			rail.setTrainstationId(trainstationId);
			trainstationRailIds.add(rail.getId());
		}
		
		return trainstationRailIds;
	}
}
