package models.game;

import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import communication.MessageInformation;
import exceptions.RailSectionException;
import models.session.GameSessionManager;

/**
 * Klasse fuer Schienen, die einem Feld (Square) zugeordnet sind und ein
 * Schienenstueck (= Gerade, Kurve) bzw. zwei Schienenstuecke (= Kreuzung,
 * Weiche) besitzen
 */
public class Rail extends InteractiveGameObject implements PlaceableOnSquare {
    private PlaceableOnRail placeableOnRail = null;
    private UUID trainstationId;
    private UUID mineID;
    private List<RailSection> railSectionList;
    private Resource resource; // TODO: Wie kann eine Schiene eine Ressource halten?


    /**
     * Konstruktor, falls die Schiene zu einem Bahnhof gehört
     * @param railSectionList
     * @param ueberObjekt
     */
    public Rail(List<RailSection> railSectionList, UUID ueberObjekt) {
        // Todo: Wenn Überobjekt setze transtationID oder minenID je nachdem was ueberobjekt dingens ist.
        setTrainstationID(ueberObjekt);
        this.railSectionList = railSectionList;
        notifyCreatedRail();
    }


    /**
     * Konstruktor für Geraden oder Kurven
     */
    public Rail(List<RailSection> railSectionList) {
        this.railSectionList = new ArrayList<RailSection>();
        notifyCreatedRail();
    }



    // TODO: Welche Ressourcen kann eine Schiene haben und wann?
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }


    //TODO: Gehört eigentlich in die Ressourcenklasse oder in Railressourcen oder so...
    /**
     * Platziert auf den benachbarten Squares (sofern frei) anhand der Schwierigkeit
     * des Spiels entweder Kohle oder Gold
     */
   /* public void generateResourcesNextToRail() {
        // TODO: Ressource immer Bestandteil einer bestimmten Session, Maps, Square, also muss es davon gelöst werden.
        Square square = getSquareFromGameSession();

        if (square != null) {

            // Durchgehen der benachbarten Squares, um Ressourcen zu platzieren
            List<Square> squares = square.getNeighbouringSquares();
            for (Square s : squares) {

                Double chanceToSpawn = Difficulty.EASY.getChanceToSpawnResource();

                if (s.getPlaceableOnSquare() == null && Math.random() < chanceToSpawn / 100) {
                    if (Math.random() < 0.5) {
                        Gold gold = new Gold(
                                GameSessionManager.getInstance().getGameSessionByName("DUMMER SESSIONNAME").getPlayerName(), s);
                        s.setPlaceableOnSquare(gold);
                    } else {
                        Coal coal = new Coal(
                                GameSessionManager.getInstance().getGameSessionByName("DUMMER SESSIONNAME").getPlayerName(), s);
                        s.setPlaceableOnSquare(coal);
                    }
                }
            }
        }
    }*/

    /**
     * Gibt das Square zurück, auf welchem die Rail liegt
     *
     * @return Square auf welchen die Rail liegt
     */
/*    public Square getSquareFromGameSession() {
         return GameSessionManager.getInstance().getGameSessionByName(sessionName).getMap().getSquareById(getSquareId());
    }*/

    /**
     * Erstellt für die hereingegebenen RailSectionPositions die jeweiligen
     * RailSections Dabei werden für jede RailSection immer zwei
     * RailSectionPositions benötigt
     *
     * @param sessionName
     * @param railSectionPositions
     */
/*
    private void createRailSectionsForRailSectionPositions(String sessionName, List<Compass> railSectionPositions) {
        for (int i = 0; i < railSectionPositions.size(); i += 2) {
            RailSection section = new RailSection(sessionName, this, railSectionPositions.get(i),
                   railSectionPositions.get(i + 1));
            railSectionList.add(section);
        }
    }
*/

    /**
     * Schickt Nachricht an Observer, wenn Schiene erstellt wurde.
     */
    // TODO: ist nur für Geraden und Kurven und muss gefixt werden. Sollte auch nicht unbedingt hier serialisiert werden...
    private void notifyCreatedRail() {
        MessageInformation messageInfo = new MessageInformation("CreateRail");

        List<JsonObject> railSectionJsons = new ArrayList<JsonObject>();
        for (RailSection section : railSectionList) {
            JsonObject json = new JsonObject();
            json.addProperty("railSectionId", section.getUUID().toString());
            json.addProperty("node1", section.getNode1().toString());
            json.addProperty("node2", section.getNode2().toString());
            railSectionJsons.add(json);
        }
        messageInfo.putValue("railSectionList", railSectionJsons);

        notifyObservers();
        // notifyChange(messageInfo);
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

    public void setTrainstationID(UUID trainstationId) {
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

    /**
     * Rotiert alle RailSections der Rail
     *
     * @param right
     */
    public void rotate(boolean right) {
        for (RailSection section : railSectionList) {
            section.rotate(right);
        }
    }

    public void rotate(boolean right, boolean notYet) {
        for (RailSection section : railSectionList) {
            section.rotate(right, notYet);
        }
    }

    @Override
    public String toString() {
        return " Rail ID()=\" + getUUID() + [placeableOnRail=" + placeableOnRail + ", trainstationId=" + trainstationId + "minesID=" + mineID + "]";
    }


   /* @Override
    public Rail loadFromMap(Square square, RoRSession session) {

        Rail rail = (Rail) square.getPlaceableOnSquare();

        // Hole die SectionPositions aus den RailSections und speichere in Liste
        List<Compass> railSectionPosition = new ArrayList<Compass>();
        for (RailSection section : rail.getRailSectionList()) {
            railSectionPosition.add(section.getNode1());
            railSectionPosition.add(section.getNode2());
        }

        // Neues Rail erstellen und damit an den Client schicken
        Rail newRail = new Rail(session.getPlayerName(), square, railSectionPosition);
        System.out.println("Neue Rail erstellt: " + newRail.toString());

        // Ressourcen setzen
        newRail.generateResourcesNextToRail();

        return newRail;
    }*/


    @Override
    public PlaceableOnSquare loadFromMap() {
        return null;
    }
}
