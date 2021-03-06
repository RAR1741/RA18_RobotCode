package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.TankDrive;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class TankDriveArcadeMove implements AutoMoveMove {
    private TankDrive drive;
    private double xdrive;
    private double ydrive;

    public TankDriveArcadeMove(TankDrive drive) {
        this.drive = drive;
    }

    @Override
    public void setArgs(Map<String, String> args) {
        xdrive = Double.parseDouble(args.get("xdrive"));
        ydrive = Double.parseDouble(args.get("ydrive"));
    }

    @Override
    public void start() { /* doesn't need init */ }

    @Override
    public void run() {
        drive.arcadeDrive(xdrive, ydrive);
    }

    @Override
    public void stop() {
        drive.arcadeDrive(0, 0);
    }
}
