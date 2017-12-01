package models;

public class DummySignal extends InteractiveGameObject implements PlaceableOnRail {

    private String name = "SignalTest";
	
	public DummySignal(Square square) {
		super(square);
	}
}
