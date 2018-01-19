package commands.editor;

import commands.base.MoveTrainstationCommandBase;
import communication.MessageInformation;

import models.session.RoRSession;

public class MovePlayertrainstationCommand extends MoveTrainstationCommandBase {

	public MovePlayertrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
	}

}
