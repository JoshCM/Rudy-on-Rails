package commands.editor;

import java.util.ArrayList;
import java.util.List;
import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.*;
import models.session.EditorSession;
import models.session.RoRSession;

public class CreateCrossingCommand extends CommandBase {
	private int xPos;
	private int yPos;

	public CreateCrossingCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		Map map = editorSession.getMap();
		Square square = map.getSquare(xPos, yPos);
		
		List<Enum> railSectionPositions = new ArrayList<Enum>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);
		railSectionPositions.add(RailSectionStatus.ACTIVE);
		railSectionPositions.add(Compass.EAST);
		railSectionPositions.add(Compass.WEST);
		
		Rail rail = new Rail(session.getDescription(), square, railSectionPositions);
		square.setPlaceableOnSquare(rail);
	}
}
