package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Map;
import models.session.RoRSession;
import persistent.MapManager;

public class SaveNewMapCommand extends CommandBase {
	private String mapName;
	private final static String ext = ".map";
	
	public SaveNewMapCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		
		mapName = messageInfo.getValueAsString("name");
	}

	@Override
	public void execute() {
		Map map = session.getMap();
		map.changeName(mapName);
		
		//setzt die availablePlayerSlots
		map.initAvailablePlayerSlots();
		MapManager.setAvailablePlayerSlotsForMapName(map.getName(), map.getAvailablePlayerSlots());
		
		MapManager.saveMap(map, ext);
	}
}
