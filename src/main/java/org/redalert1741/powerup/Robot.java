package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.redalert1741.robotbase.wrapper.RealTalonSrxWrapper;

public class Robot extends IterativeRobot
{
    // allocates logger for this class
    private static final Logger logger = Logger.getLogger(Robot.class.getName());

    private TankDrive drive;
    private Manipulation manip;

    private XboxController driver;

    @Override
    public void robotInit() {
        // Set up global logger
        setupLogging();

        logger.info("Robot startup started");

        driver = new XboxController(0);

        drive = new TankDrive(new RealTalonSrxWrapper(4), new RealTalonSrxWrapper(5),
                new RealTalonSrxWrapper(2), new RealTalonSrxWrapper(3),
                null);
        manip = new Manipulation(new RealTalonSrxWrapper(1), null,
                null);

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
        drive.arcadeDrive(driver.getX(Hand.kRight), -driver.getY(Hand.kLeft));
        manip.setLift(driver.getTriggerAxis(Hand.kRight)-driver.getTriggerAxis(Hand.kLeft));
    }

    @Override
    public void testPeriodic() {
        // TODO: Add code to be called in test mode
    }

    // Set logging up for the rest of the application
    private void setupLogging() {
        try {
            FileInputStream fis = new FileInputStream("/home/lvuser/logging.properties");
            LogManager.getLogManager().readConfiguration(fis);
            fis.close();
        } catch (FileNotFoundException ex) {
            // Ignore exceptions
            ex.printStackTrace();
        } catch (IOException ex) {
            // Ignore exceptions
            ex.printStackTrace();
        }
    }
}
