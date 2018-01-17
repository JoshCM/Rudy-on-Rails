package commands.editor;

import java.util.ArrayList;
import java.util.List;
import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Compass;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
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
		
		List<Compass> railSectionPositions = new ArrayList<Compass>();
		railSectionPositions.add(Compass.NORTH);
		railSectionPositions.add(Compass.SOUTH);
		railSectionPositions.add(Compass.EAST);
		railSectionPositions.add(Compass.WEST);
		
		Rail rail = new Rail(session.getName(), square, railSectionPositions);
		square.setPlaceableOnSquare(rail);
	}
}
