package org.redalert1741.robotbase.logging;

/**
 * An interface that allows objects to be logged.
 * @see DataLogger
 */
public interface Loggable {
    /**
     * Sets up logging for Loggable.
     * Should only call {@link DataLogger#addAttribute(String)}.
     * @param logger logger to setup for
     */
    public abstract void setupLogging(DataLogger logger);

    /**
     * Logs the Loggable.
     * Should only call {@link DataLogger#log(String, Object)}
     * @param logger logger to log for
     */
    public abstract void log(DataLogger logger);
}
