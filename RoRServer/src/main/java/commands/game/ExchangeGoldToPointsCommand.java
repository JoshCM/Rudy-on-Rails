package commands.game;

import java.util.List;
import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Cart;
import models.game.Gold;
import models.game.Loco;
import models.game.PointContainer;
import models.game.Publictrainstation;
import models.session.GameSession;
import models.session.RoRSession;

public class ExchangeGoldToPointsCommand extends CommandBase {
	
	private UUID playerId;
	private UUID trainstationId;

	public ExchangeGoldToPointsCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.playerId = messageInfo.getValueAsUUID("playerId");
		this.trainstationId = messageInfo.getValueAsUUID("trainstationId");
	}

	@Override
	public void execute() {
        GameSession gameSession = (GameSession)session;
        Publictrainstation trainstation = gameSession.getPublictrainstationById(trainstationId);
        Loco loco = gameSession.getLocomotiveByPlayerId(playerId);
        
        List<Cart> carts = loco.getCarts();
        
        for (Cart c : carts) {
        	if (c.getResource() instanceof Gold) {
            	Gold gold = (Gold) c.getResource();
        		PointContainer points = trainstation.exchangeGoldToPoints(gold);
        		c.removeResourceFromCart();
        		c.loadResourceOntoCart(points);
        	}
        }
	}

}
