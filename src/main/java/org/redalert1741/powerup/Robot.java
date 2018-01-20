package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.IterativeRobot;

import java.util.logging.Logger;

public class Robot extends IterativeRobot
{
    // allocates logger for this class
    private static final Logger logger = Logger.getLogger(Robot.class.getName());

    @Override
    public void robotInit() {
      logger.info("Robot startup started");

      logger.info("Robot startup complete");
    }

    @Override
    public void autonomousInit() {
      logger.info("Autonomous init started");

      logger.info("Autonomous init complete");
    }

    @Override
    public void autonomousPeriodic() {
      // TODO: Add code to be called during the autonomous period
    }

    @Override
    public void teleopPeriodic() {
      // TODO: Add code to be called during the teleop period
    }

    @Override
    public void testPeriodic() {
      // TODO: Add code to be called in test mode
    }
}
