package communication.dispatcher;

import java.lang.reflect.InvocationTargetException;

import commands.CommandCreator;
import commands.base.Command;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
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

	@Override
	public void dispatch(String messageType, String message) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		MessageInformation messageInformation = requestSerializer.deserialize(message);

		executeCommandForMessageType(messageType, messageInformation);
	}

	private void executeCommandForMessageType(String messageType, MessageInformation messageInformation) {
		String commandName = COMMAND_PACKAGE_NAME + messageType + COMMAND_SUFFIX;
		Command command = null;
		
		try {
			command = CommandCreator.createCommandForName(commandName, editorSession, messageInformation);
			command.execute();
		} catch (InvalidModelOperationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
