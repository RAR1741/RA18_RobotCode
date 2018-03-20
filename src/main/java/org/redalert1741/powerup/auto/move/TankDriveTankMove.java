package org.redalert1741.powerup.auto.move;

import java.util.Map;

import org.redalert1741.powerup.TankDrive;
import org.redalert1741.robotbase.auto.core.AutoMoveMove;

public class TankDriveTankMove implements AutoMoveMove {
    private TankDrive drive;
    private double leftdrive;
    private double rightdrive;
    private boolean stop;
    private String mode;

    public TankDriveTankMove(TankDrive drive) {
        this.drive = drive;
    }

    @Override
    public void setArgs(Map<String, String> args) {
        leftdrive = Double.parseDouble(args.get("leftDrive"));
        rightdrive = Double.parseDouble(args.get("rightDrive"));
        stop = Boolean.parseBoolean(args.getOrDefault("stop", "true"));
        mode = args.getOrDefault("mode", "speed");
    }

    @Override
    public void start() { /* doesn't need init */ }

    @Override
    public void run() {
        if(mode.equals("speed")){
            drive.driveMotorsSpeed(leftdrive, rightdrive);
        } else{
            drive.driveMotorsPercentV(leftdrive, rightdrive);
        }
    }

    @Override
    public void stop() {
    	if(stop) {
    		drive.driveMotorsSpeed(0, 0);
    	}
    }
}
