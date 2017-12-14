package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Map;
import models.session.RoRSession;
import persistent.MapManager;

public class SaveMapCommand extends CommandBase {
	public SaveMapCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
	}

	@Override
	public void execute() {
		Map map = session.getMap();
		MapManager.saveMap(map);
	}
}
