package models;

public class DummyContainer implements PlaceableOnSquare {
	
	private final String className;
	private String test;
	
	public DummyContainer() {
		className = getClass().getName();
		test = "Juliane";
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return className;
	}

	@Override
	public void setPlaceableOnRail(PlaceableOnRail placeableOnRail) {
		// TODO Auto-generated method stub
		
	}

}
