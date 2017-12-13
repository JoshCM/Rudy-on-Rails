package communication.dispatcher;

import commands.CommandCreator;
import commands.base.Command;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.session.RoRSession;

public abstract class SessionDispatcher extends DispatcherBase {
	
	private final static String COMMAND_SUFFIX = "Command";
	private String commandPackageName;
	private RoRSession roRSession;
	
	public SessionDispatcher(RoRSession roRSession, String commandPackageName) {
		this.roRSession = roRSession;
		this.commandPackageName = commandPackageName;
	}
	
	public void dispatch(String messageType, String message) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		MessageInformation messageInformation = requestSerializer.deserialize(message);

		executeCommandForMessageType(messageType, messageInformation);
	}

	private void executeCommandForMessageType(String messageType, MessageInformation messageInformation) {
		String commandName = commandPackageName + messageType + COMMAND_SUFFIX;
		Command command = null;
		
		try {
			command = CommandCreator.createCommandForName(commandName, roRSession, messageInformation);
			command.execute();
		} catch (InvalidModelOperationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}	
