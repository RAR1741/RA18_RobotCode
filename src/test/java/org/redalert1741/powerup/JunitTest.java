package org.redalert1741.powerup;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JunitTest {

    @Test
    void myFirstTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void mySecondTest() {
        assertNotEquals(1, 1+1);
    }

}
