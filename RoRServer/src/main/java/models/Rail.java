package models;

public class Rail extends InteractiveGameObject implements PlaceableOnSquare {
	
	protected Square square;
	protected PlaceableOnRail placeableOnRail = null;
	protected RailSection section1;
	protected RailSection section2;
	
	public Rail (Square square , RailSection section) {
		this.square = square;
		this.section1 = section;
	}
	
	public Rail (Square square, RailSection section1, RailSection section2) {
		this.square = square;
		this.section1 = section1;
		this.section2 = section2;
	}
	
	public void setPlaceableOnRail (PlaceableOnRail placeableOnRail) {
		if(placeableOnRail == null){
			this.placeableOnRail = placeableOnRail;
		}
	}
	
	
	
}
