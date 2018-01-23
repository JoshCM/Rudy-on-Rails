package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.NotRotateableException;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
import models.game.Switch;
import models.session.EditorSession;
import models.session.RoRSession;

public class ChangeSwitchCommand extends CommandBase {
    private int xPos;
    private int yPos;
    private boolean change;


    public ChangeSwitchCommand(RoRSession session, MessageInformation messageInfo) {
        super(session, messageInfo);

        xPos = messageInfo.getValueAsInt("xPos");
        yPos = messageInfo.getValueAsInt("yPos");
        change = messageInfo.getValueAsBoolean("change");
    }

    @Override
    public void execute() {
        EditorSession editorSession = (EditorSession)session;
        Map map = editorSession.getMap();
        Square square = map.getSquare(xPos, yPos);
        Switch railSwitch = (Switch)square.getPlaceableOnSquare();

        if(change){railSwitch.changeSwitch();}
    }
}
