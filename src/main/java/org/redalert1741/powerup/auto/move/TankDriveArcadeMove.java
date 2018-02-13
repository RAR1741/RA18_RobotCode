package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.TankDrive;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class TankDriveArcadeMove implements AutoMoveMove {
    private TankDrive drive;
    private double x;
    private double y;

    public TankDriveArcadeMove(TankDrive drive) {
        this.drive = drive;
    }

    @Override
    public void setArgs(Map<String, String> args) {
        x = Double.parseDouble(args.get("x"));
        y = Double.parseDouble(args.get("y"));
    }

    @Override
    public void start() { /* doesn't need init */ }

    @Override
    public void run() {
        drive.arcadeDrive(x, y);
    }

    @Override
    public void stop() {
        drive.arcadeDrive(0, 0);
    }

}
