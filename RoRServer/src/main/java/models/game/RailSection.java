package models.game;

import java.util.UUID;

import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.base.ModelBase;

/**
 * Klasse für ein Schienenstueck mit "Eingang" und "Ausgang"
 */
public class RailSection extends ModelBase {
	private UUID squareId;
	private int squareXPos;
	private int squareYPos;
	private UUID railId;
	private Direction node1;
	private Direction node2;
	
	// TODO: hier muss placeableOnSquareSection

	public RailSection(String sessionName, Rail rail, Direction node1, Direction node2) {
		super(sessionName);
		
		if (node1 == node2) {
			throw new InvalidModelOperationException(
					"RailSectionPositions are equal; node1: " + node1.toString() + ", node2: " + node2.toString());
		}

		this.railId = rail.getId();
		this.squareId = rail.getSquareId();
		this.squareXPos = rail.getXPos();
		this.squareYPos = rail.getYPos();
		this.node1 = node1;
		this.node2 = node2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((node1 == null) ? 0 : node1.hashCode());
		result = prime * result + ((node2 == null) ? 0 : node2.hashCode());
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
		RailSection other = (RailSection) obj;
		if (node1 != other.node1)
			return false;
		if (node2 != other.node2)
			return false;
		return true;
	}

	public UUID getId() {
		return railId;
	}

	public Direction getNode1() {
		return node1;
	}

	public Direction getNode2() {
		return node2;
	}

	/**
	 * Rotiert die RailSectionPositions. 
	 * Rechtsherum z.B. North zu East
	 * @param right Rechts herum (true) oder links herum (false)
	 */
	public void rotate(boolean right) {
		node1 = rotateRailSectionPosition(node1, right);
		node2 = rotateRailSectionPosition(node2, right);
		
		notifyNodesUpdated();
	}
	
	private void notifyNodesUpdated() {
		MessageInformation messageInformation = new MessageInformation("UpdateNodesOfRailSection");
		messageInformation.putValue("squareId", squareId);
		messageInformation.putValue("xPos", squareXPos);
		messageInformation.putValue("yPos", squareYPos);
		messageInformation.putValue("railSectionId", getId().toString());
		messageInformation.putValue("node1", node1.toString());
		messageInformation.putValue("node2", node2.toString());
		notifyChange(messageInformation);
	}
	
	private Direction rotateRailSectionPosition(Direction direction, boolean right) {
		int newIndex;
		
		if(right) {
			newIndex = ((direction.ordinal() + 1) % Direction.values().length);
		} else {
			newIndex = ((direction.ordinal() - 1) % Direction.values().length);
			if(newIndex < 0) {
				newIndex += Direction.values().length;
			}
		}
		
		return Direction.values()[newIndex];
	}

}
