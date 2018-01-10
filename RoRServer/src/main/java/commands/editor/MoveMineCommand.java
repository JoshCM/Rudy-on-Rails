package commands.editor;
import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.NotMoveableException;
import models.game.Map;
import models.game.Mine;
import models.game.Rail;
import models.game.Square;
import models.session.EditorSession;
import models.session.RoRSession;

/**
 * Command zum Verschieben einer Mine im Editor
 * @author apoeh001
 *
 */
public class MoveMineCommand extends CommandBase {
	
	private Square oldSquare;
	private Square newSquare;
	private Mine mine;
	private int newXPos;
	private int newYPos;

	public MoveMineCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		
		EditorSession editorSession = (EditorSession) session;
		mine = (Mine)editorSession.getMap().getPlaceableOnRailById(messageInfo.getValueAsUUID("id"));
		newXPos = messageInfo.getValueAsInt("newXPos");
		newYPos = messageInfo.getValueAsInt("newYPos");
		oldSquare = editorSession.getMap().getSquareById(mine.getSquareId());
		newSquare = editorSession.getMap().getSquare(newXPos, newYPos);
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		Map map = editorSession.getMap();
		// Nur etwas Platzieren, wenn auf dem Square eine Rail liegt
		if (newSquare.getPlaceableOnSquare() instanceof Rail) {
			Rail rail = (Rail)oldSquare.getPlaceableOnSquare();
			Rail newRail = (Rail)newSquare.getPlaceableOnSquare();
			// Nur etwas Platzieren, wenn die Rail eine Gerade ist
			if (newRail.railIsStraight()) {
				newRail.setPlaceableOnRail(mine);
				// Die Mine benötigt die neue Square ID und die neue Rail ID
				mine.setSquareId(newSquare.getId());
				mine.setRailId(newRail.getId());
				rail.setPlaceableOnRail(null);
				map.movePlaceableOnRail(oldSquare, newSquare);
			} else {
				throw new NotMoveableException("Die Rail ist nicht gerade");
			}

		} else {
			throw new NotMoveableException("Es befindet sich hier ein Rail / Andere Rail auswählen oder Mine löschen");
		}
	}

}