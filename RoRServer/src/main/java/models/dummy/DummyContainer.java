package models.dummy;

import models.game.InteractiveGameObject;
import models.game.PlaceableOnRail;
import models.game.PlaceableOnSquare;

public class DummyContainer extends InteractiveGameObject implements PlaceableOnSquare {
	private String test;
	
	public DummyContainer() {
		super("blubb", null);
		test = "Juliane";
	}
}