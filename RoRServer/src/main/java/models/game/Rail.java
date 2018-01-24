package models.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
    private Signals signals;
	// muss hier raus und eine Ebene tiefer(RailSection)
	protected PlaceableOnRail placeableOnRail = null;
	protected RailSection section1;
	protected RailSection section2;
	private Square square;
	private UUID trainstationId;
	protected List<RailSection> railSectionList;
	private Resource resource;
	private Sensor sensor;
	private boolean sensorActive;

    /**
     * Konstruktor für Geraden oder Kurven
     */
    public Rail(String sessionName, Square square, List<Compass> railSectionPositions) {
        this(sessionName, square, railSectionPositions, false, new UUID(0L, 0L), UUID.randomUUID());
    }
    
    /**
     * Konstruktor für Rails mit Signalen
     */
    public Rail(String sessionName, Square square, List<Compass> railSectionPositions, boolean withSignals) {
        this(sessionName, square, railSectionPositions, withSignals, new UUID(0L, 0L), UUID.randomUUID());
    }
    
    public Rail(String sessionName, Square square, List<Compass> railSectionPositions, boolean withSignals, UUID trainstationId, UUID id) {
        super(sessionName, square, id);
        
        railSectionList = new ArrayList<RailSection>();
        createRailSectionsForRailSectionPositions(sessionName, railSectionPositions);
        setTrainstationId(trainstationId);
        notifyCreatedRail();
        
        if(withSignals) {
        	Signals signals = new Signals(sessionName, square);
        	this.signals = signals;
        };
    }
    
    public Rail(String sessionName, Square square, List<Compass> railSectionPositions, UUID trainstationId, UUID id) {
    	this(sessionName, square, railSectionPositions, false, trainstationId, id);
    }

    public Rail(String sessionName, Square newSquare, List<Compass> railSectionsCompass, boolean b, UUID trainstationId, UUID id, PlaceableOnRail placeableOnRail) {
    	this(sessionName,newSquare,railSectionsCompass,b,trainstationId,id);
    	this.placeableOnRail = placeableOnRail;
    	
	}

	// TODO: Welche Ressourcen kann eine Schiene haben und wann?
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
    
    public Sensor getSensor() {
    	return sensor;
    }
    
    /**
     * Neuen Sensor auf Rail platzieren
     */
    public void placeSensor(UUID playerId) {
    	GameSession gameSession = GameSessionManager.getInstance().getGameSessionByName(sessionName);
    	Square square = gameSession.getMap().getSquareById(getSquareId());
    	sensor = new Sensor(getSessionName(), square , getId(), playerId);
    	
    	// Die Locos kennen den Sensor und sagen diesem Bescheid, wenn darüber gefahren wird
    	Loco.addSensor(sensor);
    }
    
    public void removeSensor() {
    	sensorActive = false;
    	sensor = null;
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
                                GameSessionManager.getInstance().getGameSessionByName(sessionName).getDescription(), s);
                        s.setPlaceableOnSquare(gold);
                    } else {
                        Coal coal = new Coal(
                                GameSessionManager.getInstance().getGameSessionByName(sessionName).getDescription(), s);
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
    protected void createRailSectionsForRailSectionPositions(String sessionName, List<Compass> railSectionPositions) {
        for (int i = 0; i < railSectionPositions.size(); i += 2) {
            RailSection section = new RailSection(sessionName, this, railSectionPositions.get(i),
                    railSectionPositions.get(i + 1), RailSectionStatus.ACTIVE);
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
		messageInfo.putValue("trainstationId", getTrainstationId());
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());

		List<JsonObject> railSectionJsons = new ArrayList<JsonObject>();
		for (RailSection section : railSectionList) {
			JsonObject json = new JsonObject();
			json.addProperty("railSectionId", section.getId().toString());
			json.addProperty("node1", section.getNode1().toString());
			json.addProperty("node2", section.getNode2().toString());
			json.addProperty("railSectionStatus", section.getRailSectionStatus().toString());
			railSectionJsons.add(json);
		}
		messageInfo.putValue("railSections", railSectionJsons);

		notifyChange(messageInfo);
	}
	
	public PlaceableOnRail getPlaceableOnrail() {
		return placeableOnRail;
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

    public RailSection getActivDirection() {
        for (RailSection railSection : railSectionList) {
            if(railSection.getRailSectionStatus() == RailSectionStatus.ACTIVE){
                return railSection;
            }
        }
	    return railSectionList.get(0);
    }


    public UUID getTrainstationId() {
        return trainstationId;
    }

    public void setTrainstationId(UUID trainstationId) {
        this.trainstationId = trainstationId;
    }
    
    public void deletePlaceableOnRail() {
		placeableOnRail = null;
		notifyDeletePlaceableOnRail();
	}
	
	public void notifyDeletePlaceableOnRail() {
		MessageInformation message = new MessageInformation("DeleteMine");
		message.putValue("railId", getId());
		notifyChange(message);
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
        
        if(signals != null) {
        	signals.switchSignals();
        }
    }

    public void rotate(boolean right, boolean notYet) {
        for (RailSection section : railSectionList) {
            section.rotate(right, notYet);
        }

        if(signals != null) {
        	signals.switchSignals();
        }
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
    
    /**
	 * Prüft, ob die Rail eine Gerade ist
	 * @return Gibt true zurück, wenn die Rail eine Gerade ist
	 */
	public boolean railIsStraight() {
		
		boolean railIsStraight = false;
		
		RailSection railSection = getFirstSection();
		Compass node1 = railSection.getNode1();
		Compass node2 = railSection.getNode2();
		if ((node1 == Compass.NORTH && node2 == Compass.SOUTH) || 
			(node1 == Compass.SOUTH && node2 == Compass.NORTH) ||
			(node1 == Compass.EAST && node2 == Compass.WEST) ||
			(node1 == Compass.WEST && node2 == Compass.EAST)) {
			railIsStraight = true;
		}		
		return railIsStraight;
	}
	
	/**
	 * Gibt die Richtung der Rail zurück: Dabei ist die Richtung 
	 * - North, wenn die Rail nur eine Gerade besitzt, welche von Norden nach Süden oder Süden nach Norden geht
	 * - South, wenn die Rail nur eine Gerade besitzt, welche von Westen nach Osten oder Osten nach Westen geht
	 * @return South oder North, wenn es eine Gerade ist, ansonsten Null
	 */
	public Compass getAlignment() {
		
		Compass alignment = null;
		
		RailSection railSection = getFirstSection();
		Compass node1 = railSection.getNode1();
		Compass node2 = railSection.getNode2();
		
		if ((node1 == Compass.NORTH && node2 == Compass.SOUTH) || 
				(node1 == Compass.SOUTH && node2 == Compass.NORTH) ||
				(node1 == Compass.EAST && node2 == Compass.WEST) ||
				(node1 == Compass.WEST && node2 == Compass.EAST)) {
			alignment = node2;
		}
	
		return alignment;
	}

    @Override
    public Rail loadFromMap(Square square, RoRSession session) {
        Rail newRail = null;

        Rail rail = (Rail) square.getPlaceableOnSquare();

        // Hole die SectionPositions aus den RailSections und speichere in Liste
        List<Compass> railSectionPosition = new ArrayList<Compass>();
        for (RailSection section : rail.getRailSectionList()) {
            railSectionPosition.add(section.getNode1());
            railSectionPosition.add(section.getNode2());
        }

        boolean createSignals = rail.getSignals() != null;

        // Neues Rail erstellen und damit an den Client schicken
        if (rail.getClassName().contains("Switch")) {
            newRail = new Switch(session.getDescription(), square, railSectionPosition);
        } else {
            newRail = new Rail(session.getDescription(), square, railSectionPosition, createSignals, trainstationId, rail.getId());
        }
        System.out.println("Neue Rail erstellt: " + newRail.toString());



        // Sonderfall für Krezungen, die Signale haben
        // ToDo: Refactoring, wenn die Modelstruktur umgebaut wurde!
        if(createSignals) {
        	if(rail.getSignals().isWestSignalActive() && rail.getSignals().isEastSignalActive()) {
        		newRail.getSignals().switchSignals();
        	}
        }
        
        return newRail;
    }
    
    public Signals getSignals() {
    	return signals;
    }
}
