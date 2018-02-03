package org.redalert1741.robotbase.auto.core;

import java.util.Map;

/**
 * The action in an {@link AutoMove}.
 */
public interface AutoMoveMove {
    /**
     * Sets the arguments for the object to use. Actual usage depends on subclass
     * @param args Map of arguments
     */
    public void setArgs(Map<String, String> args);

    /**
     * Initializes the AutoMoveMove.
     */
    public void start();

    /**
     * Runs the AutoMoveMove, called iteratively.
     */
    public void run();

    /**
     * Stop doing whatever the defined action is.
     */
    public void stop();
}
