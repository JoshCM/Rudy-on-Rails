package commands;

import java.lang.reflect.InvocationTargetException;

import commands.base.Command;
import communication.MessageInformation;
import models.session.RoRSession;

public class CommandCreator {
	public static Command createCommandForName(
			String commandName,
			RoRSession session,
			MessageInformation messageInformation
	)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		Class<?> cls = Class.forName(commandName);
		Command command = (Command) cls.getConstructor(RoRSession.class, MessageInformation.class).newInstance(session,
				messageInformation);

		return command;
	}
}
