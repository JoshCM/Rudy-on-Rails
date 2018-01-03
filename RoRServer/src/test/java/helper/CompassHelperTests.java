/*
 * Copyright (c) 2018. Florian Treder
 */

package helper;

import models.game.Compass;
import models.helper.CompassHelper;
import org.junit.Assert;
import org.junit.Test;

public class CompassHelperTests {
    @Test
    public void getRandomCompassDirection() {
        Compass node = CompassHelper.getRandomNode();
        System.out.println(node.toString());
        Assert.assertTrue(node.getClass().isEnum());
    }
}
