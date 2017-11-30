package models;

public class DummySignal extends InteractiveGameObject implements PlaceableOnRail {

    private final String className;
    private String name = "SignalTest";
	
	public DummySignal(Square square) {
		super(square);
		className = getClass().getName();
	}

	@Override
	public String getClassName() {
		return className;
	}

}
