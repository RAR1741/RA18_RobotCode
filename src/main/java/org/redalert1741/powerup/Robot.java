package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.redalert1741.powerup.Manipulation.LiftPos;
import org.redalert1741.powerup.auto.end.TalonDistanceEnd;
import org.redalert1741.powerup.auto.move.ManipulationLiftMove;
import org.redalert1741.powerup.auto.move.ManipulationTiltMove;
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
import org.redalert1741.robotbase.input.EdgeDetect;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.LoggablePdp;
import org.redalert1741.robotbase.wrapper.RealDoubleSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.RealSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.RealTalonSrxWrapper;
import org.redalert1741.robotbase.wrapper.TalonSrxWrapper;

import openrio.powerup.MatchData;
import openrio.powerup.MatchData.GameFeature;

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
    
    int place;
    EdgeDetect up;
    EdgeDetect down;
    double fheight;
    double sheight;
    double manualSpeed;

    @Override
    public void robotInit() {
        up = new EdgeDetect();
        down = new EdgeDetect();
        
        driver = new XboxController(0);
        operator = new XboxController(1);

        pdp = new LoggablePdp();

        TalonSrxWrapper rightDrive = new RealTalonSrxWrapper(2);
        TalonSrxWrapper leftDrive = new RealTalonSrxWrapper(4);

        setupSolenoids();

        drive = new TankDrive(leftDrive, new RealTalonSrxWrapper(5),
                rightDrive, new RealTalonSrxWrapper(3),
                driveBrake);
        manip = new Manipulation(new RealTalonSrxWrapper(1),
                new RealTalonSrxWrapper(7),
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
        AutoFactory.addMoveMove("tilted", () -> new ManipulationTiltMove(manip));
        AutoFactory.addMoveMove("lift", () -> new ManipulationLiftMove(manip));
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

        int position = config.getSetting("auto_position", -1);
        MatchData.OwnedSide sw = MatchData.getOwnedSide(GameFeature.SWITCH_NEAR);
        MatchData.OwnedSide sc = MatchData.getOwnedSide(GameFeature.SCALE);
        String autoChoice = "empty-auto.json";
        switch(position) {
        case 1:
            if(sw == MatchData.OwnedSide.LEFT) {
                autoChoice = "left_switch.json";
            } else if(sc == MatchData.OwnedSide.LEFT) {
                autoChoice = "left_scale.json";
            } else {
                autoChoice = "min-auto.json";
            }
            break;
        case 2:
            if(sw == MatchData.OwnedSide.LEFT) {
                autoChoice = "middle_left.json";
            } else {
                autoChoice = "middle_right.json";
            }
            break;
        case 3:
            if(sw == MatchData.OwnedSide.RIGHT) {
                autoChoice = "right_switch.json";
            } else if(sc == MatchData.OwnedSide.RIGHT) {
                autoChoice = "right_scale.json";
            } else {
                autoChoice = "min-auto.json";
            }
            break;
        default:
            autoChoice = "min-auto.json";
        }
        
        auto = new JsonAutoFactory().makeAuto("/home/lvuser/auto/"+autoChoice);
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
        drive.enableDriving();
        manip.disableBrake();

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
        if(!climbing) {
            drive.enableDriving();
            double speedmultiplier = (0.4*driver.getTriggerAxis(Hand.kLeft)+0.6);
            drive.arcadeDrive(driver.getX(Hand.kRight)*speedmultiplier,
                    -speedmultiplier*driver.getY(Hand.kLeft));
        } else {
            climb.climb();
        }
        
        //manipulation height control
        if(up.check(operator.getPOV() == 0)) {
            manip.changeLiftHeight(5);
        }
        if(down.check(operator.getPOV() == 180)) {
            manip.changeLiftHeight(-5);
        }
        
        if(operator.getAButton()){
            manip.setLiftPos(LiftPos.GROUND);
        }
        if(operator.getBButton()){
            manip.setLiftPos(LiftPos.HOVER);
        }
        if(operator.getYButton()){
            manip.setLiftPos(LiftPos.SWITCH);
        }
        if(operator.getBumper(Hand.kLeft)){
            manip.setLiftPos(LiftPos.SCALE_LOW);
        }
        if(operator.getBumper(Hand.kRight)){
            manip.setLiftPos(LiftPos.SCALE_HIGH);
        }
        
        if(Math.abs(operator.getY(Hand.kLeft))>0.1) {
            manip.setFirstStageHeight(manip.getFirstStageHeight()-(operator.getY(Hand.kLeft)*5));
        }
        
        if(Math.abs(operator.getY(Hand.kRight))>0.1) {
            manip.setSecondStageHeight(manip.getSecondStageHeight()-(operator.getY(Hand.kRight)*5));
        }
        
        //manipulation brake
        if(operator.getXButton()) {
            manip.enableBrake();
        } else{
            manip.disableBrake();
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

        //reset stages
        if(operator.getBackButton()) {
            manip.resetFirstStagePosition();
        }
        if(operator.getStartButton()) {
            manip.resetSecondStagePosition();
        }
        
        //update manipulation
        manip.update();

        data.log("time", System.currentTimeMillis()-enableStart);
        data.logAll();
    }

    @Override
    public void testInit() {
        startLogging(data, "test");
    }

    @Override
    public void testPeriodic() {
        data.log("time", System.currentTimeMillis()-enableStart);
        data.logAll();
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
        manualSpeed = config.getSetting("manual_speed", 10.0);
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
