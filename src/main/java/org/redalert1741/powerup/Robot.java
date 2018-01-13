package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Robot extends IterativeRobot
{
    TankDrive tank;
    XboxController driver;
    DoubleSolenoid grabber;

    @Override
    public void robotInit() {
	//controllers
	driver = new XboxController(0);

	//drivetrain
	tank = new TankDrive(1,2,3,4,5,6);

	//grabber
	grabber = new DoubleSolenoid(0, 1);
    }

    @Override
    public void autonomousInit() {}

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopPeriodic() {
	tank.arcadeDrive(driver.getX(Hand.kRight) * -0.6, driver.getY(Hand.kLeft) * 0.6);

	if(driver.getAButton()) {
	    grabber.set(Value.kForward);
	} else if (driver.getBButton()) {
	    grabber.set(Value.kReverse);
	}
    }

    @Override
    public void testPeriodic() {}
}
