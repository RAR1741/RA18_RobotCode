package org.redalert1741.robotbase.wrapper;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;

public abstract class DoubleSolenoidWrapper implements Loggable {
    protected String logname;

    public abstract void set(Value value);

    public abstract Value get();

    @Override
    public void setupLogging(DataLogger logger) {
        logger.addAttribute(logname+"_state");
    }

    @Override
    public void log(DataLogger logger) {
        logger.log(logname+"_state", get());
    }
}
