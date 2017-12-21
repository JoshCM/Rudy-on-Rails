package communication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Test;
import communication.dispatcher.FromClientRequestQueueDispatcher;
import communication.dispatcher.RequestSerializer;
import helper.MessageQueueStub;
import models.game.Player;
import models.session.EditorSession;
import models.session.EditorSessionManager;

public class FromClientRequestQueueDispatcherTests {
	@Test
	public void FromClientRequestQueueDispatcher_handleCreateEditorSession_createsEditorSessionAndHostPlayer() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.addObserver(messageQueueStub);
		String messageType = "CreateEditorSession";
		String editorSessionName = "TestSession";
		String playerName = "MyPlayer";

		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.putValue("editorName", editorSessionName);
		messageInfo.putValue("playerName", playerName);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleCreateEditorSession(messageInfo);

		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(editorSessionName);
		assertNotNull(editorSession);
		
		Player player = editorSession.getPlayers().get(0);
		assertNotNull(player);
	}

	@Test
	public void FromClientRequestQueueDispatcher_handleCreateEditorSession_createsResponseWithExpectedValues() {
		MessageQueueStub messageQueueStub = new MessageQueueStub();
		FromClientRequestQueueDispatcher dispatcher = new FromClientRequestQueueDispatcher();
		dispatcher.addObserver(messageQueueStub);
		String messageType = "CreateEditorSession";
		String editorSessionName = "TestSession";
		String playerName = "MyPlayer";

		MessageInformation messageInfo = new MessageInformation(messageType);
		messageInfo.putValue("editorName", editorSessionName);
		messageInfo.putValue("playerName", playerName);
		messageInfo.setClientid(UUID.randomUUID().toString());
		dispatcher.handleCreateEditorSession(messageInfo);

		MessageEnvelope messageEnvelope = messageQueueStub.messages.get(0);
		MessageInformation response = messageEnvelope.getMessageInformation();
		assertEquals(messageType, messageEnvelope.getMessageType());
		assertEquals(editorSessionName, response.getValueAsString("editorName"));
		assertEquals(editorSessionName, response.getValueAsString("topicName"));
		assertEquals(playerName, response.getValueAsString("playerName"));
		assertNotNull(response.getValueAsUUID("playerId"));
	}
}
