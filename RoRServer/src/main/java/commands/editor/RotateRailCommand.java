package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.NotRotateableException;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
import models.session.EditorSession;
import models.session.RoRSession;

public class RotateRailCommand extends CommandBase {
	private int xPos;
	private int yPos;
	private boolean right;
	
	public RotateRailCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		
		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
		right = messageInfo.getValueAsBoolean("right");
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession)session;
		Map map = editorSession.getMap();
		Square square = map.getSquare(xPos, yPos);
		
		boolean rotateable = true;
		Rail rail = (Rail)square.getPlaceableOnSquare();

		// wenn die Rail einen TrainstationId hat
		if (rail.getTrainstationId() != null) {
			rotateable = false;
		}

		if (rotateable) {
			rail.rotate(right);
		} else {
			throw new NotRotateableException(
					String.format("%s(Id:%s)%s", 
							square.getPlaceableOnSquare().getClass().getName(),
							square.getPlaceableOnSquare().getUUID(),
							" kann nicht rotiert werden"));
		}
	}
}
