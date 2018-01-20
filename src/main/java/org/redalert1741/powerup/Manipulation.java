package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;

import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;

public class Manipulation implements Loggable {
    
    DoubleSolenoid tilt;
    Spark lift;
    
    /**
     * Constructor for manipulation subsystem.
     * @param liftID PWM channel of lift spark
     * @param in ID 1 for DoubleSolenoid
     * @param out ID 2 for DoubleSolenoid
     * @see Spark
     * @see DoubleSolenoid
     */
    public Manipulation(int liftId, int in, int out) {
        this.lift = new Spark(liftId);
        this.tilt = new DoubleSolenoid(in, out);
    }
    
    public void tiltIn() {
        tilt.set(Value.kReverse);
    }
    
    public void tiltOut() {
        tilt.set(Value.kForward);
    }
    
    public void setLift(double input) {
        lift.set(input);
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
