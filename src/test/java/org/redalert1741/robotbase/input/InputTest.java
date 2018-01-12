package org.redalert1741.robotbase.input;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.redalert1741.robotBase.input.EdgeDetect;

class InputTest {

	/**
	 * Tests {@link EdgeDetect} with no changes to state
	 */
    @Test
    void edgeDetectNoChangeTest() {
    	EdgeDetect edge = new EdgeDetect();
        for(int i = 0; i < 10; i++) {
        	assertEquals(false, edge.Check(false));
        }
    }
    /**
     * Tests {@link EdgeDetect} with one change to state
     */
    @Test
    void edgeDetectOneChangeTest() {
    	EdgeDetect edge = new EdgeDetect();
        for(int i = 0; i < 5; i++) {
        	assertEquals(false, edge.Check(false));
        }
        assertEquals(true, edge.Check(true));
        for(int i = 0; i < 5; i++) {
        	assertEquals(false, edge.Check(true));
        }
    }
    
    /**
     * Tests {@link EdgeDetect} by alternating states, starting with false.
     */
    @Test
    void edgeDetectAlternateTest() {
    	EdgeDetect edge = new EdgeDetect();
        for(int i = 0; i < 10; i++) {
        	assertEquals(i%2!=0, edge.Check(i%2!=0));
        }
    }

}
