package org.redalert1741.robotbase.input;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.redalert1741.robotbase.input.EdgeDetect;

public class InputTest {
    /**
     * Tests {@link EdgeDetect} with no changes to state
     */
    @Test
    public void edgeDetectNoChangeTest() {
        EdgeDetect edge = new EdgeDetect();
        for(int i = 0; i < 10; i++) {
            assertEquals(false, edge.check(false));
        }
    }

    /**
     * Tests {@link EdgeDetect} with one change to state
     */
    @Test
    public void edgeDetectOneChangeTest() {
        EdgeDetect edge = new EdgeDetect();
        for(int i = 0; i < 5; i++) {
            assertEquals(false, edge.check(false));
        }
        assertEquals(true, edge.check(true));
        for(int i = 0; i < 5; i++) {
            assertEquals(false, edge.check(true));
        }
    }

    /**
     * Tests {@link EdgeDetect} by alternating states, starting with false.
     */
    @Test
    public void edgeDetectAlternateTest() {
        EdgeDetect edge = new EdgeDetect();
        for(int i = 0; i < 10; i++) {
            assertEquals(i%2!=0, edge.check(i%2!=0));
        }
    }
}
