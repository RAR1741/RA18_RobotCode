package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;

import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;

public class Scoring implements Loggable {
    Spark left;
    Spark right;
    DoubleSolenoid grabber;
    
    /**
     * Constructor for scoring subsystem.
     * @param leftP PWM channel of left spark
     * @param rightP PWM channel of right spark
     * @param grab ID 1 for DoubleSolenoid
     * @param drop ID 2 for DoubleSolenoid
     * @see Spark
     * @see DoubleSolenoid
     */
    public Scoring(int leftP, int rightP, int grab, int drop) {
        this.left = new Spark(leftP);
        this.right = new Spark(rightP);
        this.grabber = new DoubleSolenoid(grab, drop);
    }

    public void open() {
        grabber.set(Value.kForward);
    }

    public void close() {
        grabber.set(Value.kReverse);
    }
    
    public void setIntake(double input) {
        left.set(input);
        right.set(-input);
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
