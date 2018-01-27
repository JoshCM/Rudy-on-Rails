package commands.game;

import java.util.List;
import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Cart;
import models.game.Coal;
import models.game.Gold;
import models.game.Loco;
import models.game.Map;
import models.game.Publictrainstation;
import models.session.GameSession;
import models.session.RoRSession;

public class ExchangeCoalToGoldCommand extends CommandBase {
	
	private UUID playerId;
	private UUID trainstationId;

	public ExchangeCoalToGoldCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.playerId = messageInfo.getValueAsUUID("playerId");
		this.trainstationId = messageInfo.getValueAsUUID("trainstationId");
	}

	@Override
	public void execute() {
        GameSession gameSession = (GameSession)session;
        Map map = gameSession.getMap();
        Publictrainstation trainstation = (Publictrainstation)map.getPlaceableOnSquareById(trainstationId);
        Loco loco = gameSession.getLocomotiveByPlayerId(playerId);
        
        List<Cart> carts = loco.getCarts();
        
        for (Cart c : carts) {
        	if (c.getResource() instanceof Coal) {
            	Coal coal = (Coal) c.getResource();
        		Gold gold = trainstation.exchangeCoalToGold(coal);
        		c.removeResourceFromCart();
        		c.loadResourceOntoCart(gold);
        	}
        }
	}

}
