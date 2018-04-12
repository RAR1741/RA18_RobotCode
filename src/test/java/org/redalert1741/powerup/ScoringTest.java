package org.redalert1741.powerup;

import static org.junit.Assert.*;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import org.junit.Before;
import org.junit.Test;
import org.redalert1741.robotbase.wrapper.FakeDoubleSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.FakeSolenoidWrapper;

public class ScoringTest {
    Scoring scoring;
    FakeDoubleSolenoidWrapper grab;
    FakeSolenoidWrapper kick;

    @Before
    public void initScoring() {
        kick = new FakeSolenoidWrapper();
        grab = new FakeDoubleSolenoidWrapper();
        scoring = new Scoring(kick, grab);
    }

    @Test
    public void kickTest() {
        scoring.kick();
        assertEquals(true, kick.value);
        scoring.retract();
        assertEquals(false, kick.value);
    }

    @Test
    public void grabTest() {
        scoring.close();
        assertEquals(Value.kReverse, grab.value);
        scoring.open();
        assertEquals(Value.kForward, grab.value);
    }
}
