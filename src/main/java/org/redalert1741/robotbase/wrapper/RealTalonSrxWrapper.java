package org.redalert1741.robotbase.wrapper;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.redalert1741.robotbase.config.Config;
import org.redalert1741.robotbase.logging.DataLogger;

public class RealTalonSrxWrapper extends TalonSrxWrapper {
    private TalonSRX talon;
    private double value;
    private String logname;
    private int timeout;

    /**
     * A real TalonSRX. Logging name is "talon_{id}"
     * @param id CAN ID of the Talon
     */
    public RealTalonSrxWrapper(int id) {
        talon = new TalonSRX(id);
        value = 0;
        logname = "talon_" + id;
    }

    /**
     * A real TalonSRX.
     * @param id CAN ID of the Talon
     * @param name Logging name
     */
    public RealTalonSrxWrapper(int id, String name) {
        talon = new TalonSRX(id);
        value = 0;
        logname = name;
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

    @Override
    public ErrorCode setP(double pvalue) {
        return talon.config_kP(0, pvalue, timeout);
    }

    @Override
    public ErrorCode setI(double ivalue) {
        return talon.config_kI(0, ivalue, timeout);
    }

    @Override
    public ErrorCode setD(double dvalue) {
        return talon.config_kD(0, dvalue, timeout);
    }

    @Override
    public ErrorCode setF(double kvalue) {
        return talon.config_kF(0, kvalue, timeout);
    }

    @Override
    public ErrorCode configNominalOutputForward(double value) {
        return talon.configNominalOutputForward(value, timeout);
    }

    @Override
    public ErrorCode configNominalOutputReverse(double value) {
        return talon.configNominalOutputReverse(value, timeout);
    }

    @Override
    public ErrorCode configPeakOutputForward(double value) {
        return talon.configPeakOutputForward(value, timeout);
    }

    @Override
    public ErrorCode configPeakOutputReverse(double value) {
        return talon.configPeakOutputReverse(value, timeout);
    }

    @Override
    public void setupLogging(DataLogger logger) {
        logger.addAttribute(logname+"_current");
        logger.addAttribute(logname+"_voltage");
        logger.addAttribute(logname+"_value");
    }

    @Override
    public void log(DataLogger logger) {
        logger.log(logname+"_current", getOutputCurrent());
        logger.log(logname+"_voltage", getBusVoltage());
        logger.log(logname+"_value", get());
    }

    @Override
    public void reloadConfig(Config config) {
        timeout = config.getSetting("can_timeout", 10.0).intValue();
    }
}
