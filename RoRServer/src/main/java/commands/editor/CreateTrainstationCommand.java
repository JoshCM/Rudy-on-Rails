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
import models.game.Trainstation;
import models.helper.Validator;
import models.session.EditorSession;
import models.session.RoRSession;

public class CreateTrainstationCommand extends CommandBase {
	private int xPos;
	private int yPos;
	private Compass alignment;
	private final static int TRAINSTATION_MARGIN = 1;
	private final static int RIGHT_STRAIGHT_RAILS_START_Y = -4;
	private final static int LEFT_STRAIGHT_RAILS_START_Y = -3;
	private final static int RIGHT_STRAIGHT_RAILS_COUNT = 8;
	private final static int LEFT_STRAIGHT_RAILS_COUNT = 6;
	private static final int EAST_SOUTH_CURVED = -3;
	private static final int EAST_NORTH_CURVED = 2;
	private List<Integer> crossing = Arrays.asList(-3,2);
	private Square spawnPointforLoco;

	public CreateTrainstationCommand(RoRSession session, MessageInformation messageInfo) {
		super(session, messageInfo);

		xPos = messageInfo.getValueAsInt("xPos");
		yPos = messageInfo.getValueAsInt("yPos");
		alignment = Compass.valueOf(messageInfo.getValueAsString("alignment"));
	}

	@Override
	public void execute() {
		EditorSession editorSession = (EditorSession) session;
		Map map = editorSession.getMap();
		Square newSquare = map.getSquare(xPos, yPos);

		if (!Validator.validateTrainstationOnMap(newSquare,alignment, editorSession.getMap())) {
			throw new InvalidModelOperationException(String.format("Trainstation(x:%s,y:%s) konnte nicht angelegt werden", xPos, yPos));
		} else {
			// generiere UUID für Trainstation
			UUID trainstationId = UUID.randomUUID();
			
			// neuer Stock wird erstellt 
			// y-1 da die anfangsausrichtung der trainstation immer EAST ist
			Square stockSquare = map.getSquare(xPos, yPos - 1);
			Stock newStock = new Stock(session.getName(), stockSquare, trainstationId, alignment);
			stockSquare.setPlaceableOnSquare(newStock);
			
			// Trainstation wird erzeugt und auf Square gesetzt
			Trainstation trainstation = new Trainstation(session.getName(), newSquare, createTrainstationRails(map, newSquare, trainstationId), trainstationId, alignment, newStock);
			trainstation.setSpawnPointforLoco(spawnPointforLoco);
			newSquare.setPlaceableOnSquare(trainstation);
		}
	}

	/**
	 * Erzeugt die Rails einer Trainstation anhand der Square der neuen Trainstation
	 * @param map Die momentane Map
	 * @param square Das Square der Trainstation
	 * @param trainstationId Die ID der Trainstation
	 * @return Eine Liste von IDs der Rails, die erzeugt und platziert wurden
	 */
	private List<UUID> createTrainstationRails(Map map, Square square, UUID trainstationId) {
		
		List<UUID> trainstationRailIds = new ArrayList<UUID>();

		// Railsection werden erstellt		
		List<Compass> northSouthRailSectionPositions = Arrays.asList(Compass.NORTH, Compass.SOUTH);
		List<Compass> crossRailSectionPositions = Arrays.asList(Compass.NORTH, Compass.SOUTH, Compass.WEST, Compass.EAST);
		List<Compass> eastSouthRailSectionPositions = Arrays.asList(Compass.EAST, Compass.SOUTH);
		List<Compass> eastNorthRailSectionPositions = Arrays.asList(Compass.EAST, Compass.NORTH);
		
		// Squares der geraden Rails
		List<Square> straightTrainstationRailSquares = new ArrayList<Square>();
		
		// Squares der Kreuzungen
		List<Square> crossingTrainstationRailSquares = new ArrayList<Square>();
		
		// alle außenliegenden Squares werden hinzugefügt
		for(int i = RIGHT_STRAIGHT_RAILS_START_Y; i < RIGHT_STRAIGHT_RAILS_COUNT + RIGHT_STRAIGHT_RAILS_START_Y; i++) {
			int railX = square.getXIndex() + 2;
			int railY = square.getYIndex() + i;
			Square trainstationRailSquare = map.getSquare(railX, railY);
			
			// wenn die Rail eine Kreuzung werden soll oder eine gerade Rail
			if(crossing.contains(i))
				crossingTrainstationRailSquares.add(trainstationRailSquare);
			else
				straightTrainstationRailSquares.add(trainstationRailSquare);
		}
		
		// alle innenliegenden Squares werden hinzugefügt
		for(int i = LEFT_STRAIGHT_RAILS_START_Y; i < LEFT_STRAIGHT_RAILS_COUNT + LEFT_STRAIGHT_RAILS_START_Y; i++) {
			int railX = square.getXIndex() + 1;
			int railY = square.getYIndex() + i;
			Square trainstationRailSquare = map.getSquare(railX, railY);
			
			// wenn die Rail eine Kurve werden soll oder eine gerade Rail
			if(i == EAST_SOUTH_CURVED)
				trainstationRailIds.add(createRail(trainstationRailSquare, trainstationId, eastSouthRailSectionPositions));
			else if(i == EAST_NORTH_CURVED)
				trainstationRailIds.add(createRail(trainstationRailSquare, trainstationId, eastNorthRailSectionPositions));
			else
				straightTrainstationRailSquares.add(trainstationRailSquare);
		}
		
		// StraightRails werden erstellt und auf die jeweiligen Squares gesetzt
		for(Square straightTrainstationRailSquare : straightTrainstationRailSquares) {
			trainstationRailIds.add(createRail(straightTrainstationRailSquare, trainstationId, northSouthRailSectionPositions));
		}
		
		// Crossings werden erstellt und auf die jeweilige Square gesetzt
		for(Square crossingTrainstationRailSquare : crossingTrainstationRailSquares) {
			trainstationRailIds.add(createRail(crossingTrainstationRailSquare, trainstationId, crossRailSectionPositions));
		}
				
		return trainstationRailIds;
	}

	/**
	 * Erzeugt eine Rail auf dem mitgegebenen Square mit einer TrainstationId und bestimmten RailSectionPositions
	 * @param trainstationRailSquare Das Square auf dem die Rail platziert werden soll
	 * @param trainstationId Die Id der zugehörigen Trainstation
	 * @param compassList Die Liste von Compass 
	 * @return Die Id der neuen Rail
	 */
	private UUID createRail(Square trainstationRailSquare, UUID trainstationId, List<Compass> compassList) {
		Rail rail = new Rail(session.getName(), trainstationRailSquare, compassList, trainstationId, UUID.randomUUID());
		rail.setSquareId(trainstationRailSquare.getId());
		trainstationRailSquare.setPlaceableOnSquare(rail);
		return rail.getId();
	}
}
