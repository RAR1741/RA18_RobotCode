package org.redalert1741.powerup;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.redalert1741.robotbase.config.Config;
import org.redalert1741.robotbase.config.Configurable;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;

import org.redalert1741.robotbase.wrapper.SolenoidWrapper;
import org.redalert1741.robotbase.wrapper.TalonSrxWrapper;

public class TankDrive implements Loggable, Configurable {
    private TalonSrxWrapper left1;
    private TalonSrxWrapper left2;
    private TalonSrxWrapper right1;
    private TalonSrxWrapper right2;

    private SolenoidWrapper shifter;

    private double maxrpm;
    private boolean speedMode;

    /**
     * Initializes the drivetrain.
     * @param l1 CAN ID of a left TalonSRX
     * @param l2 CAN ID of a left TalonSRX
     * @param r1 CAN ID of a right TalonSRX
     * @param r2 CAN ID of a right TalonSRX
     * @param s1 PCM ID of shifter
     * @see TalonSRX
     * @see Solenoid
     */
    public TankDrive(TalonSrxWrapper l1, TalonSrxWrapper l2,
            TalonSrxWrapper r1, TalonSrxWrapper r2, SolenoidWrapper s1) {
        left1 = l1;
        left2 = l2;
        left1.setStatusFrameRate(20, 5);
        left2.setStatusFrameRate(20, 5);

        right1 = r1;
        right2 = r2;
        right1.setStatusFrameRate(20, 5);
        right2.setStatusFrameRate(20, 5);


        left1.setInverted(true);
        left2.setInverted(true);

        left2.follow(left1);
        right2.follow(right1);
        
        shifter = s1;
        
        left1.setPhase(true);
        right1.setPhase(true);
    }

    /**
     * Drives the tank drive with right and left side values.
     * @param left Power for left side
     * @param right Power for right side
     */
    private void driveMotors(double left, double right) {
        left1.set(ControlMode.PercentOutput, left);
        right1.set(ControlMode.PercentOutput, right);
    }

    /**
     * Drive the tank drive in arcade style.
     * @param xdrive drive steering
     * @param ydrive drive power
     */
    public void arcadeDrive(double xdrive, double ydrive) {
        driveMotors(ydrive+xdrive, ydrive-xdrive);
    }

    public void driveMotorsSpeed(double left, double right) {
        left1.set(ControlMode.Velocity, left*maxrpm);
        right1.set(ControlMode.Velocity, right*maxrpm);
    }
    
    public void driveMotorsPercentV(double left, double right){
        left1.set(ControlMode.PercentOutput, left);
        right1.set(ControlMode.PercentOutput, right);
        System.out.println("Left" + left1.getVelocity());
        System.out.println("Right" + right1.getVelocity());
    }
    
    public void driveTeleopSpeed(double xdrive, double ydrive){
      left1.set(ControlMode.Velocity, (ydrive+xdrive)*maxrpm);        
      right1.set(ControlMode.Velocity, (ydrive-xdrive)*maxrpm);
    }

    /**
     * Shift to driving.
     */
    public void enableDriving() {
        shifter.set(false);
    }

    /**
     * Shift to climbing.
     */
    public void enableClimbing() {
        shifter.set(true);
    }

    public void setP(double pval) {
        left1.setP(pval);
        left2.setP(pval);
        right1.setP(pval);
        right2.setP(pval);
    }

    public void setI(double ival) {
        left1.setI(ival);
        left2.setI(ival);
        right1.setI(ival);
        right2.setI(ival);
    }

    public void setD(double dval) {
        left1.setD(dval);
        left2.setD(dval);
        right1.setD(dval);
        right2.setD(dval);
    }

    public void setPID(double pval, double ival, double dval) {
        setP(pval);
        setI(ival);
        setD(dval);
    }
    
    /**
     * Sets the Closed Loop Ramp rate on the drive {@link TalonSrxWrapper talons}.
     * @param time how much time it takes to go from neutral to full speed
     */
    public void setRampRate(double time) {
        left1.setClosedLoopRampRate(time);
        right1.setClosedLoopRampRate(time);
        left2.setClosedLoopRampRate(time);
        right2.setClosedLoopRampRate(time);
    }

    /**
     * Sets the brakes on the drive {@link TalonSrxWrapper talons} to be in
     * brake (true) or coast (false).
     * @param brake whether brakes should be enabled
     */
    public void setBrakes(boolean brake) {
        left1.setBrake(brake);
        left2.setBrake(brake);
        right1.setBrake(brake);
        right2.setBrake(brake);
    }

    @Override
    public void setupLogging(DataLogger logger) {
    	left1.setupLogging(logger);
    	left2.setupLogging(logger);
    	right1.setupLogging(logger);
    	right2.setupLogging(logger);
    	shifter.setupLogging(logger);
    }

    @Override
    public void log(DataLogger logger) {
    	left1.log(logger);
    	left2.log(logger);
    	right1.log(logger);
    	right2.log(logger);
    	shifter.log(logger);
    }

    @Override
    public void reloadConfig(Config config) {
        left1.reloadConfig(config);
        left2.reloadConfig(config);
        right1.reloadConfig(config);
        right2.reloadConfig(config);
        setP(config.getSetting("drive_p", 1.0));
        setI(config.getSetting("drive_i", 0.0));
        setD(config.getSetting("drive_d", 0.0));
        maxrpm = config.getSetting("drive_maxrpm", 1300.0);
        speedMode = config.getSetting("speedmode", true);
    }
}
