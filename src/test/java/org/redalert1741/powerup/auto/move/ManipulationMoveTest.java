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
    static FakeTalonSrxWrapper lift;
    static FakeDoubleSolenoidWrapper tilt;
    static FakeSolenoidWrapper brake;
    static ManualEnd manual1, manual2;

    @BeforeClass
    public static void initManipulation() {
        lift = new FakeTalonSrxWrapper();
        tilt = new FakeDoubleSolenoidWrapper();
        brake = new FakeSolenoidWrapper();
        manipulation = new Manipulation(lift, tilt, brake);
        AutoFactory.addMoveMove("tilt", () -> new ManipulationTiltMove(manipulation));

        manual1 = new ManualEnd();
        manual2 = new ManualEnd();
        AutoFactory.addMoveEnd("manual1", () -> manual1);
        AutoFactory.addMoveEnd("manual2", () -> manual2);
    }

    @Before
    public void setupTests() {
        manipulation.tiltOut();
        manipulation.setLift(0);

        manual1.completed = false;
        manual2.completed = false;
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
}
