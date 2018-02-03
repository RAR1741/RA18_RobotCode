package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;

public class Manipulation implements Loggable {
    DoubleSolenoid tilt;
    Spark lift;
    Solenoid brake;
    
    /**
     * Constructor for manipulation subsystem.
     * @param liftId PWM channel of lift spark
     * @param in ID 1 for DoubleSolenoid
     * @param out ID 2 for DoubleSolenoid
     * @param b1 PCM ID of brake
     * @see Spark
     * @see DoubleSolenoid
     * @see Solenoid
     */
    public Manipulation(int liftId, int in, int out, int b1) {
        this.lift = new Spark(liftId);
        this.tilt = new DoubleSolenoid(in, out);
        this.brake = new Solenoid(b1);
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
    
    public void stop() {
        brake.set(true);
    }
    
    public void go() {
        brake.set(false);
    }
    
    @Override
    public void setupLogging(DataLogger logger) {
        logger.addAttribute("tilt_state");
        logger.addAttribute("brake_state");
    }

    @Override
    public void log(DataLogger logger) {
        logger.log("tilt_state", tilt.get());
        logger.log("brake_state", brake.get());
    }
}
