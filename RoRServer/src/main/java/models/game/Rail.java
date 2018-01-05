package models.game;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;
import communication.MessageInformation;
import exceptions.RailSectionException;
import models.session.GameSession;
import models.session.GameSessionManager;
import models.session.RoRSession;

/**
 * Klasse fuer Schienen, die einem Feld (Square) zugeordnet sind und ein
 * Schienenstueck (= Gerade, Kurve) bzw. zwei Schienenstuecke (= Kreuzung,
 * Weiche) besitzen
 */
public class Rail extends InteractiveGameObject implements PlaceableOnSquare, Comparable<Rail> {
    private PlaceableOnRail placeableOnRail = null;
    private UUID trainstationId;
    private List<RailSection> railSectionList;
    private Resource resource;
    private Signals signals;

    /**
     * Konstruktor für Geraden oder Kurven
     */
    public Rail(String sessionName, Square square, List<Compass> railSectionPositions) {
        super(sessionName, square);
        railSectionList = new ArrayList<RailSection>();
        createRailSectionsForRailSectionPositions(sessionName, railSectionPositions);
        notifyCreatedRail();
    }
    
    public Rail(String sessionName, Square square, List<Compass> railSectionPositions, boolean withSignals) {
        this(sessionName, square, railSectionPositions);
        
        if(withSignals) {
        	Signals signals = new Signals(sessionName, square);
        	this.signals = signals;
        };
    }

    public Rail(String sessionName, Square square, List<Compass> railSectionPositions, UUID trainstationId, UUID id) {
        super(sessionName, square, id);

        setTrainstationId(trainstationId);
        railSectionList = new ArrayList<RailSection>();
        createRailSectionsForRailSectionPositions(sessionName, railSectionPositions);
        notifyCreatedRail();
    }

    // TODO: Welche Ressourcen kann eine Schiene haben und wann?
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    /**
     * Platziert auf den benachbarten Squares (sofern frei) anhand der Schwierigkeit
     * des Spiels entweder Kohle oder Gold
     */
    public void generateResourcesNextToRail() {
        Square square = getSquareFromGameSession();

        if (square != null) {

            // Durchgehen der benachbarten Squares, um Ressourcen zu platzieren
            List<Square> squares = square.getNeighbouringSquares();
            for (Square s : squares) {

                Double chanceToSpawn = Difficulty.EASY.getChanceToSpawnResource();

                if (s.getPlaceableOnSquare() == null && Math.random() < chanceToSpawn / 100) {
                    if (Math.random() < 0.5) {
                        Gold gold = new Gold(
                                GameSessionManager.getInstance().getGameSessionByName(sessionName).getName(), s);
                        s.setPlaceableOnSquare(gold);
                    } else {
                        Coal coal = new Coal(
                                GameSessionManager.getInstance().getGameSessionByName(sessionName).getName(), s);
                        s.setPlaceableOnSquare(coal);
                    }
                }
            }
        }
    }

    /**
     * Gibt das Square zurück, auf welchem die Rail liegt
     *
     * @return Square auf welchen die Rail liegt
     */
    public Square getSquareFromGameSession() {
        return GameSessionManager.getInstance().getGameSessionByName(sessionName).getMap().getSquareById(getSquareId());
    }

    /**
     * Erstellt für die hereingegebenen RailSectionPositions die jeweiligen
     * RailSections Dabei werden für jede RailSection immer zwei
     * RailSectionPositions benötigt
     *
     * @param sessionName
     * @param railSectionPositions
     */
    private void createRailSectionsForRailSectionPositions(String sessionName, List<Compass> railSectionPositions) {
        for (int i = 0; i < railSectionPositions.size(); i += 2) {
            RailSection section = new RailSection(sessionName, this, railSectionPositions.get(i),
                    railSectionPositions.get(i + 1));
            railSectionList.add(section);
        }
    }

    /**
     * Schickt Nachricht an Observer, wenn Schiene erstellt wurde.
     */
    private void notifyCreatedRail() {
        MessageInformation messageInfo = new MessageInformation("CreateRail");
        messageInfo.putValue("railId", getId());

        messageInfo.putValue("squareId", getSquareId());
        // TODO: Später haben wir die richtigen SquareIds im Client, im Moment noch
        // nicht!!
        messageInfo.putValue("xPos", getXPos());
        messageInfo.putValue("yPos", getYPos());

        List<JsonObject> railSectionJsons = new ArrayList<JsonObject>();
        for (RailSection section : railSectionList) {
            JsonObject json = new JsonObject();
            json.addProperty("railSectionId", section.getId().toString());
            json.addProperty("node1", section.getNode1().toString());
            json.addProperty("node2", section.getNode2().toString());
            railSectionJsons.add(json);
        }
        messageInfo.putValue("railSections", railSectionJsons);

        notifyChange(messageInfo);
    }

    public void setPlaceableOnRail(PlaceableOnRail placeableOnRail) {
        this.placeableOnRail = placeableOnRail;
    }

    public RailSection getFirstSection() {
        return railSectionList.get(0);
    }

    public List<RailSection> getRailSectionList() {
        return railSectionList;
    }

    public UUID getTrainstationId() {
        return trainstationId;
    }

    public void setTrainstationId(UUID trainstationId) {
        this.trainstationId = trainstationId;
    }

    /**
     * Gibt den Ausgang der Rail, und damit auch die Zukünftige Fahrtrichtung der
     * Lok zurück.
     *
     * @param direction
     * @return exitDirection
     */
    public Compass getExitDirection(Compass direction) {
        for (RailSection r : railSectionList) {
            if (r.getNode1() == direction)
                return r.getNode2();
            if (r.getNode2() == direction)
                return r.getNode1();
        }
        return null;
    }

    /**
     * Stellt alle Sections der Rail um.
     */
    public void toggleAllDirectionsOfSections() {
        for (RailSection r : railSectionList) {
            r.toggleIsDrivable();
        }
    }

    /**
     * Gibt alle befahrbaren RailSections einer Rail zurück.
     *
     * @return ArrayList mit allen befahrbaren RailSections
     */
    public List<RailSection> getAllDrivableRailSections() {
        List<RailSection> drivableRailSections = new ArrayList<>();
        for (RailSection r : railSectionList) {
            if (r.getIsDrivable()) drivableRailSections.add(r);
        }
        return drivableRailSections;
    }

    /**
     * Gibt alle existierenden RailSections einer Rail zurück.
     *
     * @return ArrayList mit allen befahrbaren RailSections
     */
    public List<RailSection> getAllRailSections() {
        return this.railSectionList;
    }

    public void addRailSection(RailSection rs)  {
        if (this.railSectionList.contains(rs)) {
            throw new RailSectionException("This kind of RailSection already exists in this Rail");
        } else {
            this.railSectionList.add(rs);
        }
    }

    public void deleteRailSection(RailSection rs) {
        if (!this.railSectionList.contains(rs)) {
            throw new RailSectionException("Couldn't find this Railsection " + rs.toString());
        } else {
            this.railSectionList.remove(rs);
        }
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((placeableOnRail == null) ? 0 : placeableOnRail.hashCode());

        for (RailSection section : railSectionList) {
            result = prime * result + ((section == null) ? 0 : section.hashCode());
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rail other = (Rail) obj;
        if (placeableOnRail == null) {
            if (other.placeableOnRail != null)
                return false;
        } else if (!placeableOnRail.equals(other.placeableOnRail))
            return false;

        return true;
    }

    /**
     * Rotiert alle RailSections der Rail
     *
     * @param right
     */
    public void rotate(boolean right) {
        for (RailSection section : railSectionList) {
            section.rotate(right);
        }
        signals.switchSignals();
    }

    public void rotate(boolean right, boolean notYet) {
        for (RailSection section : railSectionList) {
            section.rotate(right, notYet);
        }
        signals.switchSignals();
    }

    @Override
    public String toString() {
        return "Rail [placeableOnRail=" + placeableOnRail + ", trainstationId=" + trainstationId + ", getXPos()="
                + getXPos() + ", getYPos()=" + getYPos() + ", getId()=" + getId() + "]";
    }

    public void changeSquare(Square newSquare) {
        this.setSquareId(newSquare.getId());
        this.setXPos(newSquare.getXIndex());
        this.setYPos(newSquare.getYIndex());
    }

    @Override
    public int compareTo(Rail o) {
        if (this.getXPos() == o.getXPos()) {
            return o.getYPos() - this.getYPos();
        } else {
            return o.getXPos() - this.getXPos();
        }
    }

    @Override
    public Rail loadFromMap(Square square, RoRSession session) {

        Rail rail = (Rail) square.getPlaceableOnSquare();

        // Hole die SectionPositions aus den RailSections und speichere in Liste
        List<Compass> railSectionPosition = new ArrayList<Compass>();
        for (RailSection section : rail.getRailSectionList()) {
            railSectionPosition.add(section.getNode1());
            railSectionPosition.add(section.getNode2());
        }

        // Neues Rail erstellen und damit an den Client schicken
        Rail newRail = new Rail(session.getName(), square, railSectionPosition);
        System.out.println("Neue Rail erstellt: " + newRail.toString());
        
        if(rail.getSignals() != null) {
        	Signals newSignals = new Signals(session.getName(), square);
        	
        	// Der einfachhalthalber nur für Kreuzungen
        	if(newSignals.isWestSignalActive() && newSignals.isEastSignalActive()) {
        		newSignals.switchSignals();
        	}
        }
        
        return newRail;
    }
    
    public Signals getSignals() {
    	return signals;
    }
}
