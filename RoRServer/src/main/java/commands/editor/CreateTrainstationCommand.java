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
	private Rail spawnPointforLoco;

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

		if (!validate(map, trainstationSquare)) {
			throw new InvalidModelOperationException("Trainstation konnte nicht angelegt werden");
		} else {
			// generiere UUID für Trainstation
			UUID trainstationId = UUID.randomUUID();
			// Trainstation wird erzeugt und auf Square gesetzt
			Trainstation trainstation = new Trainstation(session.getSessionName(), trainstationSquare, createTrainstationRails(map, trainstationSquare, trainstationId), trainstationId, alignment);
			trainstation.setSpawnPointforLoco(spawnPointforLoco);
			trainstationSquare.setPlaceable(trainstation);
		}
	}

	private List<UUID> createTrainstationRails(Map map, Square square, UUID trainstationId) {
		List<UUID> trainstationRailIds = new ArrayList<UUID>();

		// Railsection werden erstellt
		Compass railSectionPositionNode1 = Compass.NORTH;
		Compass railSectionPositionNode2 = Compass.SOUTH;
		List<Compass> railSectionPositions = Arrays.asList(railSectionPositionNode1, railSectionPositionNode2);
		
		// Squares für die Rails der Trainstation werden erstellt
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
			Rail rail = new Rail(session.getSessionName(), trainstationRailSquare, railSectionPositions);
			// Mittlere Schiene des Bahnhofs (noch unschoen)
			if (i == 1) {
				spawnPointforLoco = rail;
			}
			trainstationRailSquare.setPlaceable(rail);
			rail.setTrainstationId(trainstationId);
			trainstationRailIds.add(rail.getId());
		}
		
		return trainstationRailIds;
	}
	
	private boolean validate(Map map, Square square) {
		if(!validatePossibleTrainstation(map, square))
			return false;
		if(!validateWindowEdges(map, square))
			return false;
		if(!validatePossibleRails(map, square))
			return false;		
		return true;
	}
	
	private boolean validatePossibleTrainstation(Map map, Square square) {
		if (square != null) {
			if (square.getPlaceableOnSquare() != null) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean validateWindowEdges(Map map, Square square) {
		if(square.getYIndex() == 0)
			return false;
		if(square.getYIndex() == map.getSquares().length - 1)
			return false;
		if(square.getXIndex() == map.getSquares().length - 1)
			return false;
		return true;
	}
	
	private boolean validatePossibleRails(Map map, Square square) {
		// Iteriert über die Squares für die möglichen Rails der Trainstation
		for (int i = -1; i <= 1; i++) {
			Square possibleRailSquare = map.getSquare(square.getXIndex() + 1, square.getYIndex() + i);
			// Square für Rail ist vorhanden
			if (possibleRailSquare != null) {
				// Square für Rail ist belegt
				if (possibleRailSquare.getPlaceableOnSquare() != null) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
}
