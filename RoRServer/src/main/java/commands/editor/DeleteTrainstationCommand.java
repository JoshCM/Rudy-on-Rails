package commands.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
import models.session.EditorSession;
import models.session.RoRSession;

public class DeleteTrainstationCommand extends CommandBase{

	int trainstationXPos;
	int trainstationYPos;
	List<UUID> trainstationRailIds = new ArrayList<UUID>();
	
	public DeleteTrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.trainstationXPos = messageInfo.getValueAsInt("xPos");
		this.trainstationYPos = messageInfo.getValueAsInt("yPos");
		for(String railIdString : messageInfo.<String>getValueAsObjectList("trainstationRailIds")) {
			UUID railId = UUID.fromString(railIdString);
			trainstationRailIds.add(railId);
		}
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession)session;
		Map map = editorSession.getMap();
		
		// remove trainstation
		Square trainstationSquare = map.getSquare(trainstationXPos, trainstationYPos);
		trainstationSquare.deletePlaceable();
		
		// remove trainstationRails
		for(UUID trainstationRailId : trainstationRailIds) {
			Rail trainstationRail = (Rail)map.getPlaceableById(trainstationRailId);
			Square railSquare = map.getSquare(trainstationRail.getXPos(), trainstationRail.getYPos());
			railSquare.deletePlaceable();
		}
	}

}
