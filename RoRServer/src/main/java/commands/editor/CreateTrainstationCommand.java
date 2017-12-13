package commands.editor;

import java.util.ArrayList;
import java.util.List;

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

	public CreateTrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		Map map = editorSession.getMap();
		Square square = map.getSquare(xPos, yPos);

		if (!validate(map, square)) {
			throw new InvalidModelOperationException("Trainstation konnte nicht angelegt werden");
		} else {
			// TODO: das geht noch schöner (besser ausgelagert)
			// Railsection und Squares werden als Variable gehalten
			RailSectionPosition railSectionPositionNode1 = RailSectionPosition.NORTH;
			RailSectionPosition railSectionPositionNode2 = RailSectionPosition.SOUTH;
			Square squareTop = map.getSquare(square.getXIndex() + 1, square.getYIndex() - 1);
			Square squareMid = map.getSquare(square.getXIndex() + 1, square.getYIndex());
			Square squareBottom = map.getSquare(square.getXIndex() + 1, square.getYIndex() + 1);

			// Rails werden erstellt und auf die jeweiligen Squares gesetzt
			Rail railTop = new Rail(session.getName(), squareTop, railSectionPositionNode1, railSectionPositionNode2);
			squareTop.setPlaceable(railTop);
			Rail railMid = new Rail(session.getName(), squareMid, railSectionPositionNode1, railSectionPositionNode2);
			squareMid.setPlaceable(railMid);
			Rail railBottom = new Rail(session.getName(), squareBottom, railSectionPositionNode1,
					railSectionPositionNode2);
			squareBottom.setPlaceable(railBottom);

			// Liste der Rails wird gefüllt
			List<Rail> trainstationRails = new ArrayList<Rail>();
			trainstationRails.add(railTop);
			trainstationRails.add(railMid);
			trainstationRails.add(railBottom);
			
			// Trainstation wird erzeugt und auf Square gesetzt
			Trainstation trainstation = new Trainstation(session.getName(), square, trainstationRails);
			square.setPlaceable(trainstation);
		}
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
		// Square für Trainstation ist weit genug weg vom oberen, unteren und rechten Rand
		if (square.getYIndex() > 0 && square.getYIndex() < map.getSquares().length - 1 && square.getXIndex() < map.getSquares().length - 1) {
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
		}else {
			return false;
		}
		return true;
	}
}
