package org.redalert1741.robotbase.wrapper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class RealTalonSRXWrapper implements TalonSRXWrapper {
    private TalonSRX talon;
    private double value;

    public RealTalonSRXWrapper(int id) {
        talon = new TalonSRX(id);
        value = 0;
    }
    
    @Override
    public void set(ControlMode mode, double value) {
        talon.set(mode, value);
        this.value = value;
    }

    @Override
    public void setInverted(boolean inverted) {
        talon.setInverted(inverted);
    }

    @Override
    public void follow(TalonSRXWrapper toFollow) {
        if(toFollow instanceof RealTalonSRXWrapper) {
            talon.follow(((RealTalonSRXWrapper)toFollow).talon);
        }
    }

    @Override
    public double getOutputCurrent() {
        return talon.getOutputCurrent();
    }

    @Override
    public double getBusVoltage() {
        return talon.getBusVoltage();
    }

    @Override
    public double get() {
        return value;
    }

}
