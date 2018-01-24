package commands.base;

import communication.MessageInformation;
import models.game.Square;
import models.game.Trainstation;
import models.session.EditorSession;
import models.session.RoRSession;

public class MoveTrainstationCommandBase extends CommandBase {

	private Square oldSquare;
	private Square newSquare;
	
	public MoveTrainstationCommandBase(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		EditorSession editorSession = (EditorSession) session;
		Trainstation trainstation = (Trainstation)editorSession.getMap().getPlaceableOnSquareById(messageInfo.getValueAsUUID("id"));
		int newXPos = messageInfo.getValueAsInt("newXPos");
		int newYPos = messageInfo.getValueAsInt("newYPos");
		this.oldSquare = editorSession.getMap().getSquareById(trainstation.getSquareId());
		this.newSquare = editorSession.getMap().getSquare(newXPos, newYPos);
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		editorSession.getMap().movePlaceableOnSquare(oldSquare, newSquare);
	}
}
