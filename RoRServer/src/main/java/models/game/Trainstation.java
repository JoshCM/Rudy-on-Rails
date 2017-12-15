package models.game;

import java.util.ArrayList;
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

	public void rotate(boolean right) {
		EditorSession editorSession = EditorSessionManager.getInstance().getEditorSessionByName(sessionName);
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
							railXpos += 2;
							railYpos += 0;
						}
					}else if(railXpos > pivotXPos){
						if(railYpos < pivotYPos) {
							railXpos += (-2);
							railYpos += 0;
						}
					}
				}
			}
			if(railXpos != trainstationRail.getXPos() || railYpos != trainstationRail.getYPos()) {
				Square oldRailSquare = (Square)editorSession.getMap().getSquareById(trainstationRail.getSquareId());
				Square newRailSquare = (Square)editorSession.getMap().getSquare(railXpos, railYpos);
				
				// altes Square wird zum moven mitgegeben
				newRailSquare.movePlaceable(trainstationRail, oldRailSquare);
				
				System.out.println(String.format("%s, %s", oldRailSquare, newRailSquare));
			}
		}
	}
}
