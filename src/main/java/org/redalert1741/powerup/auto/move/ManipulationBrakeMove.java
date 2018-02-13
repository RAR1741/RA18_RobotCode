package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.Manipulation;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class ManipulationBrakeMove implements AutoMoveMove {
    private Manipulation manip;
    private boolean brake;

    public ManipulationBrakeMove(Manipulation manip) {
        this.manip = manip;
    }

    @Override
    public void setArgs(Map<String, String> args) {
        brake = Boolean.parseBoolean(args.get("brake"));
    }

    @Override
    public void start() {/*doesn't need init*/}

    @Override
    public void run() {
        if(brake) {
            manip.enableBrake();
        } else {
            manip.disableBrake();
        }
    }

    @Override
    public void stop() {/*doesn't need init*/}
}