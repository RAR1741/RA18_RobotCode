package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot
{
    @Override
    public void robotInit() {}

    @Override
    public void autonomousInit() {
        // see http://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/826278-2018-game-data-details
        // panels are marked relative to your alliance as they face the field
        // our Switch is first, then the Scale, then the opponent Switch
        // each element is either L (left) or R (right)

//        char[] panelAssignments = DriverStation.getInstance().getGameSpecificMessage().toCharArray();
//        if (panelAssignments[0] == 'L') {
//            // our panel is on the left!
//        } else if (panelAssignments[0] == 'R') {
//            // our panel is on the right!
//        } else {
//            // uh oh!
//        }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopPeriodic() {}

    @Override
    public void testPeriodic() {}
}
