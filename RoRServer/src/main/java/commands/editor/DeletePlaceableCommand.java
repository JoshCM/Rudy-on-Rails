package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Map;
import models.game.Square;
import models.session.EditorSession;
import models.session.RoRSession;

public class DeletePlaceableCommand extends CommandBase {
	private int xPos;
	private int yPos;
	
	public DeletePlaceableCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		
		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession)session;
		Map map = editorSession.getMap();
		Square square = map.getSquare(xPos, yPos);
		square.deletePlaceable();
	}
}
