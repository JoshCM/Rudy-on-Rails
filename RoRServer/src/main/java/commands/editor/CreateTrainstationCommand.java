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
import models.game.RailSectionPosition;
import models.game.Square;
import models.game.Trainstation;
import models.session.EditorSession;
import models.session.RoRSession;

public class CreateTrainstationCommand extends CommandBase {
	private int xPos;
	private int yPos;
	private final static int TRAINSTATION_MARGIN = 1;

	public CreateTrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
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
			Trainstation trainstation = new Trainstation(session.getName(), trainstationSquare, createTrainstationRails(map, trainstationSquare, trainstationId), trainstationId);
			trainstationSquare.setPlaceable(trainstation);
		}
	}

	private List<Rail> createTrainstationRails(Map map, Square square, UUID trainstationId) {
		List<Rail> trainstationRails = new ArrayList<Rail>();

		// Railsection werden erstellt
		RailSectionPosition railSectionPositionNode1 = RailSectionPosition.NORTH;
		RailSectionPosition railSectionPositionNode2 = RailSectionPosition.SOUTH;
		
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
			Rail rail = new Rail(session.getName(), trainstationRailSquare, railSectionPositionNode1, railSectionPositionNode2);
			trainstationRailSquare.setPlaceable(rail);
			rail.setTrainstationId(trainstationId);
			trainstationRails.add(rail);
		}
		
		return trainstationRails;
	}

	private boolean validate(Map map, Square square) {
		// Square für Trainstation ist vorhanden
		if (square != null) {
			// Square für Trainstation ist belegt
			if (square.getPlaceableOnSquare() != null) {
				return false;
			}
		} else {
			return false;
		}
		// Square für Trainstation ist weit genug weg vom oberen, unteren und rechten
		// Rand
		if (square.getYIndex() > 0 && square.getYIndex() < map.getSquares().length - 1
				&& square.getXIndex() < map.getSquares().length - 1) {
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
		} else {
			return false;
		}
		return true;
	}
}
