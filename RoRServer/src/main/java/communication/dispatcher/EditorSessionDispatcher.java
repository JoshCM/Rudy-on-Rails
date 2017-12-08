package communication.dispatcher;

import commands.CommandCreator;
import commands.base.Command;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.editor.EditorSession;

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

	/**
	 * Erzeugt einen neuen Command für den hereingegebenen messageType und führt ihn aus
	 * @param messageType
	 * @param messageInformation
	 */
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
