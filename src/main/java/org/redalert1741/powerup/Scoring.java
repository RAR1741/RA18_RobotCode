package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;

public class Scoring implements Loggable {
    DoubleSolenoid grabber;
    DoubleSolenoid kicker;
    /**
     * Constructor for scoring subsystem.
     * @param kIn ID 1 for DoubleSolenoid kicker
     * @param kOut ID 2 for DoubleSolenoid kicker
     * @param grab ID 1 for DoubleSolenoid grabber
     * @param drop ID 2 for DoubleSolenoid grabber
     * @see DoubleSolenoid
     */
    public Scoring(int kOut, int kIn, int grab, int drop) {
        this.grabber = new DoubleSolenoid(grab, drop);
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
    }

    @Override
    public void log(DataLogger logger) {
        logger.log("grabber_state", grabber.get());
    }
}
