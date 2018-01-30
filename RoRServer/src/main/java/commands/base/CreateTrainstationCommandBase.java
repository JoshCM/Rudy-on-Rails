package commands.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import communication.MessageInformation;
import models.game.Compass;
import models.game.Crane;
import models.game.Map;
import models.game.Rail;
import models.game.Square;
import models.game.Switch;
import models.game.Trainstation;
import models.session.RoRSession;

public class CreateTrainstationCommandBase extends CommandBase {

	private final static int OUTER_RAILS_COUNT = 8;
	private final static int INNER_RAILS_COUNT = 6;
	private final static int OUTER_RAILS_Y = OUTER_RAILS_COUNT / 2 * (-1);
	private final static int INNER_RAILS_Y = INNER_RAILS_COUNT / 2 * (-1);
	private static final int CURVED_RAIL_EAST_SOUTH_Y = -3;
	private static final int CURVED_RAIL_EAST_NORTH_Y = 2;
	private static final List<Integer> CROSSING_COORDINATES = Arrays.asList(-3, 2);
	
	public CreateTrainstationCommandBase(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);
	}

	@Override
	public void execute() {

	}
	
	/**
	 * Erzeugt die Rails einer Trainstation anhand der Square der neuen Trainstation
	 * 
	 * @param map
	 *            Die momentane Map
	 * @param square
	 *            Das Square der Trainstation
	 * @param trainstationId
	 *            Die ID der Trainstation
	 * @return Eine Liste von IDs der Rails, die erzeugt und platziert wurden
	 */
	protected List<UUID> createTrainstationRails(Map map, Square square, UUID trainstationId, Compass alignment) {

		List<UUID> trainstationRailIds = new ArrayList<UUID>();

		// Railsection werden erstellt
		List<Compass> northSouthRailSectionPositions = Arrays.asList(Compass.NORTH, Compass.SOUTH);

		// enthält railSectionPositions für Top und Bottom Switch
		List<List<Compass>> switchRailSectionPositions = Arrays.asList(
				Arrays.asList(Compass.NORTH, Compass.SOUTH, Compass.NORTH, Compass.WEST),
				Arrays.asList(Compass.NORTH, Compass.SOUTH, Compass.SOUTH, Compass.WEST));
		// enthält railSectionPositions für EastSouth Rails
		List<Compass> eastSouthRailSectionPositions = Arrays.asList(Compass.EAST, Compass.SOUTH);
		// enthält railSectionPositions für EastNorth Rails
		List<Compass> eastNorthRailSectionPositions = Arrays.asList(Compass.EAST, Compass.NORTH);

		// Squares der geraden Rails
		List<Square> straightTrainstationRailSquares = new ArrayList<Square>();

		// Squares der Weichen
		List<Square> switchTrainstationRailSquares = new ArrayList<Square>();

		// alle außenliegenden Squares werden hinzugefügt
		for (int i = OUTER_RAILS_Y; i < OUTER_RAILS_COUNT + OUTER_RAILS_Y; i++) {
			int railX = square.getXIndex() + 2;
			int railY = square.getYIndex() + i;
			Square trainstationRailSquare = map.getSquare(railX, railY);

			// wenn die Rail eine Kreuzung werden soll oder eine gerade Rail
			if (CROSSING_COORDINATES.contains(i))
				switchTrainstationRailSquares.add(trainstationRailSquare);
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
		for (int i = 0; i < switchTrainstationRailSquares.size(); i++) {
			Square switchTrainstationRailSquare = switchTrainstationRailSquares.get(i);
			trainstationRailIds
					.add(createSwitch(switchTrainstationRailSquare, trainstationId, switchRailSectionPositions.get(i)));
		}

		return trainstationRailIds;
	}
	
	/**
	 * Erzeugt eine Rail auf dem mitgegebenen Square mit einer TrainstationId und
	 * bestimmten RailSectionPositions
	 * 
	 * @param trainstationRailSquare
	 *            Das Square auf dem die Rail platziert werden soll
	 * @param trainstationId
	 *            Die Id der zugehörigen Trainstation
	 * @param compassList
	 *            Die Liste von Compass
	 * @return Die Id der neuen Rail
	 */
	private UUID createRail(Square trainstationRailSquare, UUID trainstationId, List<Compass> compassList) {
		Rail rail = new Rail(session.getSessionName(), trainstationRailSquare, compassList, trainstationId,
				UUID.randomUUID());
		rail.setSquareId(trainstationRailSquare.getId());
		trainstationRailSquare.setPlaceableOnSquare(rail);
		return rail.getId();
	}

	private UUID createSwitch(Square trainstationRailSquare, UUID trainstationId, List<Compass> compassList) {
		Rail switchRail = new Switch(session.getSessionName(), trainstationRailSquare, compassList, trainstationId, UUID.randomUUID());
		switchRail.setSquareId(trainstationRailSquare.getId());
		trainstationRailSquare.setPlaceableOnSquare(switchRail);
		return switchRail.getId();
	}

	public Crane createCrane(Square stock, Trainstation trainstation, Map map, Compass alignment) {
		Square craneSquare = findCraneSquare(stock, alignment, map);
		List<Rail> TrainstationRails = trainstation.getTrainstationRails();
		Rail craneRail = findCraneRail(TrainstationRails, craneSquare);

		Compass craneAlignment = getCraneAlignment(trainstation.getAlignment());

		Crane newCrane = new Crane(this.session.sessionName, craneSquare, trainstation.getId(), craneAlignment,
				craneRail.getId());
		craneRail.setPlaceableOnRail(newCrane);
		return newCrane;
	}

	public Square findCraneSquare(Square stock, Compass alignment, Map map) {
		Square craneSquare = null;

		switch (alignment) {
		case EAST:
			craneSquare = map.getSquare(stock.getXIndex() + 1, stock.getYIndex());
			break;
		case WEST:
			craneSquare = map.getSquare(stock.getXIndex() - 1, stock.getYIndex());
			break;
		case SOUTH:
			craneSquare = map.getSquare(stock.getXIndex(), stock.getYIndex() + 1);
			break;
		case NORTH:
			craneSquare = map.getSquare(stock.getXIndex(), stock.getYIndex() - 1);
			break;
		default:
			break;
		}
		return craneSquare;
	}

	public Rail findCraneRail(List<Rail> trainstationRails, Square craneSquare) {
		Rail craneRail = null;
		for (Rail rail : trainstationRails) {
			if (rail.getXPos() == craneSquare.getXIndex() && rail.getYPos() == craneSquare.getYIndex()) {
				craneRail = rail;
				return craneRail;
			}
		}
		return craneRail;
	}

	public Compass getCraneAlignment(Compass alignment) {
		switch (alignment) {
		case EAST:
			return Compass.NORTH;
		case NORTH:
			return Compass.WEST;
		case SOUTH:
			return Compass.EAST;
		case WEST:
			return Compass.SOUTH;
		default:
			return Compass.EAST;
		}
	}
}
