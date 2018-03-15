package org.redalert1741.robotbase.wrapper;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;

import org.redalert1741.robotbase.config.Config;
import org.redalert1741.robotbase.config.Configurable;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;

public abstract class TalonSrxWrapper implements Loggable, Configurable {
    protected String logname;
    protected int timeout;
    
    public abstract void set(ControlMode mode, double value);

    public abstract void setInverted(boolean inverted);

    public abstract void follow(TalonSrxWrapper toFollow);

    public abstract double getOutputCurrent();

    public abstract double getBusVoltage();

    public abstract double get();

    public abstract ErrorCode setP(double pvalue);

    public abstract ErrorCode setI(double ivalue);

    public abstract ErrorCode setD(double dvalue);

    public abstract ErrorCode setF(double fvalue);

    public abstract ErrorCode configNominalOutputForward(double value);

    public abstract ErrorCode configNominalOutputReverse(double value);

    public abstract ErrorCode configPeakOutputForward(double value);

    public abstract ErrorCode configPeakOutputReverse(double value);

    public abstract int getPosition();
    
    public abstract double getVelocity();

    public abstract void setPosition(int pos);

    public abstract void setPhase(boolean phase);

    public abstract boolean getReverseLimit();

    public abstract boolean getForwardLimit();

    public abstract void setBrake(boolean brake);
    
    public abstract void setClosedLoopRampRate(double time);

    @Override
    public void setupLogging(DataLogger logger) {
        logger.addAttribute(logname+"_current");
        logger.addAttribute(logname+"_voltage");
        logger.addAttribute(logname+"_value");
        logger.addAttribute(logname+"_position");
        logger.addAttribute(logname+"_velocity");
    }

    @Override
    public void log(DataLogger logger) {
        logger.log(logname+"_current", getOutputCurrent());
        logger.log(logname+"_voltage", getBusVoltage());
        logger.log(logname+"_value", get());
        logger.log(logname+"_position", getPosition());
        logger.log(logname+"_velocity", getVelocity());
    }

    @Override
    public void reloadConfig(Config config) {
        timeout = config.getSetting("can_timeout", 10.0).intValue();
    }
}
