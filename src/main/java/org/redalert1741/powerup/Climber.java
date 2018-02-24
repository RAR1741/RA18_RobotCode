package org.redalert1741.powerup;

public class Climber {
    Manipulation manip;
    TankDrive drive;

    public Climber(Manipulation manip, TankDrive drive) {
        this.manip = manip;
        this.drive = drive;
    }

    public void climb() {
        drive.enableClimbing();
        if(manip.getFirstStageAtBottom()) {
            drive.arcadeDrive(0, -1);
            manip.setFirstStage(1);
        } else {
            drive.arcadeDrive(0, 0);
            manip.setFirstStage(0);
        }
    }
}
