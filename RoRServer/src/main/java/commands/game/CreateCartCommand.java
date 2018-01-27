package commands.game;

import java.util.UUID;

import commands.base.Command;
import communication.MessageInformation;
import models.game.Cart;
import models.game.Compass;
import models.game.GamePlayer;
import models.game.Loco;
import models.game.PlayerLoco;
import models.game.Rail;
import models.game.Square;
import models.game.Trainstation;
import models.session.GameSession;
import models.session.RoRSession;
import resources.PropertyManager;

public class CreateCartCommand implements Command{

	private int xPos;
	private int yPos;
	private Compass compass;
	private UUID playerId;
	private UUID trainstationOwnerId;
	protected GameSession session;
	
	/**
	 * yPos, xPos müssen von den Bahnhöfen rausgelesen werden
	 * @param session
	 * @param messageInfo
	 */
	public CreateCartCommand(RoRSession session, MessageInformation messageInfo) {
		this.xPos =  messageInfo.getValueAsInt("posX");
		this.yPos =  messageInfo.getValueAsInt("posY");
		this.compass = Compass.valueOf((String) messageInfo.getValue("compass"));
		this.session = (GameSession)session;
		this.playerId = UUID.fromString(messageInfo.getClientid());
		Trainstation trainstation = (Trainstation)this.session.getMap().getPlaceableOnSquareById(messageInfo.getValueAsUUID("trainstationId"));
		this.trainstationOwnerId = trainstation.getPlayerId();
		
	}
	@Override
	public void execute() {
		Square cartSpawnPointSquare = this.session.getMap().getSquare(xPos, yPos);
		Rail cartSpawnPointRail = (Rail) cartSpawnPointSquare.getPlaceableOnSquare();
		Loco loco = session.getLocomotiveByPlayerId(playerId);
		GamePlayer currentPlayer = (GamePlayer)session.getPlayerById(playerId);
		
		if(validateBuyCart(currentPlayer, loco, cartSpawnPointRail)) {
			// abziehen des Goldes des Players
			currentPlayer.removeGold(Integer.valueOf(PropertyManager.getProperty("cart_costs")));
			
			// erstellen des Carts und setzzen auf das richtige Rail
			Cart cart = new Cart(session.sessionName, cartSpawnPointSquare, compass, playerId, false, null);
			cartSpawnPointRail.setPlaceableOnRail(cart);
		}
		
	}
	
	private boolean validateBuyCart(GamePlayer currentPlayer, Loco loco, Rail cartSpawnPointRail) {
		// die Loco des Players kann nicht mehr als 5 carts besitzen dürfen
		if(this.trainstationOwnerId.equals(this.playerId)) {
			if(loco.getCarts().size() < Integer.valueOf(PropertyManager.getProperty("max_cart_number"))) {
				// die cartSpawnPointRail muss leer sein
				if(cartSpawnPointRail.getPlaceableOnrail()==null) {
					// der player muss genügend resource(Gold) zur verfügung haben
					if(currentPlayer.getGoldCount() >= Integer.valueOf(PropertyManager.getProperty("cart_costs"))) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean isLocoOrCartOnSquare(Square square, Loco loco) {
		if(loco.getSquareId()== square.getId())
			return true;
		for(Cart c : loco.getCarts())
			if(c.getSquareId()== square.getId())
				return true;
	
		return false;
	}
}
