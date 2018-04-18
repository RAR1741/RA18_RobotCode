package org.redalert1741.powerup.auto.move;

import static org.junit.Assert.*;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.redalert1741.powerup.Manipulation;
import org.redalert1741.robotbase.auto.ManualEnd;
import org.redalert1741.robotbase.auto.core.AutoFactory;
import org.redalert1741.robotbase.auto.core.Autonomous;
import org.redalert1741.robotbase.auto.core.JsonAutoFactory;
import org.redalert1741.robotbase.wrapper.FakeDoubleSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.FakeSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.FakeTalonSrxWrapper;

public class ManipulationMoveTest {
    static Manipulation manipulation;
    static FakeTalonSrxWrapper second, up, first;
    static FakeDoubleSolenoidWrapper tilt;
    static FakeSolenoidWrapper brake;
    static FakeSolenoidWrapper startBrake;
    static ManualEnd manual1, manual2;

    @BeforeClass
    public static void initManipulation() {
        second = new FakeTalonSrxWrapper();
        tilt = new FakeDoubleSolenoidWrapper();
        brake = new FakeSolenoidWrapper();
        first = new FakeTalonSrxWrapper();
        startBrake = new FakeSolenoidWrapper();
        manipulation = new Manipulation(first, second, tilt, brake, startBrake);
        AutoFactory.addMoveMove("tilt", () -> new ManipulationTiltMove(manipulation));
        AutoFactory.addMoveMove("reset", () -> new ManipulationLiftResetPosMove(manipulation));

        manual1 = new ManualEnd();
        manual2 = new ManualEnd();
        AutoFactory.addMoveEnd("manual1", () -> manual1);
        AutoFactory.addMoveEnd("manual2", () -> manual2);
    }

    @Before
    public void setupTests() {
        manipulation.tiltOut();

        manual1.completed = false;
        manual2.completed = false;
        
        first.position = 0;
        second.position = 0;
    }

    @Test
    public void tiltTest() {
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("tilt-manipulation-auto.json").getPath());
        auto.start();
        assertEquals(Value.kForward, tilt.value);
        auto.run();
        assertEquals(Value.kReverse, tilt.value);
        auto.run();
        assertEquals(Value.kReverse, tilt.value);
        manual1.completed = true;
        auto.run();
        auto.run();
        assertEquals(Value.kForward, tilt.value);
        auto.run();
        assertEquals(Value.kForward, tilt.value);
        manual2.completed = true;
        auto.run();
        auto.run();
        assertEquals(Value.kForward, tilt.value);
    }
    
    @Test
    public void resetTest() {
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("reset-manipulation-auto.json").getPath());
        first.position = 100;
        second.position = 100;
        auto.start();
        assertEquals(first.position, 100);
        assertEquals(second.position, 100);
        auto.run();
        assertEquals(first.position, 0);
        assertEquals(first.position, 0);
        auto.run();
        assertEquals(first.position, 0);
        assertEquals(first.position, 0);
    }
}
