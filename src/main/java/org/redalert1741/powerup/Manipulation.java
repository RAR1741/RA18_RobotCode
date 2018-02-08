package org.redalert1741.powerup;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import org.redalert1741.robotbase.logging.DataLogger;
import org.redalert1741.robotbase.logging.Loggable;
import org.redalert1741.robotbase.wrapper.DoubleSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.SolenoidWrapper;
import org.redalert1741.robotbase.wrapper.TalonSrxWrapper;

public class Manipulation implements Loggable {
    DoubleSolenoidWrapper tilt;
    TalonSrxWrapper lift;
    SolenoidWrapper brake;
    
    /**
     * Constructor for manipulation subsystem.
     * @param lift lift motor
     * @param tilt manipulation tilt
     * @param brake manipulation brake
     * @see Spark
     * @see DoubleSolenoid
     * @see Solenoid
     */
    public Manipulation(TalonSrxWrapper lift, DoubleSolenoidWrapper tilt,
            SolenoidWrapper brake) {
        this.lift = lift;
        this.tilt = tilt;
        this.brake = brake;
    }
    
    public void tiltIn() {
        tilt.set(Value.kReverse);
    }
    
    public void tiltOut() {
        tilt.set(Value.kForward);
    }
    
    public void setLift(double input) {
        lift.set(ControlMode.PercentOutput, input);
    }
    
    public void enableBrake() {
        brake.set(true);
    }
    
    public void disableBrake() {
        brake.set(false);
    }
    
    @Override
    public void setupLogging(DataLogger logger) {
        logger.addAttribute("tilt_state");
        logger.addAttribute("brake_state");
        logger.addAttribute("Imotor_current");
        logger.addAttribute("Imotor_voltage");
    }

    @Override
    public void log(DataLogger logger) {
        logger.log("tilt_state", tilt.get());
        logger.log("brake_state", brake.get());
        logger.log("Imotor_current", lift.getOutputCurrent());
        logger.log("Imotor_voltage", lift.getBusVoltage());
    }
}
