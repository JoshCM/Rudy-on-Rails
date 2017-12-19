package commands.game;

import commands.base.CommandBase;
import communication.MessageInformation;
import models.session.GameSession;
import models.session.RoRSession;

public class ChangeSpeedOfLocomotiveCommand extends CommandBase {

    private int locoSpeed;

    public ChangeSpeedOfLocomotiveCommand(RoRSession session, MessageInformation messageInformation){
        super(session, messageInformation);
        locoSpeed = messageInformation.getValueAsInt("locoSpeed");

    }

    @Override
    public void execute() {
        GameSession gameSession = (GameSession)session;
    }
}
