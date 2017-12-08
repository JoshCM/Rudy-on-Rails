package communication.dispatcher;

import commands.base.Command;
import communication.MessageInformation;
import models.editor.EditorSession;
import models.editor.RoRSession;
import models.game.Map;
import models.game.Rail;
import models.game.RailSectionPosition;
import models.game.Square;

public class EditorSessionDispatcher extends DispatcherBase {
	private final static String COMMAND_PACKAGE_NAME = "commands.editor.";
	private final static String COMMAND_SUFFIX = "Command";
	
	private EditorSession editorSession;

	public EditorSessionDispatcher(EditorSession editorSession) {
		this.editorSession = editorSession;
	}

	/*
	 * public void dispatch(String request, String message) { RequestSerializer
	 * requestSerializer = RequestSerializer.getInstance(); MessageInformation
	 * messageInformation = requestSerializer.deserialize(message);
	 * 
	 * if(request.equals("CreateRail")) { handleCreateRail(messageInformation); } }
	 */

	@Override
	public void dispatch(String messageType, String message) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		MessageInformation messageInformation = requestSerializer.deserialize(message);

		executeCommandForMessageType(messageType, messageInformation);
	}

	private void executeCommandForMessageType(String messageType, MessageInformation messageInformation) {
		try {
			Class<?> cls = Class.forName(COMMAND_PACKAGE_NAME + messageType + COMMAND_SUFFIX);
			Command command = (Command) cls.getConstructor(RoRSession.class, MessageInformation.class)
					.newInstance(editorSession, messageInformation);
			// Hier könnte man den Command noch irgendwo speichern, wenn man das will
			command.execute();
		} catch (Exception e) {
			// Do something meaningful here
			e.printStackTrace();
		}
	}
}
