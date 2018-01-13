package org.redalert1741.powerup;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends IterativeRobot
{
	TalonSRX motor;
	XboxController driver;
	
	@Override
	public void robotInit() {
		driver = new XboxController(0);
		
		motor = new TalonSRX(3);
		//motor.setInverted(true);
		motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,10);
		motor.configNominalOutputForward(0, 10);
		motor.configNominalOutputReverse(0, 10);
		motor.configPeakOutputForward(1, 10);
		motor.configPeakOutputReverse(-1, 10);
		motor.configAllowableClosedloopError(0, 0, 10);
		motor.config_kF(0, 0, 10);
		motor.config_kP(0, 1, 10);
		motor.config_kI(0, 0, 10);
		motor.config_kD(0, 0, 10);
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
	public void teleopInit() {
		System.out.println(motor.getSensorCollection().setQuadraturePosition(0, 1000));
	}

	@Override
	public void teleopPeriodic() {
		System.out.println(motor.getSensorCollection().getQuadraturePosition());
		if(driver.getPOV()!=-1)
			motor.set(ControlMode.Position, (driver.getPOV()*2048)/360);
	}

	@Override
	public void testPeriodic() {}
}
