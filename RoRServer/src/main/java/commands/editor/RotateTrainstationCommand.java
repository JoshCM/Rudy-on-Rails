package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.NotRemoveableException;
import models.game.Compass;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
import models.game.Trainstation;
import models.session.EditorSession;
import models.session.RoRSession;

public class RotateTrainstationCommand extends CommandBase{

	Trainstation trainstation;
	boolean right;
	public RotateTrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		EditorSession editorSession = (EditorSession)session;
		Map map = editorSession.getMap();
		right = messageInfo.getValueAsBoolean("right");
		trainstation = (Trainstation) map.getPlaceableById(messageInfo.getValueAsUUID("id"));
	}

	@Override
	public void execute() {
		if(trainstation.validateRotation(right))
			trainstation.rotate(right);
	}

}
