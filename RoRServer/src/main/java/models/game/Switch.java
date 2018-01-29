package models.game;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Klasse für eine Rail vom Typ Weiche. Eine Weiche besteht immer aus einer inaktiven Railsection
 * */
public class Switch extends Rail {
    private Compass switchableDirectionEntry;
    private RailSection activeRailSection;
    private RailSection inactiveRailSection;


    public Switch(String sessionName, Square square, List<Compass> railSectionPositions){
        super(sessionName, square, railSectionPositions);
        setEntryNodeofSwitch();
    }
    
    public Switch(String sessionName, Square square, List<Compass> railSectionPositions, UUID trainstationId, UUID id) {
    	super(sessionName, square, railSectionPositions,trainstationId, id);
        setEntryNodeofSwitch();

    }

    @Override
    protected void createRailSectionsForRailSectionPositions(String sessionName, List<Compass> railSectionPositions) {
        if (railSectionPositions.size() == 4) {
            RailSection section1 = new RailSection(sessionName, this, railSectionPositions.get(0),
                    railSectionPositions.get(1), RailSectionStatus.ACTIVE);
            RailSection section2 = new RailSection(sessionName, this, railSectionPositions.get(2),
                    railSectionPositions.get(3), RailSectionStatus.INACTIVE);
            this.activeRailSection = section1;
            this.inactiveRailSection = section2;
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
        RailSection railSection = getRailSectionFromCompass(direction);

        if (direction == switchableDirectionEntry) {
            railSection = activeRailSection;
        }
        return railSection.getNode1() == direction? railSection.getNode2() : railSection.getNode1();
    }


    /**
     * Findet die richtige RailSection zu einem gegebenen Compass
     * @param compass - Richtung die zu einer Railsection gehört
     * @return RailSection
     */
    private RailSection getRailSectionFromCompass(Compass compass) {
        for (RailSection railSection : railSectionList) {
            if (railSection.getNode1() == compass || railSection.getNode2() == compass ) {
                return railSection;
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
        RailSection temp = inactiveRailSection;
        inactiveRailSection = activeRailSection;
        activeRailSection = temp;

    }



    /**
     * Setzt den die Eingangsrichtung für eine Weiche. Der Eingang ist immer der Compassknoten der zweimal in den
     * Railsections vertreten ist. Nur von dieser Seite aus sind die Fahrtrichtungen variierbar
     */
    void setEntryNodeofSwitch() {
        Set<Compass> set1 = new HashSet<Compass>();

        for (Compass compass : getAllCompassNodesOfRailSections()) {
            if (!set1.add(compass)) {
                this.switchableDirectionEntry = compass;
            }
        }
    }

}
