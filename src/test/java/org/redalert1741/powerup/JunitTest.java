package org.redalert1741.powerup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class JunitTest {

    @Test
    public void myFirstTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void mySecondTest() {
        assertNotEquals(1, 1+1);
    }

}
