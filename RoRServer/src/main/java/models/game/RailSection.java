package models.game;

import exceptions.InvalidModelOperationException;
import models.base.ModelBase;
import models.helper.orientationHelper;

/**
 * Klasse für ein Schienenstueck mit "Eingang" und "Ausgang"
 */
public class RailSection extends ModelBase {
    private Compass node1;
    private Compass node2;
    private boolean isDrivable;


    public RailSection(Compass node1, Compass node2) {
        if (node1 == node2) {
            throw new InvalidModelOperationException(
                    "RailSectionPositions are equal; node1: " + node1.toString() + ", node2: " + node2.toString());
        }
        this.node1 = node1;
        this.node2 = node2;
        this.isDrivable = true;
    }

    /**
     * Erwartet Strings N,E,S,W oder NORTH, EAST, WEST, SOUTH
     *
     * @param node1
     * @param node2
     */
    public RailSection(String node1, String node2) {
        this(orientationHelper.convertStringToNode(node1), orientationHelper.convertStringToNode(node2));
    }

    /**
     * Erwartet einen String mit GENAU zwei Zeichen!
     *
     * @param qs
     */
    public RailSection(String qs) {
        this(qs.substring(0, 0), qs.substring(1, 1));
    }


    public Compass getNode1() {
        return node1;
    }

    public Compass getNode2() {
        return node2;
    }

    /**
     * Rotiert die RailSectionPositions.
     * Rechtsherum z.B. North zu East
     *
     * @param right Rechts herum (true) oder links herum (false)
     */
    public void rotate(boolean right) {
        node1 = rotateRailSectionPosition(node1, right);
        node2 = rotateRailSectionPosition(node2, right);
        notifyNodesUpdated();
    }

    public void rotate(boolean right, boolean notYet) {
        node1 = rotateRailSectionPosition(node1, right);
        node2 = rotateRailSectionPosition(node2, right);
        notifyNodesUpdated();
    }


    /**
     * Wenn eine Schienensektion befahrbar war, dann stelle das Verhalten um.
     */
    public void toggleIsDrivable() {
        this.isDrivable = !this.isDrivable;
    }

    public boolean getIsDrivable() {
        return this.isDrivable;
    }

    /**
     * Benachrichtigt alle Observer auf eine Änderung eines Parameters.
     */
    private void notifyNodesUpdated() {
        notifyObservers();
    }


    /**
     * Rotiert eine Railsection nach rechts, wenn right True ist.
     *
     * @param railSectionPosition
     * @param right
     * @return
     */
    private Compass rotateRailSectionPosition(Compass railSectionPosition, boolean right) {
        int newIndex;

        if (right) {
            newIndex = ((railSectionPosition.ordinal() + 1) % Compass.values().length);
        } else {
            newIndex = ((railSectionPosition.ordinal() - 1) % Compass.values().length);
            if (newIndex < 0) {
                newIndex += Compass.values().length;
            }
        }

        return Compass.values()[newIndex];
    }


    @Override
    public String toString() {
        return "Node1: " + node1 + " Node2 " + node2;
    }

    // TODO: Wofür braucht man hier eine Hashfunktion? Gibt doch nur NS, NW, NE, WS, WE, ES und viceversa
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((node1 == null) ? 0 : node1.hashCode());
        result = prime * result + ((node2 == null) ? 0 : node2.hashCode());
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
        RailSection other = (RailSection) obj;

        // Wenn zwei Sections äquivalent sind -> return true
        if (node1.equals(other.node1)) {
            if (node2.equals(other.node2)) {
                return true;
            } else {
                return node2.equals(other.node1) && node1.equals(other.node2);
            }
        } else {
            return node2.equals(other.node1) && node1.equals(other.node2);
        }
    }

}
