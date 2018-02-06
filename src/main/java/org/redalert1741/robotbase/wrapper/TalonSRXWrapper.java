package org.redalert1741.robotbase.wrapper;

import com.ctre.phoenix.motorcontrol.ControlMode;

public interface TalonSRXWrapper {
    public void set(ControlMode mode, double value);

    public void setInverted(boolean inverted);

    public void follow(TalonSRXWrapper toFollow);

    public double getOutputCurrent();

    public double getBusVoltage();

    public double get();
}
