package org.redalert1741.powerup;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;

import org.redalert1741.robotbase.config.Config;
import org.redalert1741.robotbase.config.Configurable;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;
import org.redalert1741.robotbase.wrapper.DoubleSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.SolenoidWrapper;
import org.redalert1741.robotbase.wrapper.TalonSrxWrapper;

public class Manipulation implements Loggable, Configurable {
    private DoubleSolenoidWrapper tilt;
    private TalonSrxWrapper firstDown;
    private TalonSrxWrapper firstUp;
    private TalonSrxWrapper top;
    private SolenoidWrapper brake;
    private double forwardSpeed;
    private double reverseSpeed;
    
    /**
     * Constructor for manipulation subsystem.
     * @param lift lift motor
     * @param tilt manipulation tilt
     * @param brake manipulation brake
     * @see Spark
     * @see DoubleSolenoid
     * @see Solenoid
     */
    public Manipulation(TalonSrxWrapper firstDown, TalonSrxWrapper firstUp,
            TalonSrxWrapper top,
            DoubleSolenoidWrapper tilt, SolenoidWrapper brake) {
        this.firstUp = firstUp;
        this.firstDown = firstDown;
        this.top = top;
        this.tilt = tilt;
        this.brake = brake;

        forwardSpeed = 1;
        reverseSpeed = -1;
        firstDown.configNominalOutputForward(0);
        firstDown.configNominalOutputReverse(0);
        firstDown.configPeakOutputForward(forwardSpeed);
        firstDown.configPeakOutputReverse(reverseSpeed);
        firstDown.setP(2);
        firstDown.setI(0);
        firstDown.setD(0);
        firstDown.setPhase(true);
    }
    
    public void tiltIn() {
        tilt.set(Value.kReverse);
    }
    
    public void tiltOut() {
        tilt.set(Value.kForward);
    }

    public void setSecondStage(double input) {
        top.set(ControlMode.PercentOutput, input);
    }

    public void setFirstStage(double input) {
        if(input > 0) {
            firstUp.set(ControlMode.PercentOutput, 0.5*input);
            firstDown.set(ControlMode.PercentOutput, 0);
        } else {
            firstUp.set(ControlMode.PercentOutput, 0);
            firstDown.set(ControlMode.PercentOutput, input);
        }
    }

    public void setFirstStagePosition(int pos) {
        if(Math.abs(pos-firstDown.getPosition()) > 50) {
            setFirstStage(-0.001*(pos-firstDown.getPosition()));
        } else {
            setFirstStage(0);
        }
    }
    
    public void enableBrake() {
        brake.set(true);
    }
    
    public void disableBrake() {
        brake.set(false);
    }

    public void resetPosition() {
        firstDown.setPosition(0);
    }
    
    @Override
    public void setupLogging(DataLogger logger) {
        brake.setupLogging(logger);
        tilt.setupLogging(logger);
        firstDown.setupLogging(logger);
        firstUp.setupLogging(logger);
        top.setupLogging(logger);
    }

    @Override
    public void log(DataLogger logger) {
        brake.log(logger);
        tilt.log(logger);
        firstDown.log(logger);
        firstUp.log(logger);
        top.log(logger);
    }

    @Override
    public void reloadConfig(Config config) {
        firstDown.reloadConfig(config);
        firstUp.reloadConfig(config);
        top.reloadConfig(config);
    }
}
