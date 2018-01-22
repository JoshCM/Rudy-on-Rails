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

public class MoveCraneCommand implements Command{
	
	private int xPos;
	private int yPos;
	private UUID stockId;
	private Stock stock;
	private Trainstation trainstation;
	private Crane crane;
	protected GameSession session;
	
	public MoveCraneCommand(RoRSession session, MessageInformation messageInfo) { 

		this.xPos =  messageInfo.getValueAsInt("posX");
		this.yPos =  messageInfo.getValueAsInt("posY");
		this.session = (GameSession)session;
		this.stock = (Stock)session.getMap().getPlaceableOnSquareById(messageInfo.getValueAsUUID("stockId"));
		this.trainstation = (Trainstation)session.getMap().getPlaceableOnSquareById(this.stock.getTrainstationId());
		
		this.crane = this.trainstation.getCrane();
		
	}

	@Override
	public void execute() {
		
		this.crane.moveToTakeTheGoods();
		System.out.println("I'm moving for gooood(Crane)");
		
	}

}
