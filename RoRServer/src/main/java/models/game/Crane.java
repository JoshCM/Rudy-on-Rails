package models.game;

import java.util.UUID;

import models.session.RoRSession;

public class Crane extends InteractiveGameObject implements PlaceableOnRail{

	public Crane(String sessionName, Square square, UUID trainstationId) {
		super(sessionName, square);
		
	}

	@Override
	public PlaceableOnRail loadFromMap(Square square, RoRSession session) {
		// TODO Auto-generated method stub
		return null;
	}

}
