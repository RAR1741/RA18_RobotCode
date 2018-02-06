package org.redalert1741.robotbase.wrapper;

public class FakeSolenoidWrapper implements SolenoidWrapper {
    public boolean value;

    public FakeSolenoidWrapper() {
        value = false;
    }

    @Override
    public void set(boolean value) {
        this.value = value;
    }

    @Override
    public boolean get() {
        return value;
    }

}
