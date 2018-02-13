package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.Manipulation;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class ManipulationLiftMove implements AutoMoveMove {
    private Manipulation manip;
    private double speed;

    public ManipulationLiftMove(Manipulation manip) {
        this.manip = manip;
    }

    @Override
    public void setArgs(Map<String, String> args) {
        speed = Double.parseDouble(args.get("speed"));
    }

    @Override
    public void start() {/*doesn't need init*/}

    @Override
    public void run() {
        manip.setLift(speed);
    }

    @Override
    public void stop() {
        manip.setLift(0);
    }
}
