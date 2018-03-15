package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.redalert1741.powerup.auto.end.TalonDistanceEnd;
import org.redalert1741.powerup.auto.move.ScoringGrabberMove;
import org.redalert1741.powerup.auto.move.ScoringKickerMove;
import org.redalert1741.powerup.auto.move.TankDriveArcadeMove;
import org.redalert1741.powerup.auto.move.TankDriveBrakeMove;
import org.redalert1741.powerup.auto.move.TankDriveRampRateMove;
import org.redalert1741.powerup.auto.move.TankDriveTankMove;
import org.redalert1741.robotbase.auto.core.AutoFactory;
import org.redalert1741.robotbase.auto.core.Autonomous;
import org.redalert1741.robotbase.auto.core.JsonAutoFactory;
import org.redalert1741.robotbase.auto.end.EmptyEnd;
import org.redalert1741.robotbase.auto.end.TimedEnd;
import org.redalert1741.robotbase.config.Config;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.LoggablePdp;
import org.redalert1741.robotbase.wrapper.RealDoubleSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.RealSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.RealTalonSrxWrapper;
import org.redalert1741.robotbase.wrapper.TalonSrxWrapper;

public class Robot extends IterativeRobot {
    private DataLogger data;
    private Config config;
    private Autonomous auto;

    private RealDoubleSolenoidWrapper tilt;
    private RealDoubleSolenoidWrapper grab;
    private RealDoubleSolenoidWrapper kick;
    private RealSolenoidWrapper driveBrake;
    private RealSolenoidWrapper manipBrake;

    private TankDrive drive;
    private Manipulation manip;
    private Scoring score;
    private Climber climb;

    private LoggablePdp pdp;

    private XboxController driver;
    private XboxController operator;

    private long enableStart;
    private boolean climbing;

    @Override
    public void robotInit() {
        driver = new XboxController(0);
        operator = new XboxController(1);

        pdp = new LoggablePdp();

        TalonSrxWrapper rightDrive = new RealTalonSrxWrapper(2);
        TalonSrxWrapper leftDrive = new RealTalonSrxWrapper(4);

        setupSolenoids();

        drive = new TankDrive(leftDrive, new RealTalonSrxWrapper(5),
                rightDrive, new RealTalonSrxWrapper(3),
                driveBrake);
        manip = new Manipulation(new RealTalonSrxWrapper(1), new RealTalonSrxWrapper(7),
                new RealTalonSrxWrapper(6),
                tilt, manipBrake);
        score = new Scoring(kick, grab);
        climb = new Climber(manip, drive);

        //logging

        data = new DataLogger();

        data.addAttribute("time");

        data.addLoggable(pdp);
        data.addLoggable(drive);
        data.addLoggable(manip);
        data.addLoggable(score);

        data.setupLoggables();

        //config

        config = new Config();

        config.addConfigurable(drive);
        config.addConfigurable(manip);

        reloadConfig();

        //auto moves

        AutoFactory.addMoveMove("driveArcade", () -> new TankDriveArcadeMove(drive));
        AutoFactory.addMoveMove("driveTank", () -> new TankDriveTankMove(drive));
        AutoFactory.addMoveMove("driveBrake", () -> new TankDriveBrakeMove(drive));
        AutoFactory.addMoveMove("driveRampRate", () -> new TankDriveRampRateMove(drive));
        AutoFactory.addMoveMove("grab", () -> new ScoringGrabberMove(score));
        AutoFactory.addMoveMove("kick", () -> new ScoringKickerMove(score));
        AutoFactory.addMoveEnd("driveDistRight", () -> new TalonDistanceEnd(rightDrive));
        AutoFactory.addMoveEnd("driveDistLeft", () -> new TalonDistanceEnd(leftDrive));
        AutoFactory.addMoveEnd("time", () -> new TimedEnd());
        AutoFactory.addMoveEnd("empty", () -> new EmptyEnd());
    }

    @Override
    public void autonomousInit() {
        startLogging(data, "auto");
        reloadConfig();

        score.close();
        score.retract();
        drive.setBrakes(false);

        auto = new JsonAutoFactory().makeAuto(config.getSetting("auto", "/home/lvuser/auto/min-auto.json"));
        auto.start();

        climbing = false;
        enableStart = System.currentTimeMillis();
    }

    @Override
    public void autonomousPeriodic() {
        auto.run();
        data.log("time", System.currentTimeMillis()-enableStart);
        data.logAll();
    }

    @Override
    public void teleopInit() {
        startLogging(data, "teleop");
        reloadConfig();

        score.grabOff();
        drive.setBrakes(false);

        climbing = false;
    }

    @Override
    public void teleopPeriodic() {
        //climbing enable
        if(driver.getPOV() == 90) {
            climbing = true;
        } else if(driver.getPOV() == 270) {
            climbing = false;
        }

        //driving
//        if(!climbing) {
            drive.enableDriving();
            double speedmultiplier = (0.4*driver.getTriggerAxis(Hand.kLeft)+0.6);
            drive.arcadeDrive(driver.getX(Hand.kRight)*speedmultiplier,
                    -speedmultiplier*driver.getY(Hand.kLeft));
//        } else {
//            //climb.climb();
//        }

        //manual manipulation controls
        
        
        if(!operator.getAButton()) {
            manip.enableBrake();
            manip.setFirstStage(0);
            manip.setSecondStage(0);
        } else {
            manip.disableBrake();
            manip.setFirstStage(operator.getY(Hand.kLeft));
            manip.setSecondStage(-operator.getY(Hand.kRight));
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
        if(driver.getXButton()) {
            score.grabOff();
        }

        //reset manipulation
        if(operator.getBackButton()) {
            manip.resetPosition();
        }

        data.log("time", System.currentTimeMillis()-enableStart);
        data.logAll();
    }

    @Override
    public void testPeriodic() {
        // TODO: Add code to be called in test mode
    }

    private void startLogging(DataLogger data, String type) {
        data.open("/home/lvuser/logs/log"
                +new SimpleDateFormat("-yyyy-MM-dd_HH-mm-ss-").format(new Date())
                +type+".csv");
        data.writeAttributes();

        enableStart = System.currentTimeMillis();
    }

    private void reloadConfig() {
        config.loadFromFile("/home/lvuser/config.txt");
        config.reloadConfig();
    }

    private void setupSolenoids() {
        Config solenoidconfig = new Config();
        solenoidconfig.loadFromFile("/home/lvuser/solenoids.txt");
        String solenoids = solenoidconfig.getSetting("solenoids", "62743051");
        driveBrake = new RealSolenoidWrapper(solenoids.charAt(0)-'0');
        tilt = new RealDoubleSolenoidWrapper(solenoids.charAt(1)-'0', solenoids.charAt(2)-'0');
        manipBrake = new RealSolenoidWrapper(solenoids.charAt(3)-'0');
        kick = new RealDoubleSolenoidWrapper(solenoids.charAt(4)-'0', solenoids.charAt(5)-'0');
        grab = new RealDoubleSolenoidWrapper(solenoids.charAt(6)-'0', solenoids.charAt(7)-'0');
    }
}
