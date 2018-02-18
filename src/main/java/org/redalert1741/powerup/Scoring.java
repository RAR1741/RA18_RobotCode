package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;
import org.redalert1741.robotbase.wrapper.DoubleSolenoidWrapper;

public class Scoring implements Loggable {
    private DoubleSolenoidWrapper grabber;
    private DoubleSolenoidWrapper kicker;

    /**
     * Constructor for scoring subsystem.
     * @param kick piston for the kicker
     * @param grab piston for the grabber
     * @see DoubleSolenoid
     */
    public Scoring(DoubleSolenoidWrapper kick, DoubleSolenoidWrapper grab) {
        this.kicker = kick;
        this.grabber = grab;
    }
    
    public void open() {
        grabber.set(Value.kForward);
    }

    public void close() {
        grabber.set(Value.kReverse);
    }

    public void grabOff() {
        grabber.set(Value.kOff);
    }
    
    public void kick() {
        kicker.set(Value.kForward);
    }

    public void retract() {
        kicker.set(Value.kReverse);
    }
    
    @Override
    public void setupLogging(DataLogger logger) {
        kicker.setupLogging(logger);
        grabber.setupLogging(logger);
    }

    @Override
    public void log(DataLogger logger) {
        kicker.log(logger);
        grabber.log(logger);
    }
}
