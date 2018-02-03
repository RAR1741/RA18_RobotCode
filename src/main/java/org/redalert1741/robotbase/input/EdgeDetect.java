package org.redalert1741.robotbase.input;

/**
 * Used to detect rising edges, such as button presses.
 */
public class EdgeDetect {
    private boolean state;

    /**
     * Initializes the EdgeDetect, assuming a low start.
     */
    public EdgeDetect() {
        state = false;
    }

    /**
     * Returns true when input has gone from false to true.
     * @param in input
     * @return rising edge detection
     */
    public boolean check(boolean in) {
        boolean out;

        if(in && state) { out = true; }
        else { out = false; }

        if(in) { state = false; }
        else { state = true; }
        return out;
    }
}
