package commands.game;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.session.RoRSession;

public class LoadMapCommand extends CommandBase {

	public LoadMapCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

	}

	@Override
	public void execute() {
		System.out.print("Ich soll eine DefaultMap laden!");
	}

}
