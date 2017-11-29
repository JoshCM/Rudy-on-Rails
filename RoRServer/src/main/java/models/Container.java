package models;

public class Container implements PlaceableOnSquare {
	
	private final String className;
	private String test;
	
	public Container() {
		className = getClass().getName();
		test = "Juliane";
	}

	@Override
	public String getClassName() {
		return className;		
	}

}
