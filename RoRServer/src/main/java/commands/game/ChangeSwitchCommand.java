package commands.game;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.*;
import models.session.GameSession;
import models.session.RoRSession;
import resources.PropertyManager;

import java.util.UUID;

public class ChangeSwitchCommand extends CommandBase {
    private int xPos;
    private int yPos;
    private UUID playerId;
    private final int changeSwitchCosts = Integer.valueOf(PropertyManager.getProperty("change_switch_costs"));

    public ChangeSwitchCommand(RoRSession session, MessageInformation messageInfo) {
        super(session, messageInfo);

        xPos = messageInfo.getValueAsInt("xPos");
        yPos = messageInfo.getValueAsInt("yPos");
        this.playerId = UUID.fromString(messageInfo.getClientid());
    }

    @Override
    public void execute() {
        GameSession gameSession = (GameSession)session;
        Map map = gameSession.getMap();
        Square square = map.getSquare(xPos, yPos);
        PlaceableOnSquare railSwitch = square.getPlaceableOnSquare();
        GamePlayer currentPlayer = (GamePlayer)session.getPlayerById(playerId);

        if (railSwitch instanceof Switch) {
            Trainstation trainstation = getTrainstationFromSwitch((Switch) railSwitch, gameSession);

            if (trainstation != null) {
                if (isPlayerSwitchOwner(trainstation)) {
                    ((Switch)railSwitch).changeSwitch();
                }
                if (trainstation instanceof Publictrainstation && currentPlayer.getGoldCount() > 0 ) {
                    currentPlayer.removeGold(Integer.valueOf(PropertyManager.getProperty("change_switch_costs")));
                    ((Switch)railSwitch).changeSwitch();
                }
            } else {
                if (!(currentPlayer.getGoldCount() <= 0)) {
                    currentPlayer.removeGold(Integer.valueOf(PropertyManager.getProperty("change_switch_costs")));
                    ((Switch)railSwitch).changeSwitch();
                }
            }
        }
    }

    private Trainstation getTrainstationFromSwitch(Switch railSwitch, GameSession gameSession) {
        if(!railSwitch.getTrainstationId().equals(new UUID(0, 0))) {
            Map map = gameSession.getMap();
            return (Trainstation) map.getPlaceableOnSquareById(railSwitch.getTrainstationId());
        }
        return null;
    }


    private boolean isPlayerSwitchOwner(Trainstation trainstation) {
        if(playerId.equals(trainstation.getPlayerId())) {
            return true;
        }
        return false;
    }
}
