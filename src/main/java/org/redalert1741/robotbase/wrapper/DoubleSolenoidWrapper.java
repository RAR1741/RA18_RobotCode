package org.redalert1741.robotbase.wrapper;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public interface DoubleSolenoidWrapper {
    public void set(Value value);

    public Value get();
}
