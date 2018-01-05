package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.game.Mine;
import models.game.Rail;
import models.game.Square;
import models.session.EditorSession;
import models.session.RoRSession;

public class CreateMineCommand extends CommandBase {
	
	private int xPos;
	private int yPos;

	public CreateMineCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
	}

	@Override
	public void execute() {
		
		EditorSession editorSession = (EditorSession) session;
		Square square = editorSession.getMap().getSquare(xPos, yPos);
		
		// Hier prüfen wir, ob auf dem Square ein Rail liegt, ansonsten wird auf dem Client keine Mine erstellt
		if (square.getPlaceableOnSquare() instanceof Rail) {
			Rail rail = (Rail) square.getPlaceableOnSquare();
			if (rail.getPlaceableOnrail() == null) {
				Mine mine = new Mine(editorSession.getName(), square);
				rail.setPlaceableOnRail(mine);
			} else {
				throw new InvalidModelOperationException("Auf dem Rail liegt schon etwas");
			}
		} else {
			throw new InvalidModelOperationException("Auf dem Square befindet sich kein Rail");
		}
	}

}
