package org.redalert1741.powerup;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;

import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;

public class Manipulation implements Loggable {
    
    DoubleSolenoid tilt;
    Spark lift;
    
    public Manipulation(int tilt, int lift) {
        
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
