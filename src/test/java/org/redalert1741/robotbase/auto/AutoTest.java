package org.redalert1741.robotbase.auto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.redalert1741.robotBase.auto.core.AutoFactory;
import org.redalert1741.robotBase.auto.core.AutoMoveEnd;
import org.redalert1741.robotBase.auto.core.AutoMoveMove;
import org.redalert1741.robotBase.auto.core.Autonomous;
import org.redalert1741.robotBase.auto.core.JsonAutoFactory;

public class AutoTest {
    class CounterMove implements AutoMoveMove {
        public int count;

        @Override
        public void setArgs(Map<String, String> args) {}

        @Override
        public void start() {
            count = 0;
        }

        @Override
        public void run() {
            count++;
        }

        @Override
        public void stop() {}    
    }

    class ManualEnd implements AutoMoveEnd {
        public boolean completed;
        
        @Override
        public void setArgs(Map<String, String> args) {}

        @Override
        public void start() {
            completed = false;
        }

        @Override
        public boolean isFinished() {
            return completed;
        }
    }
    
    @Test
    void jsonAutoEmptyTest() {
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("empty-test.json").getPath());
        auto.start();
        auto.run();
        auto.run();
    }

    @Test
    void jsonAutoSyncTest() {
        CounterMove counter = new CounterMove();
        AutoFactory.addMoveMove("counter", () -> counter);
        ManualEnd manual = new ManualEnd();
        AutoFactory.addMoveEnd("manual", () -> manual);
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("sync-test.json").getPath());
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
    void jsonAutoAsyncTest() {
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
}
