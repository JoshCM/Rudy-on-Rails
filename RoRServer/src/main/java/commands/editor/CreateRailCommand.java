package commands.editor;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.*;
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
		railSectionData = messageInfo.getValueAsList("railSectionList");
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		Map map = editorSession.getMap();
		Square square = map.getSquare(xPos, yPos);
		
		List<Enum> railSectionPositions = new ArrayList<Enum>();
		for(JsonObject json : railSectionData) {
			Compass node1 = Compass.valueOf(json.get("node1").getAsString());
			Compass node2 = Compass.valueOf(json.get("node2").getAsString());
			railSectionPositions.add(node1);
			railSectionPositions.add(node2);
			railSectionPositions.add(RailSectionStatus.ACTIVE);
		}
		
		Rail rail = new Rail(session.getName(), square, railSectionPositions);
		square.setPlaceableOnSquare(rail);
	}
}
