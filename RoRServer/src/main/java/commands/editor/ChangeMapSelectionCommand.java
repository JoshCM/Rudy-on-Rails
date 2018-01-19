package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.session.EditorSession;
import models.session.RoRSession;

/**
 * Setzt den neuen MapName der GameSession
 */
public class ChangeMapSelectionCommand extends CommandBase {

	private String mapName;

	public ChangeMapSelectionCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.mapName = messageInfo.getValueAsString("mapName");
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		editorSession.setMapName(mapName);
	}
}
