package models.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import communication.MessageInformation;
import communication.topic.TopicMessageQueue;

public class MapTests {
	@Test
	public void MapChangeName_ChangesName() {
		String newName = "NewName";
		Map map = new Map("testSession", 50);
		map.changeName(newName);
		
		assertEquals(newName, map.getName());
	}
	
	@Test
	public void MapChangeName_NotifiesCorrectValues() {
		String newName = "NewName";
		Map map = new Map("testSession", 50);
		map.changeName(newName);
		
		MessageInformation messageInfo = TopicMessageQueue.getInstance().getFirstFoundMessageInformationForMessageType("UpdateNameOfMap");
		assertEquals(newName, messageInfo.getValueAsString("mapName"));
	}
}
