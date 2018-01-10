package commands.editor;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Rail;
import models.session.RoRSession;

public class DeleteMineCommand extends CommandBase {
	
	private UUID railId;
	private UUID mineId;
	private int xPos;
	private int yPos;

	public DeleteMineCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
		railId = messageInfo.getValueAsUUID("railId");
		mineId = messageInfo.getValueAsUUID("mineId");
	}

	@Override
	public void execute() {
		Rail rail = (Rail)session.getMap().getPlaceableById(railId);
		rail.deletePlaceableOnRail();
	}

}
