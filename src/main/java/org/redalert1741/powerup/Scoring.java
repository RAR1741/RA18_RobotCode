package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;
import org.redalert1741.robotbase.wrapper.DoubleSolenoidWrapper;

public class Scoring implements Loggable {
    DoubleSolenoidWrapper grabber;
    DoubleSolenoidWrapper kicker;

    /**
     * Constructor for scoring subsystem.
     * @param kickIn ID 1 for DoubleSolenoid kicker
     * @param kickOut ID 2 for DoubleSolenoid kicker
     * @param grab ID 1 for DoubleSolenoid grabber
     * @param drop ID 2 for DoubleSolenoid grabber
     * @see DoubleSolenoid
     */
    public Scoring(DoubleSolenoidWrapper kick, DoubleSolenoidWrapper grab) {
        this.grabber = kick;
        this.kicker = grab;
    }
    
    public void open() {
        grabber.set(Value.kForward);
    }

    public void close() {
        grabber.set(Value.kReverse);
    }
    
    public void kick() {
        kicker.set(Value.kForward);
    }

    public void retract() {
        kicker.set(Value.kReverse);
    }
    
    @Override
    public void setupLogging(DataLogger logger) {
        logger.addAttribute("grabber_state");
        logger.addAttribute("kicker_state");
    }

    @Override
    public void log(DataLogger logger) {
        logger.log("grabber_state", grabber.get());
        logger.log("kicker_state", kicker.get());
    }
}
