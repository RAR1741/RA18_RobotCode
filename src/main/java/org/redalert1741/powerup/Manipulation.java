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
    private TalonSrxWrapper down;
    private TalonSrxWrapper up;
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
    public Manipulation(TalonSrxWrapper lift, TalonSrxWrapper up, 
            DoubleSolenoidWrapper tilt, SolenoidWrapper brake) {
        this.down = lift;
        this.tilt = tilt;
        this.brake = brake;
        this.up = up;

        forwardSpeed = 0.5;
        reverseSpeed = -0.4;
        lift.configPeakOutputForward(forwardSpeed);
        lift.configPeakOutputReverse(reverseSpeed);
        lift.configNominalOutputForward(0);
        lift.configNominalOutputReverse(0);
        lift.setP(2);
        lift.setI(0);
        lift.setD(0);
        lift.setPhase(true);
    }
    
    public void tiltIn() {
        tilt.set(Value.kReverse);
    }
    
    public void tiltOut() {
        tilt.set(Value.kForward);
    }

    public void setLift(double input) {
        down.set(ControlMode.PercentOutput, input);
    }

    /**
     * Runs the second (up) motor if it hasn't hit the limit
     * @param input speed to set {@link ControlMode#PercentOutput}
     */
    public void setSecond(double input) {
        if(!down.getReverseLimit()) {
            up.set(ControlMode.PercentOutput, input);
        } else {
            up.set(ControlMode.PercentOutput, 0);
        }
    }

    /**
     * Experimental unused position control
     * @param pos position to go to (-7000-0)
     */
    public void setLiftPosition(int pos) {
        if(down.getPosition() < pos) {
            down.set(ControlMode.Position, pos);
            up.set(ControlMode.PercentOutput, 0);
        } else if (!down.getReverseLimit()) {
            down.set(ControlMode.PercentOutput, 0);
        } else {
            down.set(ControlMode.PercentOutput, 0);
            up.set(ControlMode.PercentOutput, 0);
        }
    }
    
    public void enableBrake() {
        brake.set(true);
    }
    
    public void disableBrake() {
        brake.set(false);
    }

    public void resetPosition() {
        down.setPosition(0);
    }
    
    @Override
    public void setupLogging(DataLogger logger) {
        brake.setupLogging(logger);
        tilt.setupLogging(logger);
        down.setupLogging(logger);
        up.setupLogging(logger);
    }

    @Override
    public void log(DataLogger logger) {
        brake.log(logger);
        tilt.log(logger);
        down.log(logger);
        up.log(logger);
    }

    @Override
    public void reloadConfig(Config config) {
        down.reloadConfig(config);
        up.reloadConfig(config);
    }
}
