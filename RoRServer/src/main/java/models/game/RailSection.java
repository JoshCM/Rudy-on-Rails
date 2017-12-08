package models.game;

import exceptions.InvalidModelOperationException;
import models.base.ModelBase;

/**
 * Klasse für ein Schienenstueck mit "Eingang" und "Ausgang"
 */
public class RailSection extends ModelBase {
	private Rail rail;
	private RailSectionPosition node1;
	private RailSectionPosition node2;
	
	// TODO: hier muss placeableOnSquareSection

	public RailSection(Rail rail, RailSectionPosition node1, RailSectionPosition node2) {
		super(rail.getRoRSession());

		if (node1 == node2) {
			throw new InvalidModelOperationException(
					"RailSectionPositions are equal; node1: " + node1.toString() + ", node2: " + node2.toString());
		}

		this.rail = rail;
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

	public Rail getRail() {
		return rail;
	}

	public RailSectionPosition getNode1() {
		return node1;
	}

	public RailSectionPosition getNode2() {
		return node2;
	}
}
