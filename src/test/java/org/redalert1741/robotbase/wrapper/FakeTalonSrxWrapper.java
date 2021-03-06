package org.redalert1741.robotbase.wrapper;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;

public class FakeTalonSrxWrapper extends TalonSrxWrapper {
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

    public double pValue;
    @Override
    public ErrorCode setP(double pValue) {
        this.pValue = pValue;
        return ErrorCode.OK;
    }

    public double iValue;
    @Override
    public ErrorCode setI(double iValue) {
        this.iValue = iValue;
        return ErrorCode.OK;
    }

    public double dValue;
    @Override
    public ErrorCode setD(double dValue) {
        this.dValue = dValue;
        return ErrorCode.OK;
    }

    public double fValue;
    @Override
    public ErrorCode setF(double fValue) {
        this.fValue = fValue;
        return ErrorCode.OK;
    }

    public double nomOutForward;
    @Override
    public ErrorCode configNominalOutputForward(double value) {
        nomOutForward = value;
        return ErrorCode.OK;
    }

    public double nomOutReverse;
    @Override
    public ErrorCode configNominalOutputReverse(double value) {
        nomOutReverse = value;
        return ErrorCode.OK;
    }

    public double peakOutForward;
    @Override
    public ErrorCode configPeakOutputForward(double value) {
        peakOutForward = value;
        return ErrorCode.OK;
    }

    public double peakOutReverse;
    @Override
    public ErrorCode configPeakOutputReverse(double value) {
        peakOutReverse = value;
        return ErrorCode.OK;
    }

    public int getTimeout() {
        return timeout;
    }

    public int position;
    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int pos) {
        position = pos;
    }

    public boolean phase;
    @Override
    public void setPhase(boolean phase) {
        this.phase = phase;
    }

    @Override
    public boolean getReverseLimit() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getForwardLimit() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setBrake(boolean brake) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getVelocity() {
        // TODO Auto-generated method stub
        return 0;
    }

	@Override
	public void setClosedLoopRampRate(double time) {
		// TODO Auto-generated method stub
		
	}
}
