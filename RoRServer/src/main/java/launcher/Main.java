package launcher;

import communication.broker.MessageBroker;
import handleRequests.RequestSerializer;

public class Main {
	public static void main(String[] args) {
		System.out.println("Broker wird gestartet");
		testThings();
		MessageBroker.getInstance();
	}

	public static void testThings() {
        System.out.println("Hier");
        String msg = "{\"clientId\": \"12345\", \"request\": \"PLAYER\", \"attributes\": {\"Playername\": \"Joendhard Biffel\"}}";
		RequestSerializer rs = RequestSerializer.getInstance();
		rs.deserialize(msg);
		//System.out.println(ri.toString());
	}

}