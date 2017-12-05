package launcher;

import communication.broker.MessageBroker;
import HandleRequests.RequestSerializer;

public class Main {
	public static void main(String[] args) {
		MessageBroker messageBroker = MessageBroker.getInstance();
	}
}