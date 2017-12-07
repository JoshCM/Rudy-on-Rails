package launcher;

import communication.broker.MessageBroker;

public class Launcher {
	public static void main(String[] args) {
		MessageBroker messageBroker = MessageBroker.getInstance();
	}
}