package org.redalert1741.robotbase.auto.core;

import java.util.Map;

/**
 * The end condition for an {@link AutoMove}.
 */
public interface AutoMoveEnd {
    /**
     * Sets the arguments for the object to use. Actual usage depends on subclass
     * @param args Map of arguments
     */
    public void setArgs(Map<String, String> args);

    /**
     * Initializes the AutoMoveEnd.
     */
    public void start();

    /**
     * finds whether the end condition has been met.
     * @return end condition state
     */
    public boolean isFinished();
}
