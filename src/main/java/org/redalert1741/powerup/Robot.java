package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Robot extends IterativeRobot
{
    private static final Logger logger = LogManager.getLogger(); // allocates logger for this class
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

    }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void testPeriodic() {}
}
