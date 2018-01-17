package commands.editor;

import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.NotRemoveableException;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
import models.session.EditorSession;
import models.session.RoRSession;

public class DeletePlaceableCommand extends CommandBase {
	private int xPos;
	private int yPos;
	private UUID emptyUUID = new UUID(0, 0);

	public DeletePlaceableCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		Map map = editorSession.getMap();
		Square square = map.getSquare(xPos, yPos);

		boolean deleteable = true;

		// überprüft ob placeableOnSquare ein Rail ist
		if (square.getPlaceableOnSquare() instanceof Rail) {
			Rail rail = (Rail) square.getPlaceableOnSquare();

			// wenn die Rail einen TrainstationId hat
			if (!rail.getTrainstationId().equals(emptyUUID)) {
				deleteable = false;
			}
		}

		if (deleteable) {
			square.deletePlaceable();
		} else {
			throw new NotRemoveableException(
					String.format("%s(Id:%s)%s", 
							square.getPlaceableOnSquare().getClass().getName(),
							square.getPlaceableOnSquare().getId(), 
							" kann nicht entfernt werden"));
		}
	}
}
