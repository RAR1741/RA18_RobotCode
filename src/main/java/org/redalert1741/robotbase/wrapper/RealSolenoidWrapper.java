package org.redalert1741.robotbase.wrapper;

import edu.wpi.first.wpilibj.Solenoid;

public class RealSolenoidWrapper extends SolenoidWrapper {
    private Solenoid solenoid;

    public RealSolenoidWrapper(int id) {
        solenoid = new Solenoid(id);
    }

    @Override
    public void set(boolean value) {
        solenoid.set(value);
    }

    @Override
    public boolean get() {
        return solenoid.get();
    }
}
