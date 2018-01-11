import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.redalert1741.powerup.Robot;

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
