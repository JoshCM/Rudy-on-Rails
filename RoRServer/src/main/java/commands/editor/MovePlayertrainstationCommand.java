package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Square;
import models.game.Playertrainstation;
import models.session.EditorSession;
import models.session.RoRSession;

public class MovePlayertrainstationCommand extends CommandBase{

	private Square oldSquare;
	private Square newSquare;
	
	public MovePlayertrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		EditorSession editorSession = (EditorSession) session;
		Playertrainstation trainstation = (Playertrainstation)editorSession.getMap().getPlaceableOnSquareById(messageInfo.getValueAsUUID("id"));
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
