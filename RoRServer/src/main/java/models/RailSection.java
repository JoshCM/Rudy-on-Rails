package models;

/**
 * Klasse für ein Schienenstück mit "Eingang" und "Ausgang"
 */
public class RailSection {
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

}
