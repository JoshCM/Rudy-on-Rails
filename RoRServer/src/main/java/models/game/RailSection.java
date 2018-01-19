package models.game;

import java.util.StringTokenizer;
import java.util.UUID;

import communication.MessageInformation;
import exceptions.InvalidModelOperationException;
import models.base.ModelBase;
import models.helper.StringToEnumConverter;

/**
 * Klasse für ein Schienenstueck mit "Eingang" und "Ausgang"
 */
public class RailSection extends ModelBase {
    private UUID squareId;
    private int squareXPos;
    private int squareYPos;
    private UUID railId;
    private Compass node1;
    private Compass node2;
    private boolean isDrivable;
    private RailSectionStatus status = RailSectionStatus.ACTIVE;

    // TODO: hier muss placeableOnSquareSection
    public RailSection(String sessionName, Rail rail, Compass node1, Compass node2, RailSectionStatus railSectionStatus) {
        super(sessionName);

        if (node1 == node2) {
            throw new InvalidModelOperationException(
                    "RailSectionPositions are equal; node1: " + node1.toString() + ", node2: " + node2.toString());
        }

        this.railId = rail.getId();
        this.squareId = rail.getSquareId();
        this.squareXPos = rail.getXPos();
        this.squareYPos = rail.getYPos();
        this.node1 = node1;
        this.node2 = node2;
        this.isDrivable = true;
        this.status = railSectionStatus;
    }

    /**
     * @param sessionName
     * @param rail
     * @param node1                 Gültige Werte sind N,E,S,W und NORTH, EAST, WEST, SOUTH
     * @param node2                 Gültige Werte sind N,E,S,W und NORTH, EAST, WEST,
     * @param railSectionStatus     Gülstige Werte sind ACTIVE; INACTIVE, FORBIDDEN
     */
    public RailSection(String sessionName, Rail rail, String node1, String node2, String railSectionStatus) {
        this(sessionName, rail, StringToEnumConverter.convertStringToNode(node1), StringToEnumConverter.convertStringToNode(node2),
                StringToEnumConverter.convertStringToRailSectionStatus(railSectionStatus));
    }


    public UUID getId() {
        return railId;
    }

    public Compass getNode1() {
        return node1;
    }

    public Compass getNode2() {
        return node2;
    }

    public RailSectionStatus getRailSectionStatus() {return status;}

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
    }

    public void switchActitityStatus()
    {
        if (status == RailSectionStatus.ACTIVE) {
            status = RailSectionStatus.INACTIVE;
        } else if (status == RailSectionStatus.INACTIVE) {
            status = RailSectionStatus.ACTIVE;
        }
        notifyNodesUpdated();
    }


    /**
     * @return String, der sagt ob Schiene Befahrbar ist oder nicht.
     */
    public String getDrivableString() {
        if (this.isDrivable) {
            return "true";
        } else {
            return "false";
        }
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
        MessageInformation messageInformation = new MessageInformation("UpdateNodesOfRailSection");
        messageInformation.putValue("squareId", squareId);
        messageInformation.putValue("xPos", squareXPos);
        messageInformation.putValue("yPos", squareYPos);
        messageInformation.putValue("railSectionId", getId().toString());
        messageInformation.putValue("node1", node1.toString());
        messageInformation.putValue("node2", node2.toString());
        messageInformation.putValue("railSectionStatus", status.toString());
        messageInformation.putValue("isDrivable", getDrivableString());
        notifyChange(messageInformation);
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

    /**
     * Verschiebt eine RailSection auf ein neues Square.
     *
     * @param newSquare
     */
    public void changeSquare(Square newSquare) {
        this.squareId = newSquare.getId();
        this.squareXPos = newSquare.getXIndex();
        this.squareYPos = newSquare.getYIndex();
    }

    @Override
    public String toString() {
        return "Node1: " + node1 + " Node2 " + node2 + " railID: " + railId;
    }

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
        // System.out.println("THIS: " + this.toString() + " || OTHER: " + other.toString());

        if (node1.equals(other.node1)) {
            if (node2.equals(other.node2)) {
                return true;
            } else {
                if (node2.equals(other.node1) && node1.equals(other.node2)) {
                    return true;
                }
                return false;
            }
        } else {
            if (node2.equals(other.node1) && node1.equals(other.node2)) {
                return true;
            } else {
                return false;
            }
        }
    }

}
