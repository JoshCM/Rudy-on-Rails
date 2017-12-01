package models;

public class DummyContainer extends InteractiveGameObject implements PlaceableOnSquare {

	private String test;
	
	public DummyContainer() {
		super(null);
		test = "Juliane";
	}

	@Override
	public void setPlaceableOnRail(PlaceableOnRail placeableOnRail) {
		// TODO Auto-generated method stub
		
	}

}