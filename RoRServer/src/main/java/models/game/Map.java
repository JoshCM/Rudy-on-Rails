package models.game;

import models.base.ModelBase;
import models.helper.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import communication.MessageInformation;
import exceptions.NotMoveableException;

/**
 * Klasse, die das Spielfeld darstellt und aus Feldern (Squares) besteht
 */
public class Map extends ModelBase {
	private String name;
	private Square squares[][];
	private final int mapSize = 50;
	private int availablePlayerSlots;

	/**
	 * Jedes Square auf der Map braucht einen Index, um jedem Objekt, das auf einem
	 * Square platziert wird, ein eindeutiges Objekt zuzuordnen
	 */
	public Map(String sessionName) {
		super(sessionName);
		squares = new Square[mapSize][mapSize];
		
		for(int x= 0; x < mapSize; x++) {
			for(int y = 0; y < mapSize; y++) {
				Square s = new Square(sessionName, x, y);
				squares[x][y] = s;
			}
		}
	}
	
	public void initAvailablePlayerSlots() {
		removeAvailablePlayerSlots();
		createAvailablePlayerSlots();
	}
	
	private void createAvailablePlayerSlots() {
		for(Square[] squares : getSquares()) {
			for(Square square : squares) {
				if(square.getPlaceableOnSquare() instanceof Trainstation) {
					addAvailablePlayerSlot();
				}
			}
		}
	}
	
	private void removeAvailablePlayerSlots() {
		availablePlayerSlots = 0;
	}
	
	public int getAvailablePlayerSlots() {
		return availablePlayerSlots;
	}
	
	public void addAvailablePlayerSlot() {
		availablePlayerSlots += 1;
	}
	
	public int getMapSize() {
		return mapSize;
	}

	public String getName() {
		return name;
	}

	public void changeName(String name) {
		this.name = name;
		notifyChangedName();
	}

	private void notifyChangedName() {
		MessageInformation messageInformation = new MessageInformation("UpdateNameOfMap");
		messageInformation.putValue("mapName", name);
		notifyChange(messageInformation);
	}
	
	public Square getSquare(int x, int y) {
		if(x >= 0 && x < squares.length && y >= 0 && y < squares.length)
			return squares[x][y];
		return null;
	}
	
	public Square[][] getSquares() {
		return squares;
	}

	public void setSquares(Square squares[][]) {
		this.squares = squares;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Map other = (Map) obj;
		if (mapSize != other.mapSize)
			return false;
		if (!Arrays.deepEquals(squares, other.squares))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mapSize;
		result = prime * result + Arrays.deepHashCode(squares);
		return result;
	}
	
	/**
	 * Holt das Placeable von Square oder Rail
	 * @param id Id des Placeables
	 * @return null, wenn Square leer ist
	 */
	public Placeable getPlaceableById(UUID id) {
		
		Placeable placeable = null;
		placeable = getPlaceableOnSquareById(id);
		if(placeable == null) {
			placeable = getPlaceableOnRailById(id);
		}
		return placeable;
	}

	public PlaceableOnSquare getPlaceableOnSquareById(UUID id) {
		for (Square[] squares : getSquares()) {
			for (Square square : squares) {
				PlaceableOnSquare placeableOnSquare = square.getPlaceableOnSquare();
				if (placeableOnSquare != null) {
					if (placeableOnSquare.getId().equals(id)) {
						return placeableOnSquare;
					}
				}
			}
		}
		return null;
	}
	
	public PlaceableOnRail getPlaceableOnRailById(UUID id) {
		for (Square[] squares : getSquares()) {
			for (Square square : squares) {
				PlaceableOnSquare placeableOnSquare = square.getPlaceableOnSquare();
				if (placeableOnSquare != null) {
					if (placeableOnSquare instanceof Rail) {
						Rail rail = (Rail)placeableOnSquare;
						if (rail.getPlaceableOnrail() instanceof Mine) {
							Mine mine = (Mine)rail.getPlaceableOnrail();
							if (mine.getId().equals(id)) {
								return mine;
							}
						}
					}	
				}
			}
		}
		return null;
	}

	public Square getSquareById(UUID id) {
		for (Square[] squares : getSquares()) {
			for (Square square : squares) {
				if (square.getId().equals(id)) {
					return square;
				}
			}
		}
		return null;
	}
	
	/**
	 * Setzt den Session Name für die Map und jedes Square
	 * @param sessionName
	 */
	public void setSessionNameForMapAndSquares(String sessionName) {
		this.sessionName = sessionName;
		
		for (Square[] squares : getSquares()) {
			for (Square square : squares) {
				square.setSessionName(sessionName);
			}
		}
	}

	/**
	 * Gibt ein zugehöriges neuse Square, nach dem Moven der Trainstation, für eine
	 * Rail zurück
	 * 
	 * @param trainstationSquare
	 * @param alignment
	 * @return Zugehöriges Square einer Rail
	 */
	private Square getTrainstationInteractiveGameObjectSquare(InteractiveGameObject trainstationInteractiveGameObject, Trainstation newTrainstation,
			int oldPlaceableOnSquareXPos, int oldPlaceableOnSquareYPos) {
		int trainstationRailXSpan = trainstationInteractiveGameObject.getXPos() - oldPlaceableOnSquareXPos;
		int trainstationRailYSpan = trainstationInteractiveGameObject.getYPos() - oldPlaceableOnSquareYPos;

		int newSquareX = newTrainstation.getXPos() + trainstationRailXSpan;
		int newSquareY = newTrainstation.getYPos() + trainstationRailYSpan;
		return getSquare(newSquareX, newSquareY);
	}
	
	/**
	 * Löst das Notify zum Verschieben einer Mine aus
	 * @param oldSquare
	 * @param newSquare
	 */
	public void movePlaceableOnRail(Square oldSquare, Square newSquare) {
		
		notifyMovedMine(oldSquare, newSquare);
	}
	
	/**
	 * Verschickt die Änderungen der verschobenen Mine an den Client
	 * @param oldSquare
	 * @param newSquare
	 */
	public void notifyMovedMine(Square oldSquare, Square newSquare) {
		MessageInformation message = new MessageInformation("MoveMine");
		message.putValue("oldXPos", oldSquare.getXIndex());
		message.putValue("oldYPos", oldSquare.getYIndex());
		message.putValue("newXPos", newSquare.getXIndex());
		message.putValue("newYPos", newSquare.getYIndex());
		Rail rail = (Rail) newSquare.getPlaceableOnSquare();
		message.putValue("alignment", rail.getAlignment().toString());
		notifyChange(message);
	}

	/**
	 * Verschiebt ein PlaceableOnSquare von oldSquareOfPlaceable auf newSquareOfPlaceable
	 * @param oldSquareOfPlaceable Altes Square von dem das PlaceableOnSquare verschoben werden soll
	 * @param newSquareOfPlaceable Neues Square auf den das PlaceableOnSquare verschoben werden soll
	 */
	public void movePlaceableOnSquare(Square oldSquareOfPlaceable, Square newSquareOfPlaceable) {
		if (Validator.validateMovePlaceableOnSquare(oldSquareOfPlaceable, newSquareOfPlaceable, this)) {
			InteractiveGameObject placeableOnSquare = (InteractiveGameObject) oldSquareOfPlaceable.getPlaceableOnSquare();
			int oldPlaceableOnSquareXPos = placeableOnSquare.getXPos();
			int oldPlaceableOnSquareYPos = placeableOnSquare.getYPos();
			oldSquareOfPlaceable.setPlaceableOnSquare(null);
			newSquareOfPlaceable.setPlaceableOnSquare((PlaceableOnSquare) placeableOnSquare);

			// entweder nur ids oder nur x und y, wir müssen uns entscheiden
			placeableOnSquare.setSquareId(newSquareOfPlaceable.getId());
			placeableOnSquare.setXPos(newSquareOfPlaceable.getXIndex());
			placeableOnSquare.setYPos(newSquareOfPlaceable.getYIndex());

			if (placeableOnSquare instanceof Rail) {
				Rail rail = (Rail) placeableOnSquare;
				// sections müssen auch die square-Änderung mitbekommen
				for (RailSection railSection : rail.getRailSectionList()) {
					railSection.changeSquare(newSquareOfPlaceable);
				}
				notifyMovedRail(oldSquareOfPlaceable, newSquareOfPlaceable);
			} else if (placeableOnSquare instanceof Trainstation) {
				Trainstation trainstation = (Trainstation) placeableOnSquare;
				// trainstationRails müssen auch die square-Änderung mitbekommen
				for (Rail trainstationRail : trainstation.getTrainstationRails()) {
					Square oldSquareOfRail = getSquare(trainstationRail.getXPos(), trainstationRail.getYPos());
					oldSquareOfRail.setPlaceableOnSquare(null);
					Square newSquareOfRail = getTrainstationInteractiveGameObjectSquare(trainstationRail, trainstation,
							oldPlaceableOnSquareXPos, oldPlaceableOnSquareYPos);
					trainstationRail.changeSquare(newSquareOfRail);
					newSquareOfRail.setPlaceableOnSquare(trainstationRail);
				}
				// der stock muss auch die square-Änderung mitbekommen
				Stock stock = trainstation.getStock();
				Square oldSquareOfStock = getSquare(stock.getXPos(), stock.getYPos());
				oldSquareOfStock.setPlaceableOnSquare(null);
				Square newSquareOfStock = getTrainstationInteractiveGameObjectSquare(stock, trainstation,
						oldPlaceableOnSquareXPos, oldPlaceableOnSquareYPos);
				stock.changeSquare(newSquareOfStock);
				newSquareOfStock.setPlaceableOnSquare(stock);
				
				notifyMovedTrainstation(oldSquareOfStock, newSquareOfStock, oldSquareOfPlaceable, newSquareOfPlaceable, trainstation);
				
				//The Crane likes to move it move it...
				Crane crane = trainstation.getCrane();
				Square newSquare = getTrainstationInteractiveGameObjectSquare(crane, trainstation, oldPlaceableOnSquareXPos, oldPlaceableOnSquareYPos);
				System.out.println("newSquare ("+ newSquare.getXIndex() +"/"+newSquare.getYIndex()+")");
				crane.moveCrane(newSquare);
				
				
				
				
				
				
				
				
				
				
			}
		} else {
			throw new NotMoveableException(String.format("PlaceableOnSquare von %s ist nicht auf %s verschiebbar",
					oldSquareOfPlaceable.toString(), newSquareOfPlaceable.toString()));
		}
	}

	private void notifyMovedTrainstation(Square oldSquareOfStock, Square newSquareOfStock, Square oldSquareOfPlaceable, Square newSquareOfPlaceable, Trainstation trainstation) {
		MessageInformation messageInformation = new MessageInformation("MoveTrainstation");
		messageInformation.putValue("oldXPos", oldSquareOfPlaceable.getXIndex());
		messageInformation.putValue("oldYPos", oldSquareOfPlaceable.getYIndex());
		messageInformation.putValue("newXPos", newSquareOfPlaceable.getXIndex());
		messageInformation.putValue("newYPos", newSquareOfPlaceable.getYIndex());
		
		// Stock alte und neue Coords mitgeben
		messageInformation.putValue("oldStockXPos", oldSquareOfStock.getXIndex());
		messageInformation.putValue("oldStockYPos", oldSquareOfStock.getYIndex());
		messageInformation.putValue("newStockXPos", newSquareOfStock.getXIndex());
		messageInformation.putValue("newStockYPos", newSquareOfStock.getYIndex());

		List<List<String>> trainstationRailsCoordinateList = new ArrayList<List<String>>();
		for (Rail trainstationRail : trainstation.getTrainstationRails()) {
			List<String> trainstationRailCoordinates = new ArrayList<String>();
			Square newTrainstationRailSquare = this.getSquareById(trainstationRail.getSquareId());
			trainstationRailCoordinates.add(trainstationRail.getId().toString());
			trainstationRailCoordinates.add(String.valueOf(newTrainstationRailSquare.getXIndex()));
			trainstationRailCoordinates.add(String.valueOf(newTrainstationRailSquare.getYIndex()));
			trainstationRailsCoordinateList.add(trainstationRailCoordinates);
		}
		messageInformation.putValue("trainstationRailsCoordinates", new Gson().toJson(trainstationRailsCoordinateList));
		notifyChange(messageInformation);
	}

	private void notifyMovedRail(Square oldSquare, Square newSquare) {
		MessageInformation messageInformation = new MessageInformation("MoveRail");
		messageInformation.putValue("oldXPos", oldSquare.getXIndex());
		messageInformation.putValue("oldYPos", oldSquare.getYIndex());
		messageInformation.putValue("newXPos", newSquare.getXIndex());
		messageInformation.putValue("newYPos", newSquare.getYIndex());
		notifyChange(messageInformation);
	}
}