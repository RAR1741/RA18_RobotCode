package org.redalert1741.powerup;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;

import org.redalert1741.robotbase.config.Config;
import org.redalert1741.robotbase.config.Configurable;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;
import org.redalert1741.robotbase.wrapper.DoubleSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.SolenoidWrapper;
import org.redalert1741.robotbase.wrapper.TalonSrxWrapper;

public class Manipulation implements Loggable, Configurable {
    private DoubleSolenoidWrapper tilt;
    private TalonSrxWrapper first;
    private TalonSrxWrapper second;
    private SolenoidWrapper brake;
    private double forwardSpeed;
    private double reverseSpeed;
    private double groundH;
    private double hoverH;
    private double switchH;
    private double scaleLowH;
    private double scaleHighH;
    private double targetHeight;
    private double firstStageMaxHeight;
    private double secondStageMaxHeight;
    private double firstStageHeightToTick;
    private double secondStageHeightToTick;
    
    protected enum LiftPos{
        Ground(0),Hover(1),Switch(2),ScaleLow(3),ScaleHigh(4);
        private final int i;
        private LiftPos(int i) {
            this.i = i;
        }

        public LiftPos next() {
            return i<LiftPos.values().length-1 ? 
                    LiftPos.values()[i+1] : LiftPos.values()[i];
        }
        
        public LiftPos prev() {
            return i>0 ? 
                    LiftPos.values()[i-1] : LiftPos.values()[i];
        }
    }
    
    /**
     * Constructor for manipulation subsystem.
     * @param firstDown motor that pulls the second stage down
     * @param firstUp motor that pulls the second stage up
     * @param top motor that moves the third stage on the second
     * @param tilt manipulation tilt
     * @param brake manipulation brake
     * @see TalonSrxWrapper
     * @see DoubleSolenoid
     * @see Solenoid
     */
    public Manipulation(TalonSrxWrapper firstStage, TalonSrxWrapper secondStage,
            DoubleSolenoidWrapper tilt, SolenoidWrapper brake) {
        this.first = firstStage;
        this.second = secondStage;
        this.tilt = tilt;
        this.brake = brake;

        forwardSpeed = 1;
        reverseSpeed = -1;
        
        targetHeight = 0;
        
        first.configNominalOutputForward(0);
        first.configNominalOutputReverse(0);
        first.configPeakOutputForward(forwardSpeed);
        first.configPeakOutputReverse(reverseSpeed);
        first.setPhase(true);
        
        second.configNominalOutputForward(0);
        second.configNominalOutputReverse(0);
        second.configPeakOutputForward(forwardSpeed);
        second.configPeakOutputReverse(reverseSpeed);
        //second.setPhase(true);
    }
    
    public void tiltIn() {
        tilt.set(Value.kReverse);
    }

    public void tiltOut() {
        tilt.set(Value.kForward);
    }

    /**
     * Sets the first stage motor to move the first stage.
     * @param input percent speed to run up or down
     */
    public void setFirstStage(double input) {
        if(!brake.get()) {
            first.set(ControlMode.PercentOutput, input);
        } else {
            first.set(ControlMode.PercentOutput, 0);
        }
    }

    /**
     * Target given position for the first stage.
     * @param pos Position to target, (~-4000 for top, 0 for bottom)
     */
    public void setFirstStagePos(int pos) {
        if(!brake.get()) {
            first.set(ControlMode.Position, pos);
        } else {
            first.set(ControlMode.PercentOutput, 0);
        }
    }
    
    public void setFirstStageHeight(double height){
        this.setFirstStagePos(
                -(int)(height*firstStageHeightToTick));
    }
    
    /**
     * Sets the second stage motor to move the second stage.
     * @param input percent speed to run up or down
     */
    public void setSecondStage(double input) {
        second.set(ControlMode.PercentOutput, input);
    }

    /**
     * Target given position for the second stage.
     * @param pos Position to target, (~?? for top, 0 for bottom)
     */
    public void setSecondStagePos(int pos) {
        second.set(ControlMode.Position, pos);
    }
    
    public void setSecondStageHeight(double height){
        this.setSecondStagePos(
                (int)(height*secondStageHeightToTick));
    }

    public int getFirstStagePos(){
        return first.getPosition();
    }
    
    public int getSecondStagePos(){
        return second.getPosition();
    }
    
    public void enableBrake() {
        brake.set(true);
    }
    
    public void disableBrake() {
        brake.set(false);
    }

    public void resetFirstStagePosition() {
        first.setPosition(0);
    }
    
    public void resetSecondStagePosition() {
        second.setPosition(0);
    }

    public boolean getFirstStageAtBottom() {
        return first.getForwardLimit();
    }
    
    public boolean getFirstStageAtTop() {
        return first.getReverseLimit();
    }
    
    public boolean getSecondStageAtBottom() {
        return second.getForwardLimit();
    }
    
    public boolean getSecondStageAtTop() {
        return second.getReverseLimit();
    }

    public void setLiftPos(LiftPos pos){
        switch(pos){
        case Ground:
            setLiftHeight(groundH);
            break;
        case Hover:
            setLiftHeight(hoverH);
            break;
        case Switch:
            setLiftHeight(switchH);
            break;
        case ScaleLow:
            setLiftHeight(scaleLowH);
            break;
        case ScaleHigh:
            setLiftHeight(scaleHighH);
            break;
        default:
            break;
        }
    }
    
    public void setLiftHeight(double height){
        targetHeight = height;
        if(targetHeight < secondStageMaxHeight) {
            setSecondStageHeight(targetHeight);
            setFirstStageHeight(0);
        } else if(targetHeight >= secondStageMaxHeight) {
            setSecondStageHeight(secondStageMaxHeight);
            setFirstStageHeight(targetHeight-secondStageMaxHeight);
        }
    }
    
    public void changeLiftHeight(double height){
        targetHeight+=height;
        setLiftHeight(targetHeight);
    }
    
    @Override
    public void setupLogging(DataLogger logger) {
        brake.setupLogging(logger);
        tilt.setupLogging(logger);
        first.setupLogging(logger);
        second.setupLogging(logger);
    }

    @Override
    public void log(DataLogger logger) {
        brake.log(logger);
        tilt.log(logger);
        first.log(logger);
        second.log(logger);
    }

    @Override
    public void reloadConfig(Config config) {
        first.reloadConfig(config);
        first.setP(config.getSetting("first_p", 1.0));
        first.setI(config.getSetting("first_i", 0.0));
        first.setD(config.getSetting("first_d", 0.0));
        
        second.reloadConfig(config);
        second.setP(config.getSetting("second_p", 1.0));
        second.setI(config.getSetting("second_i", 0.0));
        second.setD(config.getSetting("second_d", 0.0));
        
        groundH = config.getSetting("ground_height", 0.0);
        hoverH = config.getSetting("hover_height", 0.0);
        System.out.println(hoverH);
        switchH = config.getSetting("switch_height", 0.0);
        System.out.println(switchH);
        scaleLowH = config.getSetting("scaleLow_height", 0.0);
        scaleHighH = config.getSetting("scaleHigh_height", 0.0);
        firstStageMaxHeight = config.getSetting("firstMaxHeight", 41.0);
        secondStageMaxHeight = config.getSetting("firstMaxHeight", 32.0);
        secondStageHeightToTick = config.getSetting(
                "second_height_to_tick", 26290.3);
        firstStageHeightToTick = config.getSetting(
                "first_height_to_tick", 97.5);
    }
}
