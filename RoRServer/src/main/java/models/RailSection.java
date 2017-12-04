package models;

/**
 * Klasse fuer ein Schienenstueck mit "Eingang" und "Ausgang"
 */
public class RailSection extends ModelBase {
	// Geraden
	public static final RailSection STRAIGHT_VERTICAL = new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH);
	public static final RailSection STRAIGHT_HORIZONTAL = new RailSection(RailSectionPosition.EAST, RailSectionPosition.WEST);
	
	// Kurven
	public static final RailSection CURVE_NE = new RailSection(RailSectionPosition.NORTH, RailSectionPosition.EAST);
	public static final RailSection CURVE_ES = new RailSection(RailSectionPosition.EAST, RailSectionPosition.SOUTH);
	public static final RailSection CURVE_SW = new RailSection(RailSectionPosition.SOUTH, RailSectionPosition.WEST);
	public static final RailSection CURVE_WN = new RailSection(RailSectionPosition.WEST, RailSectionPosition.NORTH);
	
	private RailSectionPosition node1;
	private RailSectionPosition node2;
	
	public RailSection(){
		
	}
	
	public RailSection(RailSectionPosition node1, RailSectionPosition node2) {
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
	
	

}
