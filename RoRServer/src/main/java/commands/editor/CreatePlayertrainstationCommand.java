package commands.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import commands.base.CommandBase;
import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.game.Map;
import models.game.Rail;
import models.game.Compass;
import models.game.Square;
import models.game.Stock;
import models.game.Playertrainstation;
import models.helper.Validator;
import models.session.EditorSession;
import models.session.RoRSession;

public class CreatePlayertrainstationCommand extends CommandBase {
	private final static int OUTER_RAILS_COUNT = 8;
	private final static int INNER_RAILS_COUNT = 6;
	private final static int OUTER_RAILS_Y = OUTER_RAILS_COUNT / 2 * (-1);
	private final static int INNER_RAILS_Y = INNER_RAILS_COUNT / 2 * (-1);
	private static final int CURVED_RAIL_EAST_SOUTH_Y = -3;
	private static final int CURVED_RAIL_EAST_NORTH_Y = 2;
	private static final List<Integer> CROSSING_COORDINATES = Arrays.asList(-3, 2);

	private int xPos;
	private int yPos;
	private Compass alignment;
	private Map map;

	public CreatePlayertrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
		alignment = Compass.valueOf(messageInfo.getValueAsString("alignment"));
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		this.map = editorSession.getMap();
		Square newSquare = map.getSquare(xPos, yPos);

		if (!Validator.validateTrainstationOnMap(newSquare, alignment, editorSession.getMap())) {
			throw new InvalidModelOperationException(
					String.format("Trainstation(x:%s,y:%s) konnte nicht angelegt werden", xPos, yPos));
		} else {
			// generiere UUID für Trainstation
			UUID trainstationId = UUID.randomUUID();

			// neuer Stock wird erstellt
			// y-1 da die anfangsausrichtung der trainstation immer EAST ist
			Square stockSquare = map.getSquare(xPos, yPos - 1);
			Stock newStock = new Stock(session.getDescription(), stockSquare, trainstationId, alignment);
			stockSquare.setPlaceableOnSquare(newStock);

			// Trainstation wird erzeugt und auf Square gesetzt
			Playertrainstation trainstation = new Playertrainstation(session.getDescription(), newSquare,
					createTrainstationRails(map, newSquare, trainstationId), trainstationId, alignment, newStock);
			this.setSpawnPoint(trainstation);
			newSquare.setPlaceableOnSquare(trainstation);
		}
	}

	/**
	 * Erzeugt die Rails einer Trainstation anhand der Square der neuen Trainstation
	 * @param map				Die momentane Map
	 * @param square 			Das Square der Trainstation
	 * @param trainstationId	Die ID der Trainstation
	 * @return Eine Liste von IDs der Rails, die erzeugt und platziert wurden
	 */
	private List<UUID> createTrainstationRails(Map map, Square square, UUID trainstationId) {

		List<UUID> trainstationRailIds = new ArrayList<UUID>();

		// Railsection werden erstellt
		List<Compass> northSouthRailSectionPositions = Arrays.asList(Compass.NORTH, Compass.SOUTH);
		List<Compass> crossRailSectionPositions = Arrays.asList(Compass.NORTH, Compass.SOUTH, Compass.WEST,Compass.EAST);
		List<Compass> eastSouthRailSectionPositions = Arrays.asList(Compass.EAST, Compass.SOUTH);
		List<Compass> eastNorthRailSectionPositions = Arrays.asList(Compass.EAST, Compass.NORTH);

		// Squares der geraden Rails
		List<Square> straightTrainstationRailSquares = new ArrayList<Square>();

		// Squares der Kreuzungen
		List<Square> crossingTrainstationRailSquares = new ArrayList<Square>();

		// alle außenliegenden Squares werden hinzugefügt
		for (int i = OUTER_RAILS_Y; i < OUTER_RAILS_COUNT + OUTER_RAILS_Y; i++) {
			int railX = square.getXIndex() + 2;
			int railY = square.getYIndex() + i;
			Square trainstationRailSquare = map.getSquare(railX, railY);

			// wenn die Rail eine Kreuzung werden soll oder eine gerade Rail
			if (CROSSING_COORDINATES.contains(i))
				crossingTrainstationRailSquares.add(trainstationRailSquare);
			else
				straightTrainstationRailSquares.add(trainstationRailSquare);
		}

		// alle innenliegenden Squares werden hinzugefügt
		for (int i = INNER_RAILS_Y; i < INNER_RAILS_COUNT + INNER_RAILS_Y; i++) {
			int railX = square.getXIndex() + 1;
			int railY = square.getYIndex() + i;
			Square trainstationRailSquare = map.getSquare(railX, railY);

			// wenn die Rail eine Kurve werden soll oder eine gerade Rail
			if (i == CURVED_RAIL_EAST_SOUTH_Y)
				trainstationRailIds
						.add(createRail(trainstationRailSquare, trainstationId, eastSouthRailSectionPositions));
			else if (i == CURVED_RAIL_EAST_NORTH_Y)
				trainstationRailIds
						.add(createRail(trainstationRailSquare, trainstationId, eastNorthRailSectionPositions));
			else
				straightTrainstationRailSquares.add(trainstationRailSquare);
		}

		// StraightRails werden erstellt und auf die jeweiligen Squares gesetzt
		for (Square straightTrainstationRailSquare : straightTrainstationRailSquares) {
			trainstationRailIds
					.add(createRail(straightTrainstationRailSquare, trainstationId, northSouthRailSectionPositions));
		}

		// Crossings werden erstellt und auf die jeweilige Square gesetzt
		for (Square crossingTrainstationRailSquare : crossingTrainstationRailSquares) {
			trainstationRailIds
					.add(createRail(crossingTrainstationRailSquare, trainstationId, crossRailSectionPositions));
		}

		return trainstationRailIds;
	}

	/**
	 * Erzeugt eine Rail auf dem mitgegebenen Square mit einer TrainstationId und bestimmten RailSectionPositions
	 * @param trainstationRailSquare	Das Square auf dem die Rail platziert werden soll
	 * @param trainstationId			Die Id der zugehörigen Trainstation
	 * @param compassList				Die Liste von Compass
	 * @return Die Id der neuen Rail
	 */
	private UUID createRail(Square trainstationRailSquare, UUID trainstationId, List<Compass> compassList) {
		Rail rail = new Rail(session.getDescription(), trainstationRailSquare, compassList, trainstationId, UUID.randomUUID());
		rail.setSquareId(trainstationRailSquare.getId());
		trainstationRailSquare.setPlaceableOnSquare(rail);
		return rail.getId();
	}

	/**
	 * Sucht das richtige Rail und setze es als Spawnpoint
	 * @param trainstation Der neu erzeugte Trainstation
	 */
	private void setSpawnPoint(Playertrainstation trainstation) {
		int spawnPointX;
		int spawnPointY;
		switch (trainstation.getAlignment()) {
		case EAST:
			spawnPointX = 1;
			spawnPointY = 0;
		case SOUTH:
			spawnPointX = 0;
			spawnPointY = 1;
		case WEST:
			spawnPointX = -1;
			spawnPointY = 0;
		case NORTH:
			spawnPointX = 0;
			spawnPointY = -1;
		default:
			spawnPointX = 1;
			spawnPointY = 0;
		}
		
		for(Rail trainstationRail : trainstation.getTrainstationRails()) {
			if(trainstationRail.getXPos() == trainstation.getXPos() + spawnPointX)
				if(trainstationRail.getYPos() == trainstation.getYPos() + spawnPointY)
					trainstation.setSpawnPointforLoco(trainstationRail.getId());
		}
	}
}
