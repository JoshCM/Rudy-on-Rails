package commands.game;

import java.util.UUID;

import commands.base.Command;
import communication.MessageInformation;
import models.game.Cart;
import models.game.Compass;
import models.game.Loco;
import models.game.Rail;
import models.game.Square;
import models.game.Trainstation;
import models.session.GameSession;
import models.session.RoRSession;

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

		
		Square square = this.session.getMap().getSquare(xPos, yPos);
		Rail rail = (Rail) square.getPlaceableOnSquare();
		Loco loco = session.getLocomotiveByPlayerId(playerId);
		
		if(trainstationOwnerId.equals(playerId)) {
			if(loco.getCarts().size() < 5 && !isLocoOrCartOnSquare(square, loco)) {
				if(rail.getPlaceableOnrail()==null) {//Damit nicht mehrere Wagons �bereinander erzeugt werden
					Cart cart = new Cart(session.sessionName, square, compass, playerId, false, null);
					rail.setPlaceableOnRail(cart);
				}
			}
		}
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
