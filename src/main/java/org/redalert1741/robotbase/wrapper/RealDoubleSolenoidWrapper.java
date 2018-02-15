package org.redalert1741.robotbase.wrapper;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class RealDoubleSolenoidWrapper extends DoubleSolenoidWrapper {
    private DoubleSolenoid doublesolenoid;
    
    public RealDoubleSolenoidWrapper(int id1, int id2) {
        doublesolenoid = new DoubleSolenoid(6, id1, id2);
    }
    
    @Override
    public void set(Value value) {
        doublesolenoid.set(value);

    }

    @Override
    public Value get() {
        return doublesolenoid.get();
    }
}
