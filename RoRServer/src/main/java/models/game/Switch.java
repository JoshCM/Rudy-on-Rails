package models.game;

import java.util.List;
import java.util.UUID;

/**
 * Klasse für eine Rail vom Typ Weiche. Eine Weiche besteht immer aus einer inaktiven Railsection
 * */
public class Switch extends Rail {
    private List<RailSectionStatus> railSectionStatus;


    public Switch(String sessionName, Square square, List<Compass> railSectionPositions){
        super(sessionName, square, railSectionPositions);
    }
    
    public Switch(String sessionName, Square square, List<Compass> railSectionPositions, UUID trainstationId, UUID id) {
    	super(sessionName, square, railSectionPositions,trainstationId, id);
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

    /**
     * Gibt die korrekte Ausfahrtsrichtung einer Weiche zurück
     * @param direction
     * @return
     */
    @Override
    public Compass getExitDirection(Compass direction) {
        for (RailSection railSection : railSectionList) {
            if (railSection.getRailSectionStatus() == RailSectionStatus.ACTIVE ) {
                return railSection.getNode1() == direction ? railSection.getNode2() : railSection.getNode1();
            }
        }
        return null;
    }

    /**
     * Wechselt die aktive Railsection einer Weiche
     */
    public void changeSwitch() {
        for (RailSection section : railSectionList) {
            section.switchActitityStatus();
        }
    }

}
