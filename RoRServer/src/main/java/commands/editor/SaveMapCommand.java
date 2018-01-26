package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Map;
import models.session.RoRSession;
import persistent.MapManager;

public class SaveMapCommand extends CommandBase {
	private final static String ext = ".map";
	
	public SaveMapCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
	}

	@Override
	public void execute() {
		Map map = session.getMap();
		
		//setzt die availablePlayerSlots
		map.initAvailablePlayerSlots();
		MapManager.setAvailablePlayerSlotsForMapName(map.getName(), map.getAvailablePlayerSlots());
		
		MapManager.saveMap(map, ext);
	}
}
