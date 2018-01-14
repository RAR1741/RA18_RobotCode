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
}
