/*
 * Copyright (c) 2018. Florian Treder
 */

package models.helper;

import models.game.Compass;

import java.util.Random;

public class orientationHelper {

    public static Compass getRandomNode() {
        Random generator = new Random();
        int x = generator.nextInt(Compass.values().length);
        return Compass.values()[x];
    }

}
