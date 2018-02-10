package org.redalert1741.robotbase.wrapper;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;

import org.redalert1741.robotbase.config.Configurable;
import org.redalert1741.robotbase.logging.Loggable;

public abstract class TalonSrxWrapper implements Loggable, Configurable  {
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
}
