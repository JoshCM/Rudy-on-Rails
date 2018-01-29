package commands.game;

import java.util.UUID;

import commands.base.Command;
import communication.MessageInformation;
import models.game.Compass;
import models.game.Crane;
import models.game.GhostLoco;
import models.game.Loco;
import models.game.Playertrainstation;
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
	private Loco loco;
	
	public UpdateCranePositionCommand(RoRSession session, MessageInformation messageInfo) { 

		this.xPos =  messageInfo.getValueAsInt("posX");
		this.yPos =  messageInfo.getValueAsInt("posY");
		this.session = (GameSession)session;
		this.stock = (Stock)session.getMap().getPlaceableOnSquareById(messageInfo.getValueAsUUID("stockId"));
		this.trainstation = (Trainstation)session.getMap().getPlaceableOnSquareById(this.stock.getTrainstationId());
		this.playerId = UUID.fromString(messageInfo.getClientid());
		this.crane = this.trainstation.getCrane();
		this.loco = this.session.getPlayerLocoByPlayerId(playerId);
		
	}

	@Override
	public void execute() {
		
		if (trainstation instanceof Playertrainstation) {
			if(this.playerId.equals(this.trainstation.getPlayerId())) {
				GhostLoco ghostLoco = session.getGhostLocoByPlayerId(playerId);
				if(validatePosition(ghostLoco)) {
					this.crane.moveToTakeTheGoods(ghostLoco, trainstation);
				}
				
				if(validatePosition(loco)) {
					this.crane.moveToTakeTheGoods(loco, trainstation);
				}
			}
		}
	}
	
	private boolean validatePosition(Loco loco) {
		if(loco.getRail().getId().equals(((Playertrainstation)trainstation).getSpawnPointforLoco())) {
			
			if(loco.getSpeed() == 0) {
				return true;
			}
		}
		return false;
	}
}
