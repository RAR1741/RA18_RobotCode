package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.Scoring;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class ScoringKickerMove implements AutoMoveMove {
    private Scoring score;
    private boolean kick;

    public ScoringKickerMove(Scoring score) {
        this.score = score;
    }

    @Override
    public void setArgs(Map<String, String> args) {
        kick = Boolean.parseBoolean(args.get("kick"));
    }

    @Override
    public void start() {/*doesn't need init*/}

    @Override
    public void run() {
        if(kick) {
            score.kick();
        } else {
            score.retract();
        }
    }

    @Override
    public void stop() {/*doesn't need init*/}
}