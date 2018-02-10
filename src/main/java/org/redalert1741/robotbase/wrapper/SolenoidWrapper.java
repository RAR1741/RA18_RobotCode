package org.redalert1741.robotbase.wrapper;

import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;

public abstract class SolenoidWrapper implements Loggable {
    protected String logname;

    public abstract void set(boolean value);

    public abstract boolean get();

    @Override
    public void setupLogging(DataLogger logger) {
        logger.addAttribute(logname+"_state");
    }

    @Override
    public void log(DataLogger logger) {
        logger.log(logname+"_state", get());
    }
}
