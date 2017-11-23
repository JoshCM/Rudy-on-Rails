package launcher;

import communication.Broker.MessageBroker;

public class Main {
	public static void main(String[] args) {
		System.out.println("Broker wird gestartet");
		MessageBroker.getInstance();
		
	}
}
