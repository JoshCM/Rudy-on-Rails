package models.game;

import java.util.List;
import java.util.UUID;
import communication.MessageInformation;
import models.helper.CompassHelper;
import models.session.EditorSessionManager;
import models.session.RoRSession;

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
	
	public Compass getAlignment() {
		return alignment;
	}
	
	public void setAlignment(Compass alignment) {
		this.alignment = alignment;
	}

	@Override
	public PlaceableOnRail loadFromMap(Square square, RoRSession session) {
				
		// Neue Mine erstellen und damit an den Client schicken
		Mine newMine = new Mine(session.getName(), square, alignment, railId);
		
		//sessionName neu setzen, damit Observer Änderung dieses Objekts mitbekommen kann
		newMine.setName(session.getName());
		Rail rail = (Rail)session.getMap().getPlaceableById(railId);
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
	 * Schickt eine Nachricht an den Client, wenn sich die Richtung der Mine geändert hat
	 */
	private void notifyAlignmentUpdated() {
		MessageInformation message = new MessageInformation("UpdateAlignmentOfMine");
		message.putValue("alignment", alignment);
		message.putValue("mineId", getId());
		message.putValue("railId", railId.toString());
		notifyChange(message);
	}

}