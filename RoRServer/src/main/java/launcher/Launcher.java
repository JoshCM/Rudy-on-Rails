package launcher;

import communication.broker.MessageBroker;

/**
 * Der Launcher startet den BrokerService und somit die gesamte Anwendung
 */
public class Launcher {
	public static void main(String[] args) {
		MessageBroker messageBroker = MessageBroker.getInstance();
	}
}