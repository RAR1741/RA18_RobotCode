package org.redalert1741.powerup;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;

import org.redalert1741.robotbase.wrapper.SolenoidWrapper;
import org.redalert1741.robotbase.wrapper.TalonSrxWrapper;

public class TankDrive implements Loggable {
    private TalonSrxWrapper left1;
    private TalonSrxWrapper left2;
    private TalonSrxWrapper right1;
    private TalonSrxWrapper right2;

    private SolenoidWrapper shifter;

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

        right1 = r1;
        right2 = r2;

        left1.setInverted(true);
        left2.setInverted(true);

        left2.follow(left1);
        right2.follow(right1);

        shifter = s1;
    }

    /**
     * Drives the tank drive with right and left side values.
     * @param left Power for left side
     * @param right Power for right side
     */
    public void tankDrive(double left, double right) {
        enableDriving();
        left1.set(ControlMode.PercentOutput, left);
        right1.set(ControlMode.PercentOutput, right);
    }

    /**
     * Drive the tank drive in arcade style.
     * @param xdrive drive steering
     * @param ydrive drive power
     */
    public void arcadeDrive(double xdrive, double ydrive) {
        tankDrive(ydrive+xdrive, ydrive-xdrive);
    }

    /**
     * Climbs at a speed.
     * @param speed
     */
    public void climb(double speed) {
        enableClimbing();
        left1.set(ControlMode.PercentOutput, speed);
        right1.set(ControlMode.PercentOutput, speed);
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

	@Override
	public void setupLogging(DataLogger logger) {
		logger.addAttribute("leftSpeed");
		logger.addAttribute("rightSpeed");
		logger.addAttribute("left1current");
		logger.addAttribute("left2current");
		logger.addAttribute("right1current");
		logger.addAttribute("right2current");
		logger.addAttribute("right1voltage");
		logger.addAttribute("right2voltage");
		logger.addAttribute("left1voltage");
		logger.addAttribute("left2voltage");
		logger.addAttribute("shifterState");
	}

	@Override
	public void log(DataLogger logger) {
		logger.log("leftSpeed", left1.get());
		logger.log("rightSpeed", right1.get());
		logger.log("left1current", left1.getOutputCurrent());
		logger.log("left2current", left2.getOutputCurrent());
		logger.log("r1current", right1.getOutputCurrent());
		logger.log("r2current", right2.getOutputCurrent());
		logger.log("right1voltage", right1.getBusVoltage());
		logger.log("right2voltage", right2.getBusVoltage());
		logger.log("left1voltage", left1.getBusVoltage());
		logger.log("left2voltage", left2.getBusVoltage());
		logger.log("shifterState", shifter.get());
	}
}
