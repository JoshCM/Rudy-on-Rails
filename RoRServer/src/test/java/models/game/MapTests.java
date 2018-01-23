package models.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import communication.MessageInformation;
import communication.topic.TopicMessageQueue;

public class MapTests {
	@Test
	public void MapChangeName_ChangesName() {
		String newName = "NewName";
		Map map = new Map("testSession");
		map.changeName(newName);
		
		assertEquals(newName, map.getDescription());
	}
	
	@Test
	public void MapChangeName_NotifiesCorrectValues() {
		String newName = "NewName";
		Map map = new Map("testSession");
		map.changeName(newName);
		
		MessageInformation messageInfo = TopicMessageQueue.getInstance().getFirstFoundMessageInformationForMessageType("UpdateNameOfMap");
		assertEquals(newName, messageInfo.getValueAsString("mapName"));
	}
}
