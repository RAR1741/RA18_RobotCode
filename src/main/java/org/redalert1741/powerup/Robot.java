package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.redalert1741.robotbase.config.Config;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.wrapper.RealDoubleSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.RealSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.RealTalonSrxWrapper;

public class Robot extends IterativeRobot {
    private DataLogger data;
    private Config config;

    private TankDrive drive;
    private Manipulation manip;
    private Scoring score;

    private XboxController driver;

    private long enableStart;

    @Override
    public void robotInit() {
        driver = new XboxController(0);

        drive = new TankDrive(new RealTalonSrxWrapper(4), new RealTalonSrxWrapper(5),
                new RealTalonSrxWrapper(2), new RealTalonSrxWrapper(3),
                new RealSolenoidWrapper(6));
        manip = new Manipulation(new RealTalonSrxWrapper(1), new RealTalonSrxWrapper(6),
                new RealDoubleSolenoidWrapper(2, 7), new RealSolenoidWrapper(4));
        score = new Scoring(new RealDoubleSolenoidWrapper(3, 0),
                new RealDoubleSolenoidWrapper(5, 1));

        //logging

        data = new DataLogger();

        data.addAttribute("time");

        data.addLoggable(drive);
        data.addLoggable(manip);
        data.addLoggable(score);

        data.setupLoggables();

        //config

        config = new Config();

        config.addConfigurable(drive);
        config.addConfigurable(manip);

        reloadConfig();
    }

    @Override
    public void autonomousInit() {
        startLogging(data, "auto");
        reloadConfig();

        score.close();
        score.retract();
    }

    @Override
    public void autonomousPeriodic() {
        // TODO: Add code to be called during the autonomous period
        data.logAll();
    }

    @Override
    public void teleopInit() {
        startLogging(data, "teleop");
        reloadConfig();
    }

    @Override
    public void teleopPeriodic() {
        //driving
        drive.arcadeDrive(driver.getX(Hand.kRight)*0.5, -0.5*driver.getY(Hand.kLeft));

        //manual manipulation controls
        if(driver.getStartButton()) {
            manip.setLift(driver.getTriggerAxis(Hand.kRight));
            manip.setSecond(driver.getTriggerAxis(Hand.kLeft));
        }

        //tilt manipulation
        if(driver.getAButton()) {
            manip.tiltOut();
        }
        if(driver.getBButton()) {
            manip.tiltIn();
        }

        //scoring controls
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

        //reset manipulation
        if(driver.getBackButton()) {
            manip.resetPosition();
        }

        //climbing controls (just drive backwards to climb)
        if(driver.getPOV() == 90) {
            drive.enableClimbing();
        } else if(driver.getPOV() == 270) {
            drive.enableDriving();
        }

        data.log("time", System.currentTimeMillis()-enableStart);
        data.logAll();
    }

    @Override
    public void testPeriodic() {
        // TODO: Add code to be called in test mode
    }

    private void startLogging(DataLogger data, String type) {
        data.open("/home/lvuser/logs/log-"
                +new SimpleDateFormat("-yyyy-MM-dd_HH-mm-ss").format(new Date())
                +type+".csv");
        data.writeAttributes();

        enableStart = System.currentTimeMillis();
    }

    private void reloadConfig() {
        config.loadFromFile("/home/lvuser/config.txt");
        config.reloadConfig();
    }
}
