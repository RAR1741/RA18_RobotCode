package org.redalert1741.powerup.auto.move;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.redalert1741.powerup.TankDrive;
import org.redalert1741.robotbase.auto.ManualEnd;
import org.redalert1741.robotbase.auto.core.AutoFactory;
import org.redalert1741.robotbase.auto.core.Autonomous;
import org.redalert1741.robotbase.auto.core.JsonAutoFactory;
import org.redalert1741.robotbase.wrapper.FakeSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.FakeTalonSrxWrapper;

public class TankDriveMoveTest {
    static ManualEnd manual1, manual2;
    static TankDrive drive;
    static FakeTalonSrxWrapper left1, left2, right1, right2;
    static FakeSolenoidWrapper shifter;

    @BeforeClass
    public static void setupAuto() {
        left1 = new FakeTalonSrxWrapper();
        left2 = new FakeTalonSrxWrapper();
        right1 = new FakeTalonSrxWrapper();
        right2 = new FakeTalonSrxWrapper();
        shifter = new FakeSolenoidWrapper();
        drive = new TankDrive(left1, left2, right1, right2, shifter);
        AutoFactory.addMoveMove("drive", () -> new TankDriveArcadeMove(drive));

        manual1 = new ManualEnd();
        manual2 = new ManualEnd();
        AutoFactory.addMoveEnd("manual1", () -> manual1);
        AutoFactory.addMoveEnd("manual2", () -> manual2);
    }

    @Before
    public void setupTests() {
        drive.arcadeDrive(0, 0);
        manual1.completed = false;
        manual2.completed = false;
    }

    @Test
    public void driveForwardTest() {
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("forward-tank-auto.json").getPath());
        auto.start();
        assertEquals(0, left1.get(), 0.001);
        assertEquals(0, right1.get(), 0.001);
        auto.run();
        assertEquals(1, left1.get(), 0.001);
        assertEquals(1, right1.get(), 0.001);
        auto.run();
        assertEquals(1, left1.get(), 0.001);
        assertEquals(1, right1.get(), 0.001);
        manual1.completed = true;
        auto.run();
        assertEquals(0, left1.get(), 0.001);
        assertEquals(0, right1.get(), 0.001);
    }

    @Test
    public void driveLeftRightTest() {
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("leftright-tank-auto.json").getPath());
        auto.start();
        assertEquals(0, left1.get(), 0.001);
        assertEquals(0, right1.get(), 0.001);
        auto.run();
        assertEquals(-1, left1.get(), 0.001);
        assertEquals(1, right1.get(), 0.001);
        auto.run();
        assertEquals(-1, left1.get(), 0.001);
        assertEquals(1, right1.get(), 0.001);
        manual1.completed = true;
        auto.run();
        auto.run();
        assertEquals(1, left1.get(), 0.001);
        assertEquals(-1, right1.get(), 0.001);
        auto.run();
        assertEquals(1, left1.get(), 0.001);
        assertEquals(-1, right1.get(), 0.001);
        manual2.completed = true;
        auto.run();
        auto.run();
        assertEquals(0, left1.get(), 0.001);
        assertEquals(0, right1.get(), 0.001);
    }
}
