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
	public void autonomousInit() {
		// see http://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/826278-2018-game-data-details
		// panels are marked relative to your alliance as they face the field
		// our Switch is first, then the Scale, then the opponent Switch
		// each element is either L (left) or R (right)

//		char[] panelAssignments = DriverStation.getInstance().getGameSpecificMessage().toCharArray();
//		if (panelAssignments[0] == 'L') {
//			// our panel is on the left!
//		} else if (panelAssignments[0] == 'R') {
//			// our panel is on the right!
//		} else {
//			// uh oh!
//		}
	}

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
