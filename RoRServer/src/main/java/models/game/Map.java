package models.game;

import models.base.InteractiveGameObject;
import models.base.ModelBase;
import models.base.InterActiveGameModel;
import models.config.GameSettings;
import models.helper.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import communication.MessageInformation;
import exceptions.NotMoveableException;


// TODO: Messages für Beispielsweise Bahnhof müssen woanders erstellt werden.

/**
 * Klasse, die das Spielfeld darstellt und aus Feldern (Squares) besteht
 */
public class Map extends ModelBase {
    private String mapName;
    private Square squares[][];
    private final int mapSize = 50;


    /**
     * Jedes Square auf der Map braucht einen Index, um jedem Objekt, das auf einem
     * Square platziert wird, ein eindeutiges Objekt zuzuordnen
     */
    public Map(String mapname) {
        squares = new Square[mapSize][mapSize];

        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                Square s = new Square();
                squares[x][y] = s;
            }
        }
    }

    public Map() {
        this(GameSettings.DEF_MAP_NAME);
    }

    public int getMapSize() {
        return mapSize;
    }

    public String getMapName() {
        return mapName;
    }

    public void ChangeName(String name) {
        this.mapName = name;
        notifyChangedName();
    }

    private void notifyChangedName() {
        MessageInformation messageInformation = new MessageInformation("UpdateNameOfMap");
        messageInformation.putValue("mapName", mapName);
        notifyObservers();
        // notifyChange(messageInformation);
    }

    public Square getSquare(int x, int y) {
        return squares[x][y];
    }

    public Square[][] getSquares() {
        return squares;
    }


    // TODO: Wann müssen Maps innerhalb von Sessions verglichen werden? -> Wenn man später in EditorSession einsteigt?
    // TODO: FIXME: Gibt es einen UnitTest dafür?
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
        return Arrays.deepEquals(squares, other.squares);
    }

    // TODO: FIXME: Wofür braucht Map nen Hashcode?
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + mapSize;
        result = prime * result + Arrays.deepHashCode(squares);
        return result;
    }

    public PlaceableOnSquare getPlaceableById(UUID id) {
        for (Square[] squares : getSquares()) {
            for (Square square : squares) {
                PlaceableOnSquare placeableOnSquare = square.getPlaceableOnSquare();
                if (placeableOnSquare != null) {
                    if (placeableOnSquare.getID().equals(id)) {
                        return placeableOnSquare;
                    }
                }
            }
        }
        return null;
    }

    public Square getSquareById(UUID id) {
        for (Square[] squares : getSquares()) {
            for (Square square : squares) {
                if (square.getID().equals(id)) {
                    return square;
                }
            }
        }
        return null;
    }


    // TODO: Muss gefixt werden.
    /**
     * Gibt ein zugehöriges neuse Square, nach dem Moven der Trainstation, für eine
     * Rail zurück
     *
     * @return Zugehöriges Square einer Rail
     */
    private Square getTrainstationRailSquare(Rail trainstationRail, Trainstation newTrainstation,
                                             int oldPlaceableOnSquareXPos, int oldPlaceableOnSquareYPos) {
        int trainstationRailXSpan = trainstationRail.getX() - oldPlaceableOnSquareXPos;
        int trainstationRailYSpan = trainstationRail.getY() - oldPlaceableOnSquareYPos;

        int newSquareX = newTrainstation.getX() + trainstationRailXSpan;
        int newSquareY = newTrainstation.getY() + trainstationRailYSpan;
        return getSquare(newSquareX, newSquareY);
    }

    // TODO: Muss gefixt werden.
    /**
     * Verschiebt ein PlaceableOnSquare von oldSquareOfPlaceable auf newSquareOfPlaceable
     *
     * @param oldSquareOfPlaceable Altes Square von dem das PlaceableOnSquare verschoben werden soll
     * @param newSquareOfPlaceable Neues Square auf den das PlaceableOnSquare verschoben werden soll
     */
    public void movePlaceableOnSquare(Square oldSquareOfPlaceable, Square newSquareOfPlaceable) {
        if (Validator.validateMovePlaceableOnSquare(oldSquareOfPlaceable, newSquareOfPlaceable, this)) {
            InteractiveGameObject placeableOnSquare = (InteractiveGameObject) oldSquareOfPlaceable.getPlaceableOnSquare();
            int oldPlaceableOnSquareXPos = placeableOnSquare.getX();
            int oldPlaceableOnSquareYPos = placeableOnSquare.getY;
            oldSquareOfPlaceable.setPlaceableOnSquare(null);
            newSquareOfPlaceable.setPlaceableOnSquare((PlaceableOnSquare) placeableOnSquare);

            // entweder nur ids oder nur x und y, wir müssen uns entscheiden
            placeableOnSquare.setX(newSquareOfPlaceable.getX());
            placeableOnSquare.setY(newSquareOfPlaceable.getY());

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
                    Square oldSquareOfRail = getSquare(trainstationRail.getX(), trainstationRail.getY);
                    oldSquareOfRail.setPlaceableOnSquare(null);
                    Square newSquareOfRail = getTrainstationRailSquare(trainstationRail, trainstation,
                            oldPlaceableOnSquareXPos, oldPlaceableOnSquareYPos);
                    trainstationRail.changeSquare(newSquareOfRail);
                    newSquareOfRail.setPlaceableOnSquare(trainstationRail);
                }
                notifyMovedTrainstation(oldSquareOfPlaceable, newSquareOfPlaceable, trainstation);
            }
        } else {
            throw new NotMoveableException(String.format("PlaceableOnSquare von %s ist nicht auf %s verschiebbar",
                    oldSquareOfPlaceable.toString(), newSquareOfPlaceable.toString()));
        }
    }

    private void notifyMovedTrainstation(Square oldSquare, Square newSquare, Trainstation trainstation) {
        // TODO: Trainstation und Rails einzeln moven als Command
        MessageInformation messageInformation = new MessageInformation("MoveTrainstation");
        messageInformation.putValue("oldXPos", oldSquare.getX());
        messageInformation.putValue("oldYPos", oldSquare.getY());
        messageInformation.putValue("newXPos", newSquare.getX());
        messageInformation.putValue("newYPos", newSquare.getY());

        List<List<String>> trainstationRailsCoordinateList = new ArrayList<List<String>>();
        for (Rail trainstationRail : trainstation.getTrainstationRails()) {
            List<String> trainstationRailCoordinates = new ArrayList<String>();
            Square newTrainstationRailSquare = this.getSquareById(trainstationRail.getSquareId());
            trainstationRailCoordinates.add(trainstationRail.getID().toString());
            trainstationRailCoordinates.add(String.valueOf(newTrainstationRailSquare.getX()));
            trainstationRailCoordinates.add(String.valueOf(newTrainstationRailSquare.getY()));
            trainstationRailsCoordinateList.add(trainstationRailCoordinates);
        }
        messageInformation.putValue("trainstationRailsCoordinates", new Gson().toJson(trainstationRailsCoordinateList));
        notifyChange(messageInformation);
    }

    private void notifyMovedRail(Square oldSquare, Square newSquare) {
        MessageInformation messageInformation = new MessageInformation("MoveRail");
        messageInformation.putValue("oldXPos", oldSquare.getX());
        messageInformation.putValue("oldYPos", oldSquare.getY());
        messageInformation.putValue("newXPos", newSquare.getX());
        messageInformation.putValue("newYPos", newSquare.getY());
        notifyChange(messageInformation);
    }

    private void setX(int X) {
        //
    }

    private void setY(int y) {
        //
    }

    private int getX() {
        return -1; // TODO: FALSCH!
    }

    @Override
    public void update(InterActiveGameModel o, Object arg) {
        // Informiere Session über Update
    }
}