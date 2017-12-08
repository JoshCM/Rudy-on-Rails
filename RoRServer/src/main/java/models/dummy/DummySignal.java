package models.dummy;

import models.game.InteractiveGameObject;
import models.game.PlaceableOnRail;
import models.game.PlaceableOnSquare;
import models.game.Square;

public class DummySignal extends InteractiveGameObject implements PlaceableOnRail {

    private String name = "SignalTest";    
	public DummySignal(Square square) {
		super(square);
		this.square = square;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DummySignal other = (DummySignal) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	
}
