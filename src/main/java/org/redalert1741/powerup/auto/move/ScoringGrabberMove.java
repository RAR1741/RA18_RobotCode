package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.Scoring;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class ScoringGrabberMove implements AutoMoveMove {
    private Scoring score;
    private boolean grab;
    
    public ScoringGrabberMove(Scoring score) {
        this.score = score;
    }
    
    @Override
    public void setArgs(Map<String, String> args) {
        grab = Boolean.parseBoolean(args.get("grab"));
    }

    @Override
    public void start() {/*doesn't need init*/}

    @Override
    public void run() {
        if(grab) {
            score.close();
        } else {
            score.open();
        }
    }

    @Override
    public void stop() {/*doesn't need init*/}

}
