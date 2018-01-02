package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Mine;
import models.game.Rail;
import models.game.Square;
import models.session.EditorSession;
import models.session.RoRSession;

public class CreateMineCommand extends CommandBase {
	
	private int xPos;
	private int yPos;

	public CreateMineCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		Square square = editorSession.getMap().getSquare(xPos, yPos);
		Mine mine = new Mine(editorSession.getName(), square);
		if (square.getPlaceableOnSquare() instanceof Rail) {
			Rail rail = (Rail) square.getPlaceableOnSquare();
			rail.setPlaceableOnRail(mine);
		}
	}

}
