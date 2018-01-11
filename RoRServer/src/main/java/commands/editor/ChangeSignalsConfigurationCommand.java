package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Map;
import models.game.Rail;
import models.game.Signals;
import models.game.Square;
import models.session.EditorSession;
import models.session.RoRSession;

public class ChangeSignalsConfigurationCommand extends CommandBase {
	private int xPos;
	private int yPos;
	
	private int autoSwitchIntervalInSeconds;
    private int penalty;
    private int switchCost;
	
	public ChangeSignalsConfigurationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		
		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
		autoSwitchIntervalInSeconds = messageInfo.getValueAsInt("autoSwitchIntervalInSeconds");
		penalty = messageInfo.getValueAsInt("penalty");
		switchCost = messageInfo.getValueAsInt("switchCost");
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		Map map = editorSession.getMap();
		Square square = map.getSquare(xPos, yPos);
		Rail rail = (Rail)square.getPlaceableOnSquare();
		Signals signals = rail.getSignals();
		
		signals.changeConfig(autoSwitchIntervalInSeconds, penalty, switchCost);
	}
}
