package models.game;

public class RailSection {
	
	public static final RailSection STRAIGHT_VERTICAL = new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH);
	public static final RailSection STRAIGHT_HORIZONTAL = new RailSection(RailSectionPosition.EAST, RailSectionPosition.WEST);
	public static final RailSection CURVE_NE = new RailSection(RailSectionPosition.NORTH, RailSectionPosition.EAST);
	
	private RailSectionPosition node1;
	private RailSectionPosition node2;
	
	public RailSection(RailSectionPosition node1, RailSectionPosition node2) {
		this.node1 = node1;
		this.node2 = node2;
	}

}
