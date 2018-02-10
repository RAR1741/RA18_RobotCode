package org.redalert1741.robotbase.wrapper;

import edu.wpi.first.wpilibj.Solenoid;

public class RealSolenoidWrapper extends SolenoidWrapper {
    private Solenoid solenoid;

    /**
     * A real solenoid. Logging name is "solenoid_{id}"
     * @param pcm CAN ID of the PCM
     * @param id PCM ID of the solenoid
     */
    public RealSolenoidWrapper(int pcm, int id) {
        this(pcm, id, "solenoid_"+id);
    }

    /**
     * A real solenoid.
     * @param pcm CAN ID of the PCM
     * @param id PCM ID of the solenoid
     * @param name logging name
     */
    public RealSolenoidWrapper(int pcm, int id, String name) {
        solenoid = new Solenoid(pcm, id);
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
