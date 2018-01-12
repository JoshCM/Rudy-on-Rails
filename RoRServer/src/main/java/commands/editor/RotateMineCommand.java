package commands.editor;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Mine;
import models.game.Rail;
import models.session.EditorSession;
import models.session.RoRSession;

/**
 * Command zum Drehen der Mine im Editor
 * @author Andreas P�hler
 *
 */
public class RotateMineCommand extends CommandBase {
	
	private int xPos;
	private int yPos;
	private boolean rotateRight;
	private UUID mineId;
	private UUID railId;

	public RotateMineCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		mineId = messageInfo.getValueAsUUID("mineId");
		railId = messageInfo.getValueAsUUID("railId");
		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
		rotateRight = messageInfo.getValueAsBoolean("right");
	}

	@Override
	public void execute() {
		
		EditorSession editorSession = (EditorSession) session;
		Rail rail = (Rail)editorSession.getMap().getPlaceableOnSquareById(railId);
		Mine mine = (Mine)rail.getPlaceableOnRail();
		
		if (rotateRight) {
			mine.rotateRight();
		} else {
			mine.rotateLeft();
		}
	}

}
