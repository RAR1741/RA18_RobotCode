package org.redalert1741.robotbase.auto.core;

import java.util.List;
import java.util.Map;

/**
 * An intermediate representation of an {@link Autonomous}.
 * Does not run.
 */
public class AutoPojo {
    /**
     * Creates the representation of an {@link Autonomous}.
     * <br>
     * Does nothing.
     */
    public AutoPojo() { /* gson wants this */ }

    List<MovePojo> auto;

    static class MovePojo {
        public MovePojo() { /* gson wants this */ }

        String type;
        Map<String, String> args;
        Map<String, Object> moveargs;
        EndPojo end;

        static class EndPojo {
            public EndPojo() { /* gson still wants this */ }

            String type;
            Map<String, String> args;
        }
    }
}
