package launcher;

import communication.broker.MessageBroker;

public class Main {
	public static void main(String[] args) {
		System.out.println("Broker wird gestartet");
		MessageBroker.getInstance();
		
	}
}
