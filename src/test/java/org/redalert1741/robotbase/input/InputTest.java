package org.redalert1741.robotbase.input;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.redalert1741.robotbase.input.EdgeDetect;

class InputTest {
    /**
     * Tests {@link EdgeDetect} with no changes to state
     */
    @Test
    void edgeDetectNoChangeTest() {
        EdgeDetect edge = new EdgeDetect();
        for(int i = 0; i < 10; i++) {
            assertEquals(false, edge.check(false));
        }
    }

    /**
     * Tests {@link EdgeDetect} with one change to state
     */
    @Test
    void edgeDetectOneChangeTest() {
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
    void edgeDetectAlternateTest() {
        EdgeDetect edge = new EdgeDetect();
        for(int i = 0; i < 10; i++) {
            assertEquals(i%2!=0, edge.check(i%2!=0));
        }
    }
    
    /**
     * This just fails... #JustTestingThings
     */
    @Test
    void justFail() {
//        assertEquals(false, true);
    	assertEquals(true, true);
    }
}
