package models.game;

import java.util.ArrayList;
import java.util.List;

import communication.MessageInformation;
import models.base.ModelBase;


/**
 * Ein Square hält alle relevanten Objekte.
 * Ein Square ist Observer für alle beinhalteten Objekte
 * Ein Square ist Observable.
 */
public class Square extends ModelBase {
    private PlaceableOnSquare placeableOnSquare = null;

    public Square(int xIndex, int yIndex) {
        // müsste übergeben werden an Oberklasse, die was damit anfangen kann. Könnte ganz nützlich sein.
    }

    public Square() {

    }


    public void deletePlaceable() {
        this.placeableOnSquare = null;
        notifyDeletePlaceable();
    }


    private void notifyDeletePlaceable() {
        MessageInformation messageInfo = new MessageInformation("DeletePlaceable");
        // TODO: Später haben wir die richtigen SquareIds im Client, im Moment noch nicht!!!
        notifyObservers();
        // notifyChange(messageInfo);
    }

    /**
     * Gibt die benachbarten Squares zurück (Links, Rechts, Oben, Unten)
     *
     * @return Die Squares werden als Liste zurückgegeben
     */
    public List<Square> getNeighbouringSquares() {

        List<Square> neighbouringSquares = new ArrayList<Square>();

        /* TODO: Wieso kennt ein Square seinen Nachbar und die ganze Map und wofür? Ahh... Wahrscheinlich für die Jython-Sachen später? Sollte aber nicht hier stehen.
        // Linkes Square
        if (xIndex - 1 >= 0) {
            neighbouringSquares.add(map.getSquare(xIndex - 1, yIndex));
        }

        // Rechtes Square
        if (xIndex + 1 < map.getMapSize()) {
            neighbouringSquares.add(map.getSquare(xIndex + 1, yIndex));
        }

        // Oberes Square
        if (yIndex - 1 >= 0) {
            neighbouringSquares.add(map.getSquare(xIndex, yIndex - 1));
        }

        // Unteres Square
        if (yIndex + 1 < map.getMapSize()) {
            neighbouringSquares.add(map.getSquare(xIndex, yIndex + 1));
        }*/

        return neighbouringSquares;
    }

    public void setPlaceableOnSquare(PlaceableOnSquare placeable) {
        this.placeableOnSquare = placeable;
    }

    public PlaceableOnSquare getPlaceableOnSquare() {
        return placeableOnSquare;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Square other = (Square) obj;

        // TODO: Muss eventuell angepasst werden.
        if (placeableOnSquare == null) {
            if (other.placeableOnSquare != null)
                return false;
        } else if (!placeableOnSquare.equals(other.placeableOnSquare)) {
            return false;
        } else {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Square [placeableOnSquare=" + placeableOnSquare + "]";
    }
}
