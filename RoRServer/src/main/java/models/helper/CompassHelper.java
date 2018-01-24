/*
 * Copyright (c) 2018. Florian Treder
 */

package models.helper;

import java.util.Random;
import exceptions.InvalidCompassDirectionString;
import models.game.Compass;

public class CompassHelper {

	/**
	 * @param node
	 *            Gültige Werte sind die Strings N, E, S, W, NORTH, EAST, SOUTH,
	 *            WEST
	 * @return Gibt Compass.Direction zurück. Wirft Fehler wenn Eingabe ungültig
	 *         ist.
	 */
	public static Compass convertStringToNode(String node) {
		if (node.equalsIgnoreCase("NORTH") || node.equalsIgnoreCase("N"))
			return Compass.NORTH;
		if (node.equalsIgnoreCase("EAST") || node.equalsIgnoreCase("E"))
			return Compass.EAST;
		if (node.equalsIgnoreCase("SOUTH") || node.equalsIgnoreCase("S"))
			return Compass.SOUTH;
		if (node.equalsIgnoreCase("WEST") || node.equalsIgnoreCase("W"))
			return Compass.WEST;
		throw new InvalidCompassDirectionString("The Compass Direction you tried to use is invalid");
	}

	public static Compass getRandomNode() {
		Random generator = new Random();
		int x = generator.nextInt(Compass.values().length);
		return Compass.values()[x];
	}

	/**
	 * Gibt die n�chste Richtung zur�ck (kann zum Drehen benutzt werden
	 * 
	 * @param right
	 *            true, wenn die �bergebene Richtung nach rechts verschoben werden
	 *            soll, false sonst
	 * @param alignment
	 *            die zu drehende Richtung
	 * @return
	 */
	public static Compass rotateCompass(boolean right, Compass alignment) {
		int newIndex;

		if (right) {
			newIndex = ((alignment.ordinal() + 1) % Compass.values().length);
		} else {
			newIndex = ((alignment.ordinal() - 1) % Compass.values().length);
			if (newIndex < 0) {
				newIndex += Compass.values().length;
			}
		}
		return Compass.values()[newIndex];
	}

	public static int getRealXForDirection(Compass direction, int xPos, int yPos, int sideways, int forward) {
		switch (direction) {
		case NORTH:
			return xPos + sideways;
		case EAST:
			return xPos + forward;
		case SOUTH:
			return xPos - sideways;
		case WEST:
			return xPos - forward;
		default:
			return -1;
		}
	}

	public static int getRealYForDirection(Compass direction, int xPos, int yPos, int sideways, int forward) {
		switch (direction) {
		case NORTH:
			return yPos - forward;
		case EAST:
			return yPos + sideways;
		case SOUTH:
			return yPos + forward;
		case WEST:
			return yPos - sideways;
		default:
			return -1;
		}
	}
}
