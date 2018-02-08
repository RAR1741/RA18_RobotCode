package org.redalert1741.powerup;

import static org.junit.Assert.*;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import org.junit.Before;
import org.junit.Test;
import org.redalert1741.robotbase.wrapper.FakeDoubleSolenoidWrapper;

public class ScoringTest {
    Scoring scoring;
    FakeDoubleSolenoidWrapper kick, grab;

    @Before
    public void initScoring() {
        kick = new FakeDoubleSolenoidWrapper();
        grab = new FakeDoubleSolenoidWrapper();
        scoring = new Scoring(kick, grab);
    }

    @Test
    public void kickTest() {
        scoring.kick();
        assertEquals(Value.kForward, kick.value);
        scoring.retract();
        assertEquals(Value.kReverse, kick.value);
    }

    @Test
    public void grabTest() {
        scoring.close();
        assertEquals(Value.kReverse, grab.value);
        scoring.open();
        assertEquals(Value.kForward, grab.value);
    }
}
