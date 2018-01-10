package commands.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
import models.game.Stock;
import models.game.Trainstation;
import models.session.EditorSession;
import models.session.RoRSession;

public class DeleteTrainstationCommand extends CommandBase{

	UUID trainstationId;
	UUID stockId;
	int trainstationYPos;
	List<UUID> trainstationRailIds = new ArrayList<UUID>();
	
	public DeleteTrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.trainstationId = messageInfo.getValueAsUUID("trainstationId");
		this.stockId = messageInfo.getValueAsUUID("stockId");
		for(String railIdString : messageInfo.<String>getValueAsObjectList("trainstationRailIds")) {
			UUID railId = UUID.fromString(railIdString);
			trainstationRailIds.add(railId);
		}
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession)session;
		Map map = editorSession.getMap();
		
		// remove trainstation
		Trainstation trainstation = (Trainstation) map.getPlaceableOnSquareById(trainstationId);
		Square trainstationSquare = map.getSquare(trainstation.getXPos(), trainstation.getYPos());
		trainstationSquare.deletePlaceable();
		
		// remove stock
		Stock stock = (Stock) map.getPlaceableOnSquareById(stockId);
		Square stockSquare = map.getSquare(stock.getXPos(), stock.getYPos());
		stockSquare.deletePlaceable();
		
		// remove trainstationRails
		for(UUID trainstationRailId : trainstationRailIds) {
			Rail trainstationRail = (Rail)map.getPlaceableOnSquareById(trainstationRailId);
			Square railSquare = map.getSquare(trainstationRail.getXPos(), trainstationRail.getYPos());
			railSquare.deletePlaceable();
		}
	}

}
