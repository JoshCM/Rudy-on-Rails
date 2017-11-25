package models;

public class Rail extends InteractiveGameObject implements PlaceableOnSquare {
	
	protected PlaceableOnRail placeableOnRail = null;
	protected RailSection section1;
	protected RailSection section2;
	
	public Rail (RailSection section) {
		//super(square);
		this.section1 = section;
	}
	
	public Rail (RailSection section1, RailSection section2) {
		//super(square);
		this.section1 = section1;
		this.section2 = section2;
	}
	
	public void setPlaceableOnRail (PlaceableOnRail placeableOnRail) {
		this.placeableOnRail = placeableOnRail;
	}
	
	
	
}
