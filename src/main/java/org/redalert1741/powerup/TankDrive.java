package org.redalert1741.powerup;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class TankDrive {
    TalonSRX left1;
    TalonSRX left2;
    TalonSRX right1;
    TalonSRX right2;

    DoubleSolenoid shifter;

    /**
     * Initializes the drivetrain.
     * @param l1 CAN ID of a left TalonSRX
     * @param l2 CAN ID of a left TalonSRX
     * @param r1 CAN ID of a right TalonSRX
     * @param r2 CAN ID of a right TalonSRX
     * @param s1 PCM ID of piston open
     * @param s2 PCM ID of piston close
     * @see TalonSRX
     * @see DoubleSolenoid
     */
    public TankDrive(int l1, int l2, int r1, int r2, int s1, int s2) {
        left1 = new TalonSRX(l1);
        left2 = new TalonSRX(l2);

        right1 = new TalonSRX(r1);
        right2 = new TalonSRX(r2);

        left1.setInverted(true);
        left2.setInverted(true);

        shifter = new DoubleSolenoid(s1, s2);
    }

    /**
     * Drives the tank drive with right and left side values.
     * @param left Power for left side
     * @param right Power for right side
     */
    public void tankDrive(double left, double right) {
        left1.set(ControlMode.PercentOutput, left);
        left2.set(ControlMode.PercentOutput, left);

        right1.set(ControlMode.PercentOutput, right);
        right2.set(ControlMode.PercentOutput, right);
    }

    /**
     * Drive the tank drive in arcade style.
     * @param xDrive drive steering
     * @param yDrive drive power
     */
    public void arcadeDrive(double xDrive, double yDrive) {
        tankDrive(yDrive+xDrive, yDrive-xDrive);
    }

    /**
     * Shift to driving
     */
    public void enableDriving() {
        shifter.set(Value.kForward);
    }

    /**
     * Shift to climbing
     */
    public void enableClimbing() {
        shifter.set(Value.kReverse);
    }
}
