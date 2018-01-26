package commands.game;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Map;
import models.game.PlaceableOnSquare;
import models.game.Square;
import models.game.Switch;
import models.session.GameSession;
import models.session.RoRSession;

public class ChangeSwitchCommand extends CommandBase {
    private int xPos;
    private int yPos;
    private boolean change;


    public ChangeSwitchCommand(RoRSession session, MessageInformation messageInfo) {
        super(session, messageInfo);

        xPos = messageInfo.getValueAsInt("xPos");
        yPos = messageInfo.getValueAsInt("yPos");
    }

    @Override
    public void execute() {
        GameSession gameSession = (GameSession)session;
        Map map = gameSession.getMap();
        Square square = map.getSquare(xPos, yPos);
        PlaceableOnSquare railSwitch = square.getPlaceableOnSquare();
        if (railSwitch instanceof Switch) {
            ((Switch)railSwitch).changeSwitch();
        }
    }
}
