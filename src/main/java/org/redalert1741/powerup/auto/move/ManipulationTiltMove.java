package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.Manipulation;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class ManipulationTiltMove implements AutoMoveMove {
    private Manipulation manip;
    private boolean tilt;

    public ManipulationTiltMove(Manipulation manip) {
        this.manip = manip;
    }
    
    @Override
    public void setArgs(Map<String, String> args) {
        tilt = Boolean.parseBoolean(args.get("tilt"));
    }

    @Override
    public void start() {/*doesn't need init*/}

    @Override
    public void run() {
        if(tilt) {
            manip.tiltIn();
        } else {
            manip.tiltOut();
        }
    }

    @Override
    public void stop() {/*doesn't need init*/}

}
