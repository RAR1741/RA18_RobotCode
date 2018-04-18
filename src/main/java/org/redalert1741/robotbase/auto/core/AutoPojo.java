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

    public List<MovePojo> auto;

    public static class MovePojo {
        public MovePojo() { /* gson wants this */ }

        public String type;
        public Map<String, String> args;
        public Map<String, Object> moveargs;
        public EndPojo end;

        public static class EndPojo {
            public EndPojo() { /* gson still wants this */ }

            public String type;
            public Map<String, String> args;
        }
    }
}
