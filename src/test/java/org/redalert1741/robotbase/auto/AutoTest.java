package org.redalert1741.robotbase.auto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.redalert1741.robotbase.auto.core.AutoFactory;
import org.redalert1741.robotbase.auto.core.Autonomous;
import org.redalert1741.robotbase.auto.core.JsonAutoFactory;

public class AutoTest {
    @Test
    public void jsonAutoEmptyTest() {
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("empty-test.json").getPath());
        auto.start();
        auto.run();
        auto.run();
    }

    @Test
    public void jsonAutoSyncTest() {
        CounterMove counter = new CounterMove();
        AutoFactory.addMoveMove("counter", () -> counter);
        ManualEnd manual = new ManualEnd();
        AutoFactory.addMoveEnd("manual", () -> manual);
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("sync-test.json").getPath());
        runAutoSyncTest(auto, counter, manual);
    }

    @Test
    public void jsonAutoRestartTest() {
        CounterMove counter = new CounterMove();
        AutoFactory.addMoveMove("counter", () -> counter);
        ManualEnd manual = new ManualEnd();
        AutoFactory.addMoveEnd("manual", () -> manual);
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("sync-test.json").getPath());
        runAutoSyncTest(auto, counter, manual);
        runAutoSyncTest(auto, counter, manual);
    }

    public void runAutoSyncTest(Autonomous auto, CounterMove counter, ManualEnd manual) {
        auto.start();

        //no code should have run besides start, so count is zero
        assertEquals(0, counter.count);

        //running two times
        auto.run();
        assertEquals(1, counter.count);
        auto.run();
        assertEquals(2, counter.count);

        //current move runs final time
        manual.completed = true;
        auto.run();
        assertEquals(3, counter.count);

        //next move starts and runs once
        auto.run();
        assertEquals(1, counter.count);
        assertFalse(manual.completed);

        //running two more times
        auto.run();
        assertEquals(2, counter.count);
        auto.run();
        assertEquals(3, counter.count);

        //start finishing
        manual.completed = true;
        auto.run();
        assertEquals(4, counter.count);

        //counter will stop being called, so set count to weird number
        counter.count = -40;
        auto.run();
        assertEquals(-40, counter.count);
    }

    @Test
    public void jsonAutoAsyncTest() {
        CounterMove counter = new CounterMove();
        CounterMove asyncCounter1 = new CounterMove();
        CounterMove asyncCounter2 = new CounterMove();
        CounterMove asyncCounter3 = new CounterMove();
        AutoFactory.addMoveMove("counter", () -> counter);
        AutoFactory.addMoveMove("async-counter1", () -> asyncCounter1);
        AutoFactory.addMoveMove("async-counter2", () -> asyncCounter2);
        AutoFactory.addMoveMove("async-counter3", () -> asyncCounter3);

        ManualEnd manual1 = new ManualEnd();
        ManualEnd manual2 = new ManualEnd();
        ManualEnd manual3 = new ManualEnd();
        ManualEnd manual4 = new ManualEnd();

        AutoFactory.addMoveEnd("manual1", () -> manual1);
        AutoFactory.addMoveEnd("manual2", () -> manual2);
        AutoFactory.addMoveEnd("manual3", () -> manual3);
        AutoFactory.addMoveEnd("manual4", () -> manual4);

        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("async-test.json").getPath());
        auto.start();

        //only the first three moves run
        auto.run();
        assertEquals(1, counter.count);
        assertEquals(1, asyncCounter1.count);
        assertEquals(1, asyncCounter2.count);
        assertEquals(0, asyncCounter3.count);

        //finish first async move
        manual1.completed = true;
        auto.run();

        //first async move should have stopped
        auto.run();
        assertEquals(3, counter.count);
        assertEquals(2, asyncCounter1.count);
        assertEquals(3, asyncCounter2.count);
        assertEquals(0, asyncCounter3.count);

        //continue past sync move
        manual3.completed = true;
        auto.run();

        //second async move still running, next sync move and two async moves started
        auto.run();
        assertEquals(1, counter.count);
        assertEquals(5, asyncCounter2.count);
        assertEquals(1, asyncCounter1.count);
        assertEquals(1, asyncCounter3.count);

        //end new async
        manual1.completed = true;
        auto.run();

        //old async, new async, and sync move remain
        auto.run();
        assertEquals(3, counter.count);
        assertEquals(7, asyncCounter2.count);
        assertEquals(2, asyncCounter1.count);
        assertEquals(3, asyncCounter3.count);

        //stop sync
        manual3.completed = true;
        auto.run();

        //old async and new async remain
        auto.run();
        assertEquals(4, counter.count);
        assertEquals(9, asyncCounter2.count);
        assertEquals(5, asyncCounter3.count);

        //end remaining async
        manual2.completed = true;
        manual4.completed = true;
        auto.run();

        //everything should stop
        auto.run();
        assertEquals(10, asyncCounter2.count);
        assertEquals(6, asyncCounter3.count);
    }

    @Test
    public void missingArgsTest() {
        CounterMove counter = new CounterMove();
        AutoFactory.addMoveMove("counter", () -> counter);
        ManualEnd manual = new ManualEnd();
        AutoFactory.addMoveEnd("manual", () -> manual);
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("missing-args-test.json").getPath());
        auto.start();

        auto.run();
        auto.run();
        manual.completed = true;
        auto.run();
        auto.run();
        assertEquals(1, counter.count);
    }

    @Test
    public void noFileTest() {
        Autonomous auto = new JsonAutoFactory().makeAuto("bad path that i hope never exists");
        assertNull(auto);
    }
}
