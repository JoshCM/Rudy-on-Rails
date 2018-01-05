package models.helper;

import exceptions.InvalidCompassDirectionString;
import exceptions.ResourceException;
import models.game.Compass;
import models.game.ResourceType;

public class StringConverter {
    public static ResourceType convertToResource(String resourceTypeString) {
        if (resourceTypeString.equalsIgnoreCase("COAL") || resourceTypeString.equalsIgnoreCase("C")) return ResourceType.COAL;
        if (resourceTypeString.equalsIgnoreCase("GOLD") || resourceTypeString.equalsIgnoreCase("G")) return ResourceType.GOLD;
        throw new ResourceException("Couldn't convert " + resourceTypeString + " to an proper ENUM");
    }

    /**
     * @param node Gültige Werte sind die Strings N, E, S, W, NORTH, EAST, SOUTH, WEST
     * @return Gibt Compass.Direction zurück. Wirft Fehler wenn Eingabe ungültig ist.
     */
    public static Compass convertStringToCompass(String node) {
        if (node.equalsIgnoreCase("NORTH") || node.equalsIgnoreCase("N")) return Compass.NORTH;
        if (node.equalsIgnoreCase("EAST") || node.equalsIgnoreCase("E")) return Compass.EAST;
        if (node.equalsIgnoreCase("SOUTH") || node.equalsIgnoreCase("S")) return Compass.SOUTH;
        if (node.equalsIgnoreCase("WEST") || node.equalsIgnoreCase("W")) return Compass.WEST;
        throw new InvalidCompassDirectionString("The Compass Direction you tried to use is invalid");
    }
}
