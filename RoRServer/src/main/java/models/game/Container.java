package models.game;

import models.session.RoRSession;

public class Container extends InteractiveGameObject implements PlaceableOnSquare {

	private Resource resource;

	public Container(String sessionName, Square square) {
		super(sessionName, square);
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public PlaceableOnSquare loadFromMap(Square square, RoRSession session) {
		// TODO Auto-generated method stub
		return null;
	}
}
