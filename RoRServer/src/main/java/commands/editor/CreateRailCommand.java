package commands.editor;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Map;
import models.game.Rail;
import models.game.RailSectionPosition;
import models.game.Square;
import models.session.EditorSession;
import models.session.RoRSession;

public class CreateRailCommand extends CommandBase {
	private int xPos;
	private int yPos;
	private List<JsonObject> railSectionData;

	public CreateRailCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
		railSectionData = messageInfo.getValueAsList("railSections");
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		Map map = editorSession.getMap();
		Square square = map.getSquare(xPos, yPos);
		RailSectionPosition node1 = RailSectionPosition.valueOf(railSectionData.get(0).get("node1").getAsString());
		RailSectionPosition node2 = RailSectionPosition.valueOf(railSectionData.get(0).get("node2").getAsString());
		Rail rail = new Rail(session.getName(), square, node1, node2);
		square.setPlaceable(rail);
	}
}
