package communication.dispatcher;

import models.dataTranserObject.MessageInformation;
import models.editor.EditorSession;
import models.game.Map;
import models.game.Rail;
import models.game.RailSection;
import models.game.RailSectionPosition;
import models.game.Square;

public class EditorSessionDispatcher {
	private EditorSession editorSession;
	
	public EditorSessionDispatcher(EditorSession editorSession) {
		this.editorSession = editorSession;
	}
	
	public void dispatch(String request, String message) {
		RequestSerializer requestSerializer = RequestSerializer.getInstance();
		MessageInformation messageInformation = requestSerializer.deserialize(message);

		if(request.equals("CreateRail")) {
			CreateRail(messageInformation);
		}
	}
	
	private void CreateRail(MessageInformation mi) {
		int xPos = mi.getValueAsInt("xPos");
		int yPos = mi.getValueAsInt("yPos");
		RailSectionPosition railSectionPositionNode1 = RailSectionPosition.valueOf(mi.getValueAsString("railSectionPositionNode1"));
		RailSectionPosition railSectionPositionNode2 = RailSectionPosition.valueOf(mi.getValueAsString("railSectionPositionNode2"));
		
		Map map = editorSession.getMap();
		Square square = map.getSquare(xPos, yPos);
		
		Rail rail = new Rail(square, new RailSection(railSectionPositionNode1, railSectionPositionNode2));
		square.setPlaceable(rail);
	}
}
