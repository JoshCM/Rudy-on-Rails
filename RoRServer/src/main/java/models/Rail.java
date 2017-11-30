package models;

public class Rail extends InteractiveGameObject implements PlaceableOnSquare {
	
	protected PlaceableOnRail placeableOnRail = null;
	protected RailSection section1;
	protected RailSection section2;
    private final String className;
	
	public Rail (Square square, RailSection section) {
		super(square);
		this.className = getClass().getName();
		this.section1 = section;
	}
	
	public Rail (Square square, RailSection section1, RailSection section2) {
		super(square);
		this.className = getClass().getName();
		this.section1 = section1;
		this.section2 = section2;
	}
	
	public void setPlaceableOnRail (PlaceableOnRail placeableOnRail) {
		this.placeableOnRail = placeableOnRail;
	}

	@Override
	public String getClassName() {
		System.out.println("Returning className");
		return className;
	}
	
	public RailSection getSection() {
		return section1;
	}
	
	
	
}
