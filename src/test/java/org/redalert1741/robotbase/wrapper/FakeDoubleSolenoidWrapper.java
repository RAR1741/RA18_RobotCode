package org.redalert1741.robotbase.wrapper;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class FakeDoubleSolenoidWrapper extends DoubleSolenoidWrapper {
    public Value value;
    
    public FakeDoubleSolenoidWrapper() {
        value = Value.kOff;
    }
    
    @Override
    public void set(Value value) {
        this.value = value;
    }

    @Override
    public Value get() {
        return value;
    }

}
