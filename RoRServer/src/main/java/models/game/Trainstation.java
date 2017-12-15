package models.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;

import communication.MessageInformation;
import models.session.EditorSession;
import models.session.EditorSessionManager;

public class Trainstation extends InteractiveGameObject implements PlaceableOnSquare {

	private List<UUID> trainstationRailIds;
	private Compass alignment;
	private String sessionName;
	
	public Trainstation(String sessionName, Square square, List<UUID> trainstationRailIds, UUID id, Compass alignment) {
		super(sessionName, square, id);
		this.trainstationRailIds = trainstationRailIds;
		this.alignment = alignment;
		this.sessionName = sessionName;
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
	
	public List<Rail> getTrainstationRails(){
		List<Rail> trainstationRails = new ArrayList<Rail>();
		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(sessionName);
		for(UUID railId : trainstationRailIds) {
			trainstationRails.add((Rail) editorSession.getMap().getPlaceableById(railId));
		}
		return trainstationRails;
	}
	
	private void notifyTrainstationAlignmentUpdated() {
		MessageInformation messageInformation = new MessageInformation("UpdateAlignmentOfTrainstation");
		messageInformation.putValue("id", this.getId());
		messageInformation.putValue("alignment", this.alignment.toString());
		notifyChange(messageInformation);
	}
	
	private void rotateTrainstationRail(Rail trainstationRail, Square oldRailSquare, Square newRailSquare, boolean right) {
		
		// rotate der Rail ohne notify
		Rail tempRail = trainstationRail;
		tempRail.rotate(right, false);
		
		// bekomme sessionname für neue Rail
		String sessionName = EditorSessionManager.getInstance().getEditorSession().getName();
		
		// nehme section1 von RailSection
		RailSection sectionOne = tempRail.getFirstSection();
		
		// erzeuge neue Rail und setze intern das Square.PlacableOnSquare
		Rail newRail = new Rail(sessionName, newRailSquare, Arrays.asList(sectionOne.getNode1(), sectionOne.getNode2()), tempRail.getId());
		
		// lösche das Rail aus dem alten Square
		oldRailSquare.deletePlaceable();
		
		System.out.println(String.format("%s, %s", oldRailSquare, newRailSquare));
	}
	
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

	public void rotate(boolean right) {
		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(sessionName);
		
		// rotiert das Bahnhofgebäude
		rotateTrainstation(right);
		
		int pivotXPos = this.getXPos();
		int pivotYPos = this.getYPos();
		
		for(Rail trainstationRail : getTrainstationRails()) {
			int railXpos = trainstationRail.getXPos();
			int railYpos = trainstationRail.getYPos();
			
			// diagonale Rail zu Trainstation
			if(railXpos != pivotXPos && railYpos != pivotYPos) {
				if(right) {
					
				}else {
					if(railXpos < pivotXPos) {
						if(railYpos < pivotYPos) {
							railXpos += 0;
							railYpos += 2;
						}else if(railYpos > pivotYPos) {
							railXpos += 2;
							railYpos += 0;
						}
					}else if(railXpos > pivotXPos){
						if(railYpos < pivotYPos) {
							railXpos += (-2);
							railYpos += 0;
						}else if(railYpos > pivotYPos) {
							railXpos += 0;
							railYpos += (-2);
						}
					}else if(railYpos < pivotYPos) {
						if(railXpos < pivotXPos) {
							railXpos += 0;
							railYpos += 2;
						}else if(railXpos > pivotXPos) {
							railXpos += (-2);
							railYpos += 0;
						}
					}else if(railYpos > pivotYPos) {
						if(railXpos < pivotXPos) {
							railXpos += 2;
							railYpos += 0;
						}else if(railYpos > pivotYPos) {
							railXpos += 0;
							railYpos += (-1);
						}
					}
				}
			}else{
				// nebenliegende Rail zu Trainstation
				if(right) {
					
				}else {
					if(railXpos < pivotXPos) {
						if(railYpos == pivotYPos) {
							railXpos += 1;
							railYpos += 1;
						}
					}else if(railXpos > pivotXPos){
						if(railYpos == pivotYPos) {
							railXpos += (-1);
							railYpos += (-1);
						}
					}else if(railYpos < pivotYPos) {
						if(railXpos == pivotXPos) {
							railXpos += (-1);
							railYpos += 1;
						}
					}else if(railYpos > pivotYPos) {
						if(railXpos == pivotXPos) {
							railXpos += 1;
							railYpos += (-1);
						}
					}
				}
			}
			
			Square oldRailSquare = (Square)editorSession.getMap().getSquareById(trainstationRail.getSquareId());
			Square newRailSquare = (Square)editorSession.getMap().getSquare(railXpos, railYpos);
			rotateTrainstationRail(trainstationRail, oldRailSquare, newRailSquare, right);
		}
	}
}
