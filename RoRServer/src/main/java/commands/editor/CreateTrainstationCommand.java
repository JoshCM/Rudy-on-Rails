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
import models.session.EditorSession;
import models.session.RoRSession;

public class CreateTrainstationCommand extends CommandBase {
	private int xPos;
	private int yPos;
	private Compass alignment;
	private final static int TRAINSTATION_MARGIN = 1;

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
		Square trainstationSquare = map.getSquare(xPos, yPos);

		if (!map.validateSetTrainstationOnMap(trainstationSquare, alignment)) {
			throw new InvalidModelOperationException(String.format("Trainstation(x:%s,y:%s) konnte nicht angelegt werden", xPos, yPos));
		} else {
			// generiere UUID für Trainstation
			UUID trainstationId = UUID.randomUUID();
			// Trainstation wird erzeugt und auf Square gesetzt
			Trainstation trainstation = new Trainstation(session.getName(), trainstationSquare, createTrainstationRails(map, trainstationSquare, trainstationId), trainstationId, alignment);
			trainstationSquare.setPlaceableOnSquare(trainstation);
		}
	}

	private List<UUID> createTrainstationRails(Map map, Square square, UUID trainstationId) {
		List<UUID> trainstationRailIds = new ArrayList<UUID>();

		// Railsection werden erstellt
		Compass railSectionPositionNode1 = Compass.NORTH;
		Compass railSectionPositionNode2 = Compass.SOUTH;
		List<Compass> railSectionPositions = Arrays.asList(railSectionPositionNode1, railSectionPositionNode2);
		
		// Squares für die Rails der Trainstation werden gefunden
		Square squareTop = map.getSquare(square.getXIndex() + TRAINSTATION_MARGIN,
				square.getYIndex() - TRAINSTATION_MARGIN);
		Square squareMid = map.getSquare(square.getXIndex() + TRAINSTATION_MARGIN, square.getYIndex());
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
