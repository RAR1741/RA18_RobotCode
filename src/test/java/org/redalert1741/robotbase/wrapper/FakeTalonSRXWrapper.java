package org.redalert1741.robotbase.wrapper;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class FakeTalonSRXWrapper implements TalonSRXWrapper {
    public FakeTalonSRXWrapper() {
        mode = ControlMode.PercentOutput;
        value = 0;
        inverted = false;
        toFollow = null;
    }
    
    public ControlMode mode;
    public double value;

    @Override
    public void set(ControlMode mode, double value) {
        this.mode = mode;
        this.value = value;
        if(toFollow != null) {
            toFollow.set(mode, value);
        }
    }

    public boolean inverted;

    @Override
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public TalonSRXWrapper toFollow;

    @Override
    public void follow(TalonSRXWrapper toFollow) {
        this.toFollow = toFollow;
    }

    @Override
    public double getOutputCurrent() {
        return 0;
    }

    @Override
    public double getBusVoltage() {
        return 0;
    }

    @Override
    public double get() {
        return value;
    }

}
