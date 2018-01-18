package org.redalert1741.robotbase.logging;

public interface Loggable {
    public abstract void setupLogging(DataLogger logger);
    public abstract void log(DataLogger logger);
}
