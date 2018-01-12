package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.NotRotateableException;
import models.game.Map;
import models.game.PlayerTrainstation;
import models.session.EditorSession;
import models.session.RoRSession;

public class RotateTrainstationCommand extends CommandBase{

	PlayerTrainstation trainstation;
	boolean right;
	public RotateTrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		EditorSession editorSession = (EditorSession)session;
		Map map = editorSession.getMap();
		right = messageInfo.getValueAsBoolean("right");
		trainstation = (PlayerTrainstation) map.getPlaceableOnSquareById(messageInfo.getValueAsUUID("id"));
	}

	@Override
	public void execute() {
		if(trainstation.validateRotation(right))
			trainstation.rotate(right);
		else
			throw new NotRotateableException(String.format("Trainstation kann nicht rotiert werden(Clockwise:%s)", right));
	}

}
