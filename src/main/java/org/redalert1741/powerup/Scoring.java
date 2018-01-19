package org.redalert1741.powerup;

import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;

public class Scoring implements Loggable {
    Spark left, right;
    DoubleSolenoid grabber;
    
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

    @Override
    public void setupLogging(DataLogger logger) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void log(DataLogger logger) {
        // TODO Auto-generated method stub

    }
    
    
    
}
