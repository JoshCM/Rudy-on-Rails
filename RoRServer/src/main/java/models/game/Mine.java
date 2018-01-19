package models.game;

import java.util.List;
import java.util.UUID;
import communication.MessageInformation;
import models.helper.CompassHelper;
import models.session.EditorSessionManager;
import models.session.RoRSession;

/**
 * Model für Mine
 * @author apoeh001
 *
 */
public class Mine extends InteractiveGameObject implements PlaceableOnRail {
	
	private List<Resource> resources;
	private UUID railId;
	private Compass alignment;

	public Mine(String sessionName, Square square, Compass alignment, UUID railId) {
		super(sessionName, square);
		this.alignment = alignment;
		this.railId = railId;
		notifyCreatedMine();
	}
	
	public void setRailId(UUID id) {
		this.railId = id;
	}
	
	public Compass getAlignment() {
		return alignment;
	}
	
	public void setAlignment(Compass alignment) {
		this.alignment = alignment;
	}

	@Override
	public PlaceableOnRail loadFromMap(Square square, RoRSession session) {
				
		// Neue Mine erstellen und damit an den Client schicken
		Mine newMine = new Mine(session.getDescription(), square, alignment, railId);
		
		// sessionName neu setzen, damit Observer Änderung dieses Objekts mitbekommen kann
		newMine.setSessionName(session.getDescription());
		Rail rail = (Rail)session.getMap().getPlaceableOnSquareById(railId);
		rail.setPlaceableOnRail(newMine);
		
		return newMine;
	}
	
	/**
	 * Methode zum Drehen der Mine nach Links (Dreht die Rail darunter gleich mit)
	 */
	public void rotateLeft() {
		// Das Rail wird direkt mit verschoben
		Square square = EditorSessionManager.getInstance().getEditorSessionByName(sessionName).getMap().getSquareById(getSquareId());
		Rail rail = (Rail)square.getPlaceableOnSquare();
		rail.rotate(false);
		alignment = CompassHelper.rotateCompass(false, alignment);
		notifyAlignmentUpdated();	
	}
	
	/**
	 * Methode zum Drehen der Mine nach Rechts (Dreht die Rail darunter gleich mit)
	 */
	public void rotateRight() {		
		// Das Rail wird direkt mit verschoben
		Square square = EditorSessionManager.getInstance().getEditorSessionByName(sessionName).getMap().getSquareById(getSquareId());
		Rail rail = (Rail)square.getPlaceableOnSquare();
		rail.rotate(true);
		alignment = CompassHelper.rotateCompass(true, alignment);
		notifyAlignmentUpdated();
	}
	
	/**
	 * Methode zum Erstellen einer Nachrichtm wenn eine neue Mine erstellt wurde
	 */
	private void notifyCreatedMine() {
		MessageInformation message = new MessageInformation("CreateMine");
		message.putValue("xPos", getXPos());
		message.putValue("yPos", getYPos());
		message.putValue("mineId", getId());
		message.putValue("squareId", getSquareId());
		message.putValue("alignment", alignment.toString());
		notifyChange(message);		
	}
	
	/**
	 * Schickt eine Nachricht an den Client, wenn sich die Richtung der Mine ge�ndert hat
	 */
	private void notifyAlignmentUpdated() {
		MessageInformation message = new MessageInformation("UpdateAlignmentOfMine");
		message.putValue("alignment", alignment);
		message.putValue("mineId", getId());
		message.putValue("railId", railId.toString());
		notifyChange(message);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alignment == null) ? 0 : alignment.hashCode());
		result = prime * result + ((railId == null) ? 0 : railId.hashCode());
		result = prime * result + ((resources == null) ? 0 : resources.hashCode());
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
		Mine other = (Mine) obj;
		if (alignment != other.alignment)
			return false;
		if (railId == null) {
			if (other.railId != null)
				return false;
		} else if (!railId.equals(other.railId))
			return false;
		if (resources == null) {
			if (other.resources != null)
				return false;
		} else if (!resources.equals(other.resources))
			return false;
		return true;
	}

	@Override
	public void specificUpdate() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}