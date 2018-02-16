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
    private TalonSrxWrapper lift;
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
    public Manipulation(TalonSrxWrapper lift, DoubleSolenoidWrapper tilt,
            SolenoidWrapper brake) {
        this.lift = lift;
        this.tilt = tilt;
        this.brake = brake;

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
        lift.set(ControlMode.PercentOutput, input);
    }

    public void setLiftPosition(int pos) {
        lift.set(ControlMode.Position, pos);
    }
    
    public void enableBrake() {
        brake.set(true);
    }
    
    public void disableBrake() {
        brake.set(false);
    }

    public void resetPosition() {
        lift.setPosition(0);
    }
    
    @Override
    public void setupLogging(DataLogger logger) {
        brake.setupLogging(logger);
        tilt.setupLogging(logger);
        lift.setupLogging(logger);
    }

    @Override
    public void log(DataLogger logger) {
        brake.log(logger);
        tilt.log(logger);
        lift.log(logger);
    }

    @Override
    public void reloadConfig(Config config) {
        lift.reloadConfig(config);
    }
}
