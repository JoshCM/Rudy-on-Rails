package models.game;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Klasse für eine Rail vom Typ Weiche. Eine Weiche besteht immer aus einer inaktiven Railsection
 * */
public class Switch extends Rail {
    private Compass switchableDirectionEntry;


    public Switch(String sessionName, Square square, List<Compass> railSectionPositions){
        super(sessionName, square, railSectionPositions);
        setEntryNodeofSwitch();
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
        RailSection railSection;
        if (direction == switchableDirectionEntry) {
            railSection = getActiveRailSection();
        } else {
            railSection = getInActiveRailSection();
        }
        return railSection.getNode1() == direction? railSection.getNode2() : railSection.getNode1();
    }

    /**
     * Wechselt die aktive Railsection einer Weiche
     */
    public void changeSwitch() {
        for (RailSection section : railSectionList) {
            section.switchActitityStatus();
        }
    }

    public RailSection getActiveRailSection() {
        for (RailSection railSection: railSectionList) {
            if(railSection.getRailSectionStatus() == RailSectionStatus.ACTIVE) {
                return railSection;
            }
        }
        return null;
    }

    public RailSection getInActiveRailSection() {
        for (RailSection railSection: railSectionList) {
            if(railSection.getRailSectionStatus() == RailSectionStatus.INACTIVE) {
                return railSection;
            }
        }
        return null;
    }

    /**
     * Setzt den die Eingangsrichtung für eine Weiche. Der Eingang ist immer der Compassknoten der zweimal in den
     * Railsections vertreten ist. Nur von dieser Seite aus sind die Fahrtrichtungen variierbar
     */
    void setEntryNodeofSwitch() {
        final Set<Compass> set1 = new HashSet<Compass>();

        for (Compass compass : getAllCompasNodesOfRailSections()) {
            if (!set1.add(compass)) {
                this.switchableDirectionEntry = compass ;
            }
        }


    }

}
