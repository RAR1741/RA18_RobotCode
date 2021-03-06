package org.redalert1741.powerup;

import static org.junit.Assert.*;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import org.junit.Before;
import org.junit.Test;
import org.redalert1741.robotbase.config.Config;
import org.redalert1741.robotbase.wrapper.FakeDoubleSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.FakeSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.FakeTalonSrxWrapper;

public class ManipulationTest {
    Manipulation manipulation;
    FakeTalonSrxWrapper lift;
    FakeTalonSrxWrapper down;
    FakeTalonSrxWrapper liftFollow;
    FakeDoubleSolenoidWrapper tilt;
    FakeSolenoidWrapper brake;
    FakeSolenoidWrapper startBrake;
    Config config;

    @Before
    public void initManipulation() {
        lift = new FakeTalonSrxWrapper();
        tilt = new FakeDoubleSolenoidWrapper();
        down = new FakeTalonSrxWrapper();
        liftFollow = new FakeTalonSrxWrapper();
        brake = new FakeSolenoidWrapper();
        manipulation = new Manipulation(down, lift, liftFollow, tilt, brake, startBrake);
        config = new Config();
        config.loadFromFile(getClass().getResource("manipulationconfig.txt").getPath());
        config.addConfigurable(manipulation);
    }

    @Test
    public void tiltTest() {
        manipulation.tiltIn();
        assertEquals(Value.kReverse, tilt.get());
        manipulation.tiltOut();
        assertEquals(Value.kForward, tilt.get());
    }

    @Test
    public void brakeTest() {
        manipulation.enableBrake();
        assertTrue(brake.get());
        manipulation.disableBrake();
        assertFalse(brake.get());
    }

    @Test
    public void configTest() {
        config.reloadConfig();
        assertEquals(15, lift.getTimeout());
    }
}
