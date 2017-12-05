package models.game;

import models.dataTranserObject.MessageInformation;

/**
 * Klasse fuer Schienen, die einem Feld (Square) zugeordnet sind und ein
 * Schienenstueck (= Gerade, Kurve) bzw. zwei Schienenstuecke (= Kreuzung,
 * Weiche) besitzen
 */

public class Rail extends InteractiveGameObject implements PlaceableOnSquare {

	protected PlaceableOnRail placeableOnRail = null;
	protected RailSection section1;
	protected RailSection section2;

	/**
	 * Konstruktor für Geraden oder Kurven
	 */
	public Rail(Square square, RailSection section) {
		super(square);
		this.section1 = section;

		SendCreatedRailMessage();
	}
	
	private void SendCreatedRailMessage() {
		MessageInformation messageInfo = new MessageInformation();
		messageInfo.putValue("railId", getId());
		messageInfo.putValue("railSectionId", section1.getId());
		messageInfo.putValue("railSectionPositionNode1", section1.getNode1().ordinal());
		messageInfo.putValue("railSectionPositionNode2", section1.getNode2().ordinal());
		messageInfo.putValue("squareId", square.getId());
		
		square.getMap().getEditorSession().SendMessage("CreateRail", messageInfo);
	}

	/***
	 * Konstruktor für Kreuzungen oder Weichen
	 * @param square
	 * @param section1
	 * @param section2
	 */
	public Rail(Square square, RailSection section1, RailSection section2) {
		super(square);
		this.section1 = section1;
		this.section2 = section2;
	}

	public void setPlaceableOnRail(PlaceableOnRail placeableOnRail) {
		
		this.placeableOnRail = placeableOnRail;

	}

	public RailSection getSection() {
		return section1;
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
