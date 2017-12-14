package models.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import communication.MessageInformation;
import communication.topic.MessageQueue;

public class MapTests {
	@Test
	public void MapChangeName_ChangesName() {
		String newName = "NewName";
		Map map = new Map("testSession");
		map.ChangeName(newName);
		
		assertEquals(newName, map.getName());
	}
	
	@Test
	public void MapChangeName_NotifiesCorrectValues() {
		String newName = "NewName";
		Map map = new Map("testSession");
		map.ChangeName(newName);
		
		MessageInformation messageInfo = MessageQueue.getInstance().getFirstFoundMessageInformationForMessageType("UpdateNameOfMap");
		assertEquals(newName, messageInfo.getValueAsString("mapName"));
	}
}
