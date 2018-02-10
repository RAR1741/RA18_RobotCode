package org.redalert1741.robotbase.wrapper;

import edu.wpi.first.wpilibj.Solenoid;

public class RealSolenoidWrapper extends SolenoidWrapper {
    private Solenoid solenoid;

    /**
     * A real solenoid. Logging name is "solenoid_{id}"
     * @param id PCM ID
     */
    public RealSolenoidWrapper(int id) {
        solenoid = new Solenoid(id);
        logname = "solenoid_"+id;
    }

    /**
     * A real solenoid.
     * @param id PCM ID
     * @param name logging name
     */
    public RealSolenoidWrapper(int id, String name) {
        solenoid = new Solenoid(id);
        logname = name;
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
