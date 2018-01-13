package org.redalert1741.powerup;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TankDrive {
    TalonSRX left1, left2, left3, right1, right2, right3;

    public TankDrive(int l1, int l2, int l3, int r1, int r2, int r3) {
        left1 = new TalonSRX(l1);
        left2 = new TalonSRX(l2);
        left3 = new TalonSRX(l3);

        right1 = new TalonSRX(r1);
        right2 = new TalonSRX(r2);
        right3 = new TalonSRX(r3);

        left1.setInverted(true);
        left2.setInverted(true);
        left3.setInverted(true);
    }

    public void tankDrive(double left, double right) {
        left1.set(ControlMode.PercentOutput, left);
        left2.set(ControlMode.PercentOutput, left);
        left3.set(ControlMode.PercentOutput, left);

        right1.set(ControlMode.PercentOutput, right);
        right2.set(ControlMode.PercentOutput, right);
        right3.set(ControlMode.PercentOutput, right);
    }

    public void arcadeDrive(double x, double y) {
        tankDrive(y+x, y-x);
    }
}
