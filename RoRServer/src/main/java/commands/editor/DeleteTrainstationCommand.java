package commands.editor;

import java.util.List;
import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.session.RoRSession;

public class DeleteTrainstationCommand extends CommandBase{

	int trainstationXPos;
	int trainstationYPos;
	List<UUID> trainstationRailIds;
	
	public DeleteTrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.trainstationXPos = messageInfo.getValueAsInt("xPos");
		this.trainstationYPos = messageInfo.getValueAsInt("yPos");
		//this.trainstationRailIds = messageInfo
	}

	@Override
	public void execute() {
		
	}

}
