package models.game;

import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import communication.MessageInformation;
import communication.topic.TopicMessageQueue;
import models.session.GameSessionManager;
import models.session.RoRSession;

/**
 * Klasse fuer Schienen, die einem Feld (Square) zugeordnet sind und ein
 * Schienenstueck (= Gerade, Kurve) bzw. zwei Schienenstuecke (= Kreuzung,
 * Weiche) besitzen
 */
public class Rail extends InteractiveGameObject implements PlaceableOnSquare, Comparable<Rail> {

	// muss hier raus und eine Ebene tiefer(RailSection)
	protected PlaceableOnRail placeableOnRail = null;
	protected RailSection section1;
	protected RailSection section2;
	private UUID trainstationId;
	protected List<RailSection> railSections;
	private Resource resource;

	/**
	 * Konstruktor für Geraden oder Kurven
	 */
	public Rail(String sessionName, Square square, List<Compass> railSectionPositions) {
		super(sessionName, square);
		railSections = new ArrayList<RailSection>();
		createRailSectionsForRailSectionPositions(sessionName, railSectionPositions);
		notifyCreatedRail();
	}

	public Rail(String sessionName, Square square, List<Compass> railSectionPositions, UUID trainstationId, UUID id) {
		super(sessionName, square, id);

		setTrainstationId(trainstationId);
		railSections = new ArrayList<RailSection>();
		createRailSectionsForRailSectionPositions(sessionName, railSectionPositions);
		notifyCreatedRail();
	}

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
	 * Gibt das Square zur�ck, auf welchem die Rail liegt
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
	 * @param directions
	 */
	private void createRailSectionsForRailSectionPositions(String sessionName, List<Compass> railSectionPositions) {
		for (int i = 0; i < railSectionPositions.size(); i += 2) {
			RailSection section = new RailSection(sessionName, this, railSectionPositions.get(i),
					railSectionPositions.get(i + 1));
			railSections.add(section);
		}
	}

	private void notifyCreatedRail() {
		MessageInformation messageInfo = new MessageInformation("CreateRail");
		messageInfo.putValue("railId", getId());

		messageInfo.putValue("squareId", getSquareId());
		// TODO: Später haben wir die richtigen SquareIds im Client, im Moment noch
		// nicht!!
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());

		List<JsonObject> railSectionJsons = new ArrayList<JsonObject>();
		for (RailSection section : railSections) {
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
		return railSections.get(0);
	}

	public List<RailSection> getRailSectionList() {
		return railSections;
	}

	public UUID getTrainstationId() {
		return trainstationId;
	}

	public void setTrainstationId(UUID trainstationId) {
		this.trainstationId = trainstationId;
	}

	/**
	 * Gibt den Ausgang der Rail, und damit auch die Zuk�nftige Fahrtrichtugn der
	 * Lok zur�ck.
	 * 
	 * @param direction
	 * @return exitDirection
	 */
	public Compass getExitDirection(Compass direction) {
		for (RailSection r : railSections) {
			if (r.getNode1() == direction)
				return r.getNode2();
			if (r.getNode2() == direction)
				return r.getNode1();
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((placeableOnRail == null) ? 0 : placeableOnRail.hashCode());

		for (RailSection section : railSections) {
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
		for (RailSection section : railSections) {
			section.rotate(right);
		}
	}

	public void rotate(boolean right, boolean notYet) {
		for (RailSection section : railSections) {
			section.rotate(right, notYet);
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

		// Ressourcen setzen
		newRail.generateResourcesNextToRail();

		return newRail;
	}
}
