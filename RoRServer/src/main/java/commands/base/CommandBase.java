package commands.base;

import communication.MessageInformation;
import models.session.RoRSession;

public abstract class CommandBase implements Command {
	protected RoRSession session;

	public CommandBase(RoRSession session, MessageInformation messageInfo) {
		this.session = session;
	}
}
