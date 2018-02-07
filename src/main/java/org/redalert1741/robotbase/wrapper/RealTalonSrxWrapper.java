package org.redalert1741.robotbase.wrapper;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class RealTalonSrxWrapper implements TalonSrxWrapper {
    private TalonSRX talon;
    private double value;

    public RealTalonSrxWrapper(int id) {
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
    public void follow(TalonSrxWrapper toFollow) {
        if(toFollow instanceof RealTalonSrxWrapper) {
            talon.follow(((RealTalonSrxWrapper)toFollow).talon);
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
