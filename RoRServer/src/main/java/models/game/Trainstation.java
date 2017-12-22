package models.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import communication.MessageInformation;
import helper.Geometry;
import models.session.EditorSession;
import models.session.EditorSessionManager;

public class Trainstation extends InteractiveGameObject implements PlaceableOnSquare {

	private List<UUID> trainstationRailIds;
	private Compass alignment;
	
	private final int CLOCKWISE = 90;
	private final int COUNTER_CLOCKWISE = -90;
	EditorSession editorSession;
	
	public Trainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id, Compass alignment) {
		super(sessionName, square, id);
		this.trainstationRailIds = trainstationRailIds;
		this.alignment = alignment;
		editorSession = EditorSessionManager.getInstance().getEditorSessionByName(getSessionName());
		notifyCreatedTrainstation();
	}
	
	private void notifyCreatedTrainstation() {
		MessageInformation messageInfo = new MessageInformation("CreateTrainstation");
		messageInfo.putValue("trainstationId", getId());
		messageInfo.putValue("alignment", alignment);
		messageInfo.putValue("xPos", getXPos());
		messageInfo.putValue("yPos", getYPos());
		List<JsonObject> rails = new ArrayList<JsonObject>();
		for(UUID railId : getTrainstationRailIds()) {
			JsonObject json = new JsonObject();
			json.addProperty("railId", railId.toString());
			rails.add(json);
		}
		messageInfo.putValue("trainstationRails", rails);
		notifyChange(messageInfo);
	}

	public List<UUID> getTrainstationRailIds() {
		return trainstationRailIds;
	}
	
	/**
	 * Gibt die Liste von Rails der Trainstation zurück
	 * @return
	 */
	public List<Rail> getTrainstationRails(){
		List<Rail> trainstationRails = new ArrayList<Rail>();
		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(getSessionName());
		for(UUID railId : trainstationRailIds) {
			trainstationRails.add((Rail) editorSession.getMap().getPlaceableById(railId));
		}
		return trainstationRails;
	}
	
	/**
	 * Gibt die Liste von Rails der Trainstation umgedreht zurück
	 * @return
	 */
	public List<Rail> getReverseTrainstationRails(){
		List<Rail> trainstationRails = getTrainstationRails();
		List<Rail> shallowCopy = trainstationRails.subList(0, trainstationRails.size());
		Collections.reverse(shallowCopy);
		return shallowCopy;
	}
	
	private void notifyTrainstationAlignmentUpdated() {
		MessageInformation messageInformation = new MessageInformation("UpdateAlignmentOfTrainstation");
		messageInformation.putValue("id", this.getId());
		messageInformation.putValue("alignment", this.alignment.toString());
		notifyChange(messageInformation);
	}
	
	/**
	 * Rotiert die zugehörigen Rails einer Trainstation
	 * @param trainstationRail Ein Rail der Trainstation
	 * @param oldRailSquare Square worauf die Rail vorher PlaceableOnSquare war
	 * @param newRailSquare Square worauf die Rail PlaceableOnSquare werden soll
	 * @param right Uhrzeigersinn/Gegen Uhrzeigersinn
	 */
	private void rotateTrainstationRail(Rail trainstationRail, Square oldRailSquare, Square newRailSquare, boolean right) {
		// rotate der Rail ohne notify
		Rail tempRail = trainstationRail;
		tempRail.rotate(right, right);
		
		// bekomme sessionname für neue Rail
		String sessionName = editorSession.getSessionName();
		
		// nehme section1 von RailSection
		RailSection sectionOne = tempRail.getFirstSection();
		
		// lösche das Rail aus dem alten Square
		// muss zuerst gelöscht werden damit danach auch wieder was draufgesetzt werden kann
		oldRailSquare.deletePlaceable();
				
		// erzeuge neue Rail und setze intern das Square.PlacableOnSquare
		Rail newRail = new Rail(sessionName, newRailSquare, Arrays.asList(sectionOne.getNode1(), sectionOne.getNode2()), this.getId(), tempRail.getId());
		newRailSquare.setPlaceable(newRail);
	}
	
	/**
	 * Rotiert das Alignment der Trainstation
	 * @param right Uhrzeigersinn/Gegen Uhrzeigersinn
	 */
	private void rotateTrainstation(boolean right) {
		int newIndex;
		
		if(right) {
			newIndex = ((this.alignment.ordinal() + 1) % Compass.values().length);
		} else {
			newIndex = ((this.alignment.ordinal() - 1) % Compass.values().length);
			if(newIndex < 0) {
				newIndex += Compass.values().length;
			}
		}
		
		alignment = Compass.values()[newIndex];
		notifyTrainstationAlignmentUpdated();
	}

	
	
	/**
	 * Rotiert die Trainstation und alle zugehörigen Rails
	 * @param right Uhrzeigersinn/Gegen Uhrzeigersinn
	 */
	public void rotate(boolean right) {		
		// rotiert die Trainstation
		rotateTrainstation(right);
		
		// X und Y der Trainstation
		int pivotXPos = this.getXPos();
		int pivotYPos = this.getYPos();
		
		List<Rail> trainstationRails = getTrainstationRails();
		
		// wenn im Uhzeigersinn gedreht werden soll, dann muss die liste der rails umgedreht werden
		if(right)
			trainstationRails = getReverseTrainstationRails();
		
		for(Rail trainstationRail : trainstationRails) {
			int railXpos = trainstationRail.getXPos();
			int railYpos = trainstationRail.getYPos();
			
			// rotiert die koordinaten
			Geometry.Coordinate newCoordinate;
			if(right)
				newCoordinate = Geometry.rotate(railXpos, railYpos, CLOCKWISE, pivotXPos, pivotYPos);
			else
				newCoordinate = Geometry.rotate(railXpos, railYpos, COUNTER_CLOCKWISE, pivotXPos, pivotYPos);
			
			Square oldRailSquare = (Square)editorSession.getMap().getSquareById(trainstationRail.getSquareId());
			Square newRailSquare = (Square)editorSession.getMap().getSquare(newCoordinate.x, newCoordinate.y);
			rotateTrainstationRail(trainstationRail, oldRailSquare, newRailSquare, right);
		}
	}
	
	/**
	 * Validiert ob man die Rotation umsetzen kann
	 * @param right Uhrzeigersinn/Gegen Uhrzeigersinn
	 * @return (True)Validiert oder (False)nicht validiert
	 */
	public boolean validateRotation(boolean right) {
		int pivotXPos = this.getXPos();
		int pivotYPos = this.getYPos();
				
		List<Rail> trainstationRails = getTrainstationRails();
		if(right)
			trainstationRails = getReverseTrainstationRails();
		
		for(Rail trainstationRail : trainstationRails) {
			int railXpos = trainstationRail.getXPos();
			int railYpos = trainstationRail.getYPos();
	
			Geometry.Coordinate newCoordinate;
			if(right)
				newCoordinate = Geometry.rotate(railXpos, railYpos, CLOCKWISE, pivotXPos, pivotYPos);
			else
				newCoordinate = Geometry.rotate(railXpos, railYpos, COUNTER_CLOCKWISE, pivotXPos, pivotYPos);
			
			Square newRailSquare = (Square)editorSession.getMap().getSquare(newCoordinate.x, newCoordinate.y);
			if(newRailSquare == null)
				return false;
			if(newRailSquare.getPlaceableOnSquare() != null)
				if(!trainstationRailIds.contains(newRailSquare.getPlaceableOnSquare().getId()))
					return false;
		}
		return true;
	}
}
