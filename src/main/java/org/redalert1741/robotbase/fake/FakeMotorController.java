package org.redalert1741.robotbase.fake;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlFrame;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.StickyFaults;

public class FakeMotorController implements IMotorController {
    public IMotorController masterToFollow;
    @Override
    public void follow(IMotorController masterToFollow) {
        this.masterToFollow = masterToFollow;
    }

    @Override
    public void valueUpdated() { /* i don't know what this function does */ }
    
    @Override
    public void set(ControlMode mode, double demand) {
        set(mode, demand, 0);
    }

    public ControlMode mode;
    public double value;
    @Override
    public void set(ControlMode mode, double demand0, double demand1) {
        this.mode = mode;
        this.value = demand0;
    }

    @Override
    public void neutralOutput() {
        set(ControlMode.Disabled, 0);
    }

    public NeutralMode neutral;
    @Override
    public void setNeutralMode(NeutralMode neutralMode) {
        neutral = neutralMode;
    }

    public boolean sensorPhase;
    @Override
    public void setSensorPhase(boolean sensorPhase) {
        this.sensorPhase = sensorPhase;
    }

    public boolean inverted;
    @Override
    public void setInverted(boolean invert) {
        inverted = invert;
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

    public double openLoopRamp;
    @Override
    public ErrorCode configOpenloopRamp(double secondsFromNeutralToFull, int timeoutMs) {
        openLoopRamp = secondsFromNeutralToFull;
        return ErrorCode.OK;
    }

    public double closedLoopRamp;
    @Override
    public ErrorCode configClosedloopRamp(double secondsFromNeutralToFull, int timeoutMs) {
        closedLoopRamp = secondsFromNeutralToFull;
        return ErrorCode.OK;
    }

    public double peakOutputForward;
    @Override
    public ErrorCode configPeakOutputForward(double percentOut, int timeoutMs) {
        peakOutputForward = percentOut;
        return ErrorCode.OK;
    }

    public double peakOutputReverse;
    @Override
    public ErrorCode configPeakOutputReverse(double percentOut, int timeoutMs) {
        peakOutputReverse = percentOut;
        return ErrorCode.OK;
    }

    public double nominalOutputForward;
    @Override
    public ErrorCode configNominalOutputForward(double percentOut, int timeoutMs) {
        nominalOutputForward = percentOut;
        return ErrorCode.OK;
    }

    public double nominalOutputReverse;
    @Override
    public ErrorCode configNominalOutputReverse(double percentOut, int timeoutMs) {
        nominalOutputReverse = percentOut;
        return ErrorCode.OK;
    }

    public double neutralDeadband;
    @Override
    public ErrorCode configNeutralDeadband(double percentDeadband, int timeoutMs) {
        neutralDeadband = percentDeadband;
        return ErrorCode.OK;
    }

    public double voltageCompSaturation;
    @Override
    public ErrorCode configVoltageCompSaturation(double voltage, int timeoutMs) {
        voltageCompSaturation = voltage;
        return ErrorCode.OK;
    }

    public double voltageMeasurementFilter;
    @Override
    public ErrorCode configVoltageMeasurementFilter(int filterWindowSamples, int timeoutMs) {
        voltageMeasurementFilter = filterWindowSamples;
        return ErrorCode.OK;
    }

    public boolean voltageCompensation;
    @Override
    public void enableVoltageCompensation(boolean enable) {
        voltageCompensation = enable;
    }

    @Override
    public double getBusVoltage() {
        return 0;
    }

    @Override
    public double getMotorOutputPercent() {
        return 0;
    }

    @Override
    public double getMotorOutputVoltage() {
        return 0;
    }

    @Override
    public double getOutputCurrent() {
        return 0;
    }

    @Override
    public double getTemperature() {
        return 0;
    }

    RemoteFeedbackDevice feedbackDevice;
    @Override
    public ErrorCode configSelectedFeedbackSensor(RemoteFeedbackDevice feedbackDevice, int pidIdx, int timeoutMs) {
        this.feedbackDevice = feedbackDevice;
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode configRemoteFeedbackFilter(int deviceID, RemoteSensorSource remoteSensorSource, int remoteOrdinal,
            int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode configSensorTerm(SensorTerm sensorTerm, FeedbackDevice feedbackDevice, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getSelectedSensorPosition(int pidIdx) {
     // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getSelectedSensorVelocity(int pidIdx) {
        return 0;
    }

    @Override
    public ErrorCode setSelectedSensorPosition(int sensorPos, int pidIdx, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode setControlFramePeriod(ControlFrame frame, int periodMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode setStatusFramePeriod(StatusFrame frame, int periodMs, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getStatusFramePeriod(StatusFrame frame, int timeoutMs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ErrorCode configForwardLimitSwitchSource(RemoteLimitSwitchSource type, LimitSwitchNormal normalOpenOrClose,
            int deviceID, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode configReverseLimitSwitchSource(RemoteLimitSwitchSource type, LimitSwitchNormal normalOpenOrClose,
            int deviceID, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void overrideLimitSwitchesEnable(boolean enable) {
        // TODO Auto-generated method stub

    }

    @Override
    public ErrorCode configForwardSoftLimitThreshold(int forwardSensorLimit, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode configReverseSoftLimitThreshold(int reverseSensorLimit, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode configForwardSoftLimitEnable(boolean enable, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode configReverseSoftLimitEnable(boolean enable, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void overrideSoftLimitsEnable(boolean enable) {
        // TODO Auto-generated method stub

    }

    @Override
    public ErrorCode config_kP(int slotIdx, double value, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode config_kI(int slotIdx, double value, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode config_kD(int slotIdx, double value, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode config_kF(int slotIdx, double value, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode config_IntegralZone(int slotIdx, int izone, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode configAllowableClosedloopError(int slotIdx, int allowableCloseLoopError, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode configMaxIntegralAccumulator(int slotIdx, double iaccum, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode setIntegralAccumulator(double iaccum, int pidIdx, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getClosedLoopError(int pidIdx) {
        return 0;
    }

    @Override
    public double getIntegralAccumulator(int pidIdx) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getErrorDerivative(int pidIdx) {
        return 0;
    }

    @Override
    public void selectProfileSlot(int slotIdx, int pidIdx) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getActiveTrajectoryPosition() {
        return 0;
    }

    @Override
    public int getActiveTrajectoryVelocity() {
        return 0;
    }

    @Override
    public double getActiveTrajectoryHeading() {
        return 0;
    }

    @Override
    public ErrorCode configMotionCruiseVelocity(int sensorUnitsPer100ms, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode configMotionAcceleration(int sensorUnitsPer100msPerSec, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode clearMotionProfileTrajectories() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getMotionProfileTopLevelBufferCount() {
        return 0;
    }

    @Override
    public ErrorCode pushMotionProfileTrajectory(TrajectoryPoint trajPt) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isMotionProfileTopLevelBufferFull() {
        return false;
    }

    @Override
    public void processMotionProfileBuffer() { /* who knows */ }

    @Override
    public ErrorCode getMotionProfileStatus(MotionProfileStatus statusToFill) {
        return null;
    }

    @Override
    public ErrorCode clearMotionProfileHasUnderrun(int timeoutMs) {
        return null;
    }

    @Override
    public ErrorCode changeMotionControlFramePeriod(int periodMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode getLastError() {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode getFaults(Faults toFill) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode getStickyFaults(StickyFaults toFill) {
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode clearStickyFaults(int timeoutMs) {
        return ErrorCode.OK;
    }

    @Override
    public int getFirmwareVersion() {
        return 0;
    }

    @Override
    public boolean hasResetOccurred() {
        return false;
    }

    @Override
    public ErrorCode configSetCustomParam(int newValue, int paramIndex, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int configGetCustomParam(int paramIndex, int timoutMs) {
        //i don't even know
        return 0;
    }

    @Override
    public ErrorCode configSetParameter(ParamEnum param, double value, int subValue, int ordinal, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ErrorCode configSetParameter(int param, double value, int subValue, int ordinal, int timeoutMs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double configGetParameter(ParamEnum paramEnum, int ordinal, int timeoutMs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double configGetParameter(int paramEnum, int ordinal, int timeoutMs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getBaseID() {
        return 0;
    }

    @Override
    public int getDeviceID() {
        return 0;
    }

}
