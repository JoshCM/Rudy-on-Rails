package models.game;

import java.util.UUID;

import communication.MessageInformation;

/**
 * Klasse fuer Schienen, die einem Feld (Square) zugeordnet sind und ein
 * Schienenstueck (= Gerade, Kurve) bzw. zwei Schienenstuecke (= Kreuzung,
 * Weiche) besitzen
 */
public class Rail extends InteractiveGameObject implements PlaceableOnSquare {

	// muss hier raus und eine Ebene tiefer(RailSection)
	protected PlaceableOnRail placeableOnRail = null;
	protected RailSection section1;
	protected RailSection section2;
	private UUID trainstationId;

	/**
	 * Konstruktor für Geraden oder Kurven
	 */
	public Rail(String sessionName, Square square, RailSectionPosition node1, RailSectionPosition node2) {
		super(sessionName, square);
		this.section1 = new RailSection(sessionName, getId(), node1, node2);

		notifyCreatedRail();
	}
	
	private void notifyCreatedRail() {
		MessageInformation messageInfo = new MessageInformation("CreateRail");
		messageInfo.putValue("railId", getId());
		messageInfo.putValue("trainstationId", getTrainstationId());
		messageInfo.putValue("railSectionId", section1.getId());
		messageInfo.putValue("railSectionPositionNode1", section1.getNode1().toString());
		messageInfo.putValue("railSectionPositionNode2", section1.getNode2().toString());
		messageInfo.putValue("squareId", getSquareId());
		// TODO: Später haben wir die richtigen SquareIds im Client, im Moment noch nicht!! 
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		
		notifyChange(messageInfo);
	}

	public void setPlaceableOnRail(PlaceableOnRail placeableOnRail) {
		this.placeableOnRail = placeableOnRail;
	}

	public RailSection getSection() {
		return section1;
	}
	
	public UUID getTrainstationId() {
		return trainstationId;
	}
	
	public void setTrainstationId(UUID trainstationId) {
		this.trainstationId = trainstationId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((placeableOnRail == null) ? 0 : placeableOnRail.hashCode());
		result = prime * result + ((section1 == null) ? 0 : section1.hashCode());
		result = prime * result + ((section2 == null) ? 0 : section2.hashCode());
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
		if (section1 == null) {
			if (other.section1 != null)
				return false;
		} else if (!section1.equals(other.section1))
			return false;
		if (section2 == null) {
			if (other.section2 != null)
				return false;
		} else if (!section2.equals(other.section2))
			return false;
		return true;
	}
}
