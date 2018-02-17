package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogManager;

import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.wrapper.RealDoubleSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.RealSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.RealTalonSrxWrapper;

public class Robot extends IterativeRobot
{
    // allocates logger for this class
    //private static final Logger logger = Logger.getLogger(Robot.class.getName());

    private DataLogger data;

    private TankDrive drive;
    private Manipulation manip;
    private Scoring score;

    private XboxController driver;

    @Override
    public void robotInit() {
        // Set up global logger
        setupLogging();

        //logger.info("Robot startup started");

        driver = new XboxController(0);

        drive = new TankDrive(new RealTalonSrxWrapper(4), new RealTalonSrxWrapper(5),
                new RealTalonSrxWrapper(2), new RealTalonSrxWrapper(3),
                new RealSolenoidWrapper(6));
        manip = new Manipulation(new RealTalonSrxWrapper(1), new RealTalonSrxWrapper(6),
                new RealDoubleSolenoidWrapper(2, 7), new RealSolenoidWrapper(4));
        score = new Scoring(new RealDoubleSolenoidWrapper(3, 0), new RealDoubleSolenoidWrapper(5, 1));

        //logger.info("Robot startup complete");
        //logger.info("Initialize DataLogger");

        data = new DataLogger();
        data.addLoggable(drive);
        data.addLoggable(manip);

        data.setupLoggables();

        //logger.info("DataLogger initialized");
    }

    @Override
    public void autonomousInit() {
        //logger.info("Autonomous init started");

        startLogging(data, "auto");

        //logger.info("Autonomous init complete");
    }

    @Override
    public void autonomousPeriodic() {
        // TODO: Add code to be called during the autonomous period
        data.logAll();
    }

    @Override
    public void teleopInit() {
        //logger.info("Teleop init started");

        startLogging(data, "teleop");

        //logger.info("Teleop init complete");
    }

    @Override
    public void teleopPeriodic() {
        drive.arcadeDrive(driver.getX(Hand.kRight)*0.5, -0.5*driver.getY(Hand.kLeft));
        if(driver.getStartButton()) {
            manip.setLift(driver.getTriggerAxis(Hand.kRight)-driver.getTriggerAxis(Hand.kLeft));
        }

        if(driver.getAButton()) {
            manip.tiltOut();
        }
        if(driver.getBButton()) {
            manip.tiltIn();
        }

        if(driver.getYButton()) {
            score.kick();
        } else {
            score.retract();
        }
        if(driver.getBumper(Hand.kLeft)) {
            score.close();
        }
        if(driver.getBumper(Hand.kRight)) {
            score.open();
        }

        if(driver.getBackButton()) {
            manip.resetPosition();
        }

        if(driver.getPOV() == 0) {
            manip.setLiftPosition(-7000);
        } else if(driver.getPOV() == 180) {
            manip.setLiftPosition(0);
        }
        if(driver.getPOV() == 90) {
            drive.enableClimbing();
        } else if(driver.getPOV() == 270) {
            drive.enableDriving();
        }

        data.logAll();
    }

    @Override
    public void testPeriodic() {
        // TODO: Add code to be called in test mode
    }

    private void startLogging(DataLogger data, String type) {
        data.open("/home/lvuser/logs/log-"+type+new SimpleDateFormat("-yyyy-MM-dd_HH-mm-ss").format(new Date())+".csv");
        data.writeAttributes();
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
