package models.scripts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import communication.MessageInformation;
import communication.topic.TopicMessageQueue;
import models.scripts.Script.ScriptType;


public class ScriptTests {
	@Before
	public void init() {
		TopicMessageQueue.getInstance().clear();
	}
	
	@Test
	public void Script_CreatesMessageWithRightValues() {		
		String description = "TestScript";
		ScriptType scriptType = ScriptType.GHOSTLOCO;
		String filename = "TestScript_Filename";
		UUID playerId = UUID.randomUUID();
		Script script = new Script("TestSession", description, scriptType, filename, playerId);
		
		MessageInformation messageInfo = TopicMessageQueue.getInstance()
				.getFirstFoundMessageInformationForMessageType("CreateScript");
		
		UUID messageInfoScriptId = messageInfo.getValueAsUUID("id");
		UUID messageInfoPlayerId = messageInfo.getValueAsUUID("playerId");
		String messageInfoFilename = messageInfo.getValueAsString("filename");
		String messageInfoDescription = messageInfo.getValueAsString("description");
		String messageInfoScriptType = messageInfo.getValueAsString("scriptType");
		
		assertEquals(description, messageInfoDescription);
		assertEquals(filename, messageInfoFilename);
		assertEquals(ScriptType.GHOSTLOCO.toString(), messageInfoScriptType);
		assertEquals(playerId, messageInfoPlayerId);
		assertNotNull(messageInfoScriptId);
	}
}
