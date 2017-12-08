package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.editor.EditorSession;
import models.editor.RoRSession;
import models.game.Map;
import models.game.Rail;
import models.game.RailSectionPosition;
import models.game.Square;

public class CreateRailCommand extends CommandBase {
	private int xPos;
	private int yPos;
	private RailSectionPosition railSectionPositionNode1;
	private RailSectionPosition railSectionPositionNode2;
	
	public CreateRailCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		
		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
		railSectionPositionNode1 = RailSectionPosition.valueOf(messageInfo.getValueAsString("railSectionPositionNode1"));
		railSectionPositionNode2 = RailSectionPosition.valueOf(messageInfo.getValueAsString("railSectionPositionNode2"));
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession)session;
		Map map = editorSession.getMap();
		Square square = map.getSquare(xPos, yPos);
		Rail rail = new Rail(square, railSectionPositionNode1, railSectionPositionNode2);
		square.setPlaceable(rail);
	}
}
