package org.redalert1741.powerup.auto.move;

import static org.junit.Assert.*;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.redalert1741.powerup.Scoring;
import org.redalert1741.robotbase.auto.ManualEnd;
import org.redalert1741.robotbase.auto.core.AutoFactory;
import org.redalert1741.robotbase.auto.core.Autonomous;
import org.redalert1741.robotbase.auto.core.JsonAutoFactory;
import org.redalert1741.robotbase.wrapper.FakeDoubleSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.FakeSolenoidWrapper;

public class ScoringMoveTest {
    static Scoring scoring;
    static FakeDoubleSolenoidWrapper grab;
    static FakeSolenoidWrapper kick;
    static ManualEnd manual1, manual2;

    @BeforeClass
    public static void initScoring() {
        kick = new FakeSolenoidWrapper();
        grab = new FakeDoubleSolenoidWrapper();
        scoring = new Scoring(kick, grab);
        AutoFactory.addMoveMove("kick", () -> new ScoringKickerMove(scoring));
        AutoFactory.addMoveMove("grab", () -> new ScoringGrabberMove(scoring));

        manual1 = new ManualEnd();
        manual2 = new ManualEnd();
        AutoFactory.addMoveEnd("manual1", () -> manual1);
        AutoFactory.addMoveEnd("manual2", () -> manual2);
    }

    @Before
    public void setupTests() {
        scoring.close();
        scoring.retract();
        manual1.completed = false;
        manual2.completed = false;
    }

    @Test
    public void kickTest(){
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("kick-scoring-auto.json").getPath());
        auto.start();
        assertEquals(false, kick.value);
        assertEquals(Value.kReverse, grab.value);
        auto.run();
        assertEquals(true, kick.value);
        assertEquals(Value.kReverse, grab.value);
        auto.run();
        assertEquals(true, kick.value);
        assertEquals(Value.kReverse, grab.value);
        manual1.completed = true;
        auto.run();
        auto.run();
        assertEquals(false, kick.value);
        assertEquals(Value.kReverse, grab.value);
        auto.run();
        assertEquals(false, kick.value);
        assertEquals(Value.kReverse, grab.value);
        manual2.completed = true;
        auto.run();
        auto.run();
        assertEquals(false, kick.value);
        assertEquals(Value.kReverse, grab.value);
    }

    @Test
    public void grabTest(){
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("grab-scoring-auto.json").getPath());
        auto.start();
        assertEquals(false, kick.value);
        assertEquals(Value.kReverse, grab.value);
        auto.run();
        assertEquals(false, kick.value);
        assertEquals(Value.kForward, grab.value);
        auto.run();
        assertEquals(false, kick.value);
        assertEquals(Value.kForward, grab.value);
        manual1.completed = true;
        auto.run();
        auto.run();
        assertEquals(false, kick.value);
        assertEquals(Value.kReverse, grab.value);
        auto.run();
        assertEquals(false, kick.value);
        assertEquals(Value.kReverse, grab.value);
        manual2.completed = true;
        auto.run();
        auto.run();
        assertEquals(false, kick.value);
        assertEquals(Value.kReverse, grab.value);
    }
}
