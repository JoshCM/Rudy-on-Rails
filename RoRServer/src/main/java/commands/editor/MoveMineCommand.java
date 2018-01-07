package commands.editor;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.NotMoveableException;
import models.game.Map;
import models.game.Mine;
import models.game.Rail;
import models.game.Square;
import models.session.EditorSession;
import models.session.RoRSession;

public class MoveMineCommand extends CommandBase {
	
	private Square oldSquare;
	private Square newSquare;
	private Mine mine;
	private int newXPos;
	private int newYPos;

	public MoveMineCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		
		EditorSession editorSession = (EditorSession) session;
		
		mine = (Mine)editorSession.getMap().getPlaceableById(messageInfo.getValueAsUUID("id"));
		newXPos = messageInfo.getValueAsInt("newXPos");
		newYPos = messageInfo.getValueAsInt("newYPos");
		oldSquare = editorSession.getMap().getSquareById(mine.getSquareId());
		newSquare = editorSession.getMap().getSquare(newXPos, newYPos);
	}

	@Override
	public void execute() {
		System.out.println("MOVE MINE");
		EditorSession editorSession = (EditorSession) session;
		Map map = editorSession.getMap();
		if (newSquare.getPlaceableOnSquare() instanceof Rail) {
			Rail rail = (Rail)oldSquare.getPlaceableOnSquare();
			Rail newRail = (Rail)newSquare.getPlaceableOnSquare();
			newRail.setPlaceableOnRail(mine);			
			map.movePlaceableOnRail(oldSquare, newSquare);
		} else {
			throw new NotMoveableException("Es befindet sich hier ein Rail / Andere Rail auswählen oder Mine löschen");
		}
	}

}
