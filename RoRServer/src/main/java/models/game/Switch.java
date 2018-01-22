package models.game;

import java.util.List;

public class Switch extends Rail {

    public Switch(String sessionName, Square square, List<Compass> railSectionPositions){
        super(sessionName, square, railSectionPositions);
        setRailSectionStatus();
    }

    private void setRailSectionStatus(){
        if (railSectionList.size() == 2){
            railSectionList.get(1).setRailSectionStatus(RailSectionStatus.INACTIVE);
        }
    }

    public void changeSwitch() {
        for (RailSection section : railSectionList) {
            section.switchActitityStatus();
        }
    }

}
