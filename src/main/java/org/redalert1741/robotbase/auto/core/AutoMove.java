package org.redalert1741.robotbase.auto.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single move within an {@link Autonomous}
 */
public class AutoMove {
    private AutoMoveMove amm;
    private AutoMoveEnd ame;
    private boolean finished;
    protected Map<String, Object> args;

    /**
     * Create an AutoMove
     * An AutoMove consists of three parts, the {@link AutoMoveMove move}, the {@link AutoMoveEnd end condition}, and any extra arguments.
     * @param amm Movement
     * @param ame End
     * @param args Extra arguments
     */
    public AutoMove(AutoMoveMove amm, AutoMoveEnd ame, Map<String, Object> args) {
        this.amm = amm;
        this.ame = ame;
        this.args = args == null ? new HashMap<String, Object>() : args;
        finished = false;
    }

    public AutoMoveMove getMove() { return amm; }
    public AutoMoveEnd getEnd() { return ame; }
    public boolean isFinshed() { return finished; }
    public boolean isAsync() { return (args.containsKey("async") ? (boolean) args.get("async") : false); }

    public void start() {
        amm.start();
        ame.start();
        finished = false;
    }

    /**
     * Iterative run. Also checks if the move is finished.
     */
    public void run() {
        amm.run();
        if(ame.isFinished()) {
            finished = true;
            amm.stop();
        }
    }
}
