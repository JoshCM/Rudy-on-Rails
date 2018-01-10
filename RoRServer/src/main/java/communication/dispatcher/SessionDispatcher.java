package communication.dispatcher;

import commands.base.Action;
import commands.base.CommandHandler;
import commands.base.SessionCommandHandler;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;

public abstract class SessionDispatcher extends DispatcherBase {
	
	private final static String COMMAND_SUFFIX = "Command";
	private String commandPackageName;
	private SessionCommandHandler commandHandler;
	
	public SessionDispatcher(String commandPackageName) {
		this.commandPackageName = commandPackageName;
	}
	
	public void dispatch(String messageType, String message) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		MessageInformation messageInformation = requestSerializer.deserialize(message);
		executeCommandForMessageType(messageType, messageInformation);
	}

	private void executeCommandForMessageType(Action action, MessageInformation messageInformation) {
		try {
		    commandHandler.handle(action, messageInformation);
		} catch (InvalidModelOperationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}	
