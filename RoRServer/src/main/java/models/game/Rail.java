package models.game;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import communication.MessageInformation;

/**
 * Klasse fuer Schienen, die einem Feld (Square) zugeordnet sind und ein
 * Schienenstueck (= Gerade, Kurve) bzw. zwei Schienenstuecke (= Kreuzung,
 * Weiche) besitzen
 */
public class Rail extends InteractiveGameObject implements PlaceableOnSquare {

	// muss hier raus und eine Ebene tiefer(RailSection)
	protected PlaceableOnRail placeableOnRail = null;
	protected List<RailSection> railSections;

	/**
	 * Konstruktor für Geraden oder Kurven
	 */
	public Rail(String sessionName, Square square, List<RailSectionPosition> railSectionPositions) {
		super(sessionName, square);
		
		railSections = new ArrayList<RailSection>();
		for(int i = 0; i < railSectionPositions.size(); i += 2) {
			RailSection section = new RailSection(sessionName, getId(), railSectionPositions.get(i), railSectionPositions.get(i + 1));
			railSections.add(section);
		}

		notifyCreatedRail();
	}
	
	private void notifyCreatedRail() {
		MessageInformation messageInfo = new MessageInformation("CreateRail");
		messageInfo.putValue("railId", getId());
		
		messageInfo.putValue("squareId", getSquareId());
		// TODO: Später haben wir die richtigen SquareIds im Client, im Moment noch nicht!! 
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		
		List<JsonObject> railSectionJsons = new ArrayList<JsonObject>();
		for(RailSection section : railSections) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((placeableOnRail == null) ? 0 : placeableOnRail.hashCode());
		
		for(RailSection section : railSections) {
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

	public void rotate(boolean right) {
		for(RailSection section : railSections) {
			section.rotate(right);
		}
	}
}
