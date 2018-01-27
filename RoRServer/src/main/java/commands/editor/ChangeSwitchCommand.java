package commands.editor;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.NotChangeableGameObject;
import exceptions.NotRotateableException;
import models.game.*;
import models.session.EditorSession;
import models.session.RoRSession;

public class ChangeSwitchCommand extends CommandBase {
    private int xPos;
    private int yPos;


    public ChangeSwitchCommand(RoRSession session, MessageInformation messageInfo) {
        super(session, messageInfo);

        xPos = messageInfo.getValueAsInt("xPos");
        yPos = messageInfo.getValueAsInt("yPos");
    }

    @Override
    public void execute() {
        EditorSession editorSession = (EditorSession) session;
        Map map = editorSession.getMap();
        Square square = map.getSquare(xPos, yPos);
        PlaceableOnSquare railSwitch = square.getPlaceableOnSquare();
        if (railSwitch instanceof Switch) {
            ((Switch) railSwitch).changeSwitch();
        } else {
            throw new NotChangeableGameObject("Das Objekt kann seinen Zustand nicht wechseln ");
        }
    }
}
