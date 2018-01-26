package commands.game;

import java.util.UUID;

import commands.base.Command;
import communication.MessageInformation;
import models.game.Compass;
import models.game.Crane;
import models.game.Stock;
import models.game.Trainstation;
import models.session.GameSession;
import models.session.RoRSession;

public class UpdateCranePositionCommand implements Command{
	
	private int xPos;
	private int yPos;
	private UUID stockId;
	private Stock stock;
	private Trainstation trainstation;
	private Crane crane;
	private UUID playerId;
	protected GameSession session;
	
	public UpdateCranePositionCommand(RoRSession session, MessageInformation messageInfo) { 

		this.xPos =  messageInfo.getValueAsInt("posX");
		this.yPos =  messageInfo.getValueAsInt("posY");
		this.session = (GameSession)session;
		this.stock = (Stock)session.getMap().getPlaceableOnSquareById(messageInfo.getValueAsUUID("stockId"));
		this.trainstation = (Trainstation)session.getMap().getPlaceableOnSquareById(this.stock.getTrainstationId());
		this.playerId = UUID.fromString(messageInfo.getClientid());
		this.crane = this.trainstation.getCrane();
		
	}

	@Override
	public void execute() {
		
		if(this.playerId.equals(this.trainstation.getPlayerId())) {
			
			this.crane.moveToTakeTheGoods(session.getLocomotiveByPlayerId(playerId),this.trainstation);
		}
	}

}
