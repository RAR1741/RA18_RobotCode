package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.TankDrive;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class TankDriveRampRateMove implements AutoMoveMove {
    private TankDrive drive;
    private double rampRate;

    public TankDriveRampRateMove(TankDrive drive) {
        this.drive = drive;
    }

    @Override
    public void setArgs(Map<String, String> args) {
    	rampRate = Double.parseDouble(args.get("rampRate"));
    }

    @Override
    public void start() {
        drive.setRampRate(rampRate);
    }

    @Override
    public void run() { /* no */ }

    @Override
    public void stop() { /* brake more continues */ }
}
