package commands.editor;

import commands.base.MoveTrainstationCommandBase;
import communication.MessageInformation;

import models.session.RoRSession;

public class MovePublictrainstationCommand extends MoveTrainstationCommandBase {

	public MovePublictrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
	}

}
