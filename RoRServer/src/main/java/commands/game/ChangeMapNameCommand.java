package commands.game;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.session.RoRSession;

//TODO: Muss den Neuen Name angeben, der dann ein TopicUpdate macht
public class ChangeMapNameCommand extends CommandBase{

	public ChangeMapNameCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
