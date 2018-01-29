package commands.game;

import java.util.List;
import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.game.Cart;
import models.game.GamePlayer;
import models.game.Gold;
import models.game.Loco;
import models.game.Map;
import models.game.PointContainer;
import models.game.Publictrainstation;
import models.game.Square;
import models.helper.Validator;
import models.session.GameSession;
import models.session.RoRSession;

public class ExchangeGoldToPointsCommand extends CommandBase {
	
	private UUID playerId;

	public ExchangeGoldToPointsCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
		this.playerId = messageInfo.getValueAsUUID("playerId");
	}

	@Override
	public void execute() {
		GameSession gameSession = (GameSession)session;
        Map map = gameSession.getMap();

        Loco loco = gameSession.getPlayerLocoByPlayerId(playerId);
        GamePlayer player = (GamePlayer)gameSession.getPlayerById(playerId);
        
        if (player.getCurrentSelectedPublictrainstation() != null) {
            Publictrainstation publictrainstation = player.getCurrentSelectedPublictrainstation();
            Square publicTrainstationSquare = map.getSquareById(publictrainstation.getSquareId());

    		if (Validator.checkLocoInRangeOfSquare(map, loco, publicTrainstationSquare, Publictrainstation.TRADE_DISTANCE)){
		        
		        List<Cart> carts = loco.getCarts();
		        
		        for (Cart c : carts) {
		        	if (c.getResource() instanceof Gold) {
		            	Gold gold = (Gold) c.getResource();
		        		PointContainer points = publictrainstation.exchangeGoldToPoints(gold);
		        		c.removeResourceFromCart();
		        		c.loadResourceOntoCart(points);
		        	}
		        }
    		} else {
    			throw new InvalidModelOperationException("Loco steht nicht vor Publictrainstation.");
    		}
        } else {
        	throw new InvalidModelOperationException("Keine Publictrainstation ausgewählt!");
        }
	}

}
