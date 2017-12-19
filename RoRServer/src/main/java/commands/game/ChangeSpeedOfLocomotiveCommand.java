package commands.game;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.game.Loco;
import models.session.GameSession;
import models.session.RoRSession;

import java.util.UUID;

public class ChangeSpeedOfLocomotiveCommand extends CommandBase {

    private int locoSpeed;
    private String clientId;

    public ChangeSpeedOfLocomotiveCommand(RoRSession session, MessageInformation messageInformation){
        super(session, messageInformation);
        locoSpeed = messageInformation.getValueAsInt("locoSpeed");
        clientId = messageInformation.getClientid();
    }

    @Override
    public void execute() {
        GameSession gameSession = (GameSession)session;
        Loco loco = gameSession.getLocomotiveByPlayerId(UUID.fromString(clientId));
        loco.setSpeed(locoSpeed);
    }
}
