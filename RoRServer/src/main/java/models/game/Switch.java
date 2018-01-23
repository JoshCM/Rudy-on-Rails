package models.game;

import java.util.List;

public class Switch extends Rail {

    public Switch(String sessionName, Square square, List<Compass> railSectionPositions){
        super(sessionName, square, railSectionPositions);
    }

    @Override
    protected void createRailSectionsForRailSectionPositions(String sessionName, List<Compass> railSectionPositions) {
        if (railSectionPositions.size() == 4) {
            RailSection section1 = new RailSection(sessionName, this, railSectionPositions.get(0),
                    railSectionPositions.get(1), RailSectionStatus.ACTIVE);
            RailSection section2 = new RailSection(sessionName, this, railSectionPositions.get(2),
                    railSectionPositions.get(3), RailSectionStatus.INACTIVE);
            railSectionList.add(section1);
            railSectionList.add(section2);
        }
    }

    public void changeSwitch() {
        for (RailSection section : railSectionList) {
            section.switchActitityStatus();
        }
    }

}
