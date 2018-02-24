package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.TankDrive;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class TankDriveBrakeMove implements AutoMoveMove {
    private TankDrive drive;
    private boolean brake;

    public TankDriveBrakeMove(TankDrive drive) {
        this.drive = drive;
    }

    @Override
    public void setArgs(Map<String, String> args) {
        brake = Boolean.parseBoolean(args.get("brake"));
    }

    @Override
    public void start() {
        drive.setBrakes(brake);
    }

    @Override
    public void run() { /* no */ }

    @Override
    public void stop() { /* brake more continues */ }
}
