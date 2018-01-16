/*
 * Copyright (c) 2018. Florian Treder
 */

package models.helper;

import exceptions.InvalidStringForEnumType;
import models.game.Compass;
import models.game.RailSection;
import models.game.RailSectionStatus;

import java.util.Random;

public class StringToEnumConverter {

    /**
     * @param node Gültige Werte sind die Strings N, E, S, W, NORTH, EAST, SOUTH, WEST
     * @return Gibt Compass.Direction zurück. Wirft Fehler wenn Eingabe ungültig ist.
     */
    public static Compass convertStringToNode(String node) {
        if (node.equalsIgnoreCase("NORTH") || node.equalsIgnoreCase("N")) return Compass.NORTH;
        if (node.equalsIgnoreCase("EAST") || node.equalsIgnoreCase("E")) return Compass.EAST;
        if (node.equalsIgnoreCase("SOUTH") || node.equalsIgnoreCase("S")) return Compass.SOUTH;
        if (node.equalsIgnoreCase("WEST") || node.equalsIgnoreCase("W")) return Compass.WEST;
        throw new InvalidStringForEnumType("The Compass Direction you tried to use is invalid");
    }

    public static RailSectionStatus convertStringToRailSectionStatus(String status) {
        if (status.equalsIgnoreCase("ACTIVE"))  return RailSectionStatus.ACTIVE;
        if (status.equalsIgnoreCase("INACTIVE"))  return RailSectionStatus.INACTIVE;
        if (status.equalsIgnoreCase("FORBIDDEN"))  return RailSectionStatus.FORBIDDEN;
        throw new InvalidStringForEnumType("Incorrect status for rail section");
    }

    public static Compass getRandomNode() {
        Random generator = new Random();
        int x = generator.nextInt(Compass.values().length);
        return Compass.values()[x];
    }

}
