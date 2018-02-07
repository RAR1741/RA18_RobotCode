package org.redalert1741.robotbase.wrapper;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class FakeTalonSrxWrapper implements TalonSrxWrapper {
    public FakeTalonSrxWrapper() {
        mode = ControlMode.PercentOutput;
        value = 0;
        inverted = false;
        toFollow = null;
        follower = null;
    }
    
    public ControlMode mode;
    public double value;

    @Override
    public void set(ControlMode mode, double value) {
        value = Math.max(-1, Math.min(1, value)); //clamp
        this.mode = mode;
        this.value = value;
        if(follower != null) {
            follower.set(mode, value);
        }
    }

    public boolean inverted;

    @Override
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    public TalonSrxWrapper toFollow;
    public TalonSrxWrapper follower;

    @Override
    public void follow(TalonSrxWrapper toFollow) {
        this.toFollow = toFollow;
        ((FakeTalonSrxWrapper) toFollow).follower = this;
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
