package communication;

import org.junit.Test;

import communication.dispatcher.FromClientRequestQueueDispatcher;
import communication.dispatcher.RequestSerializer;

public class FromClientRequestQueueDispatcherTests {
	@Test
	public void FromClientRequestQueueDispatcher_handleCreateEditorSession() {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		String messageType = "CreateEditorSession";
		String editorSessionName = "MyEditorSession";
		String playerName = "MyPlayer";
		
		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.putValue("editorName", editorSessionName);
		messageInfo.putValue("playerName", playerName);
		String serializedMessageInfo = requestSerializer.serialize(messageInfo);
		dispatcher.dispatch(messageType, serializedMessageInfo);
	}
}
