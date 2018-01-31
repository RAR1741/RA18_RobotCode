package org.redalert1741.robotbase.auto.core;

import java.util.List;
import java.util.Map;

public class AutoPOJO {
    public AutoPOJO() { /* gson wants this */ }

    List<MovePOJO> auto;

    static class MovePOJO {
        public MovePOJO() { /* gson wants this */ }

        String type;
        Map<String, String> args;
        Map<String, Object> moveargs;
        EndPOJO end;

        static class EndPOJO {
            public EndPOJO() { /* gson still wants this */ }

            String type;
            Map<String, String> args;
        }
    }
}
