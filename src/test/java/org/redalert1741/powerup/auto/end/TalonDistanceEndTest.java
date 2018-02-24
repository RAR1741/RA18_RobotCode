package org.redalert1741.powerup.auto.end;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.redalert1741.powerup.TankDrive;
import org.redalert1741.powerup.auto.move.TankDriveArcadeMove;
import org.redalert1741.robotbase.auto.core.AutoFactory;
import org.redalert1741.robotbase.auto.core.Autonomous;
import org.redalert1741.robotbase.auto.core.JsonAutoFactory;
import org.redalert1741.robotbase.wrapper.FakeSolenoidWrapper;
import org.redalert1741.robotbase.wrapper.FakeTalonSrxWrapper;

public class TalonDistanceEndTest {
    static TankDrive drive;
    static FakeTalonSrxWrapper left1, left2, right1, right2;
    static FakeSolenoidWrapper shifter;

    @BeforeClass
    public static void setupMoves() {
        left1 = new FakeTalonSrxWrapper();
        left2 = new FakeTalonSrxWrapper();
        right1 = new FakeTalonSrxWrapper();
        right2 = new FakeTalonSrxWrapper();
        shifter = new FakeSolenoidWrapper();
        drive = new TankDrive(left1, left2, right1, right2, shifter);
        AutoFactory.addMoveMove("drive", () -> new TankDriveArcadeMove(drive));
        AutoFactory.addMoveEnd("driveDist", () -> new TalonDistanceEnd(right1));
    }

    @Before
    public void resetEncoder() {
        right1.setPosition(0);
    }

    @Test
    public void driveTicksTest() {
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("talon-ticks-auto.json").getPath());
        auto.start();
        assertEquals(0, left1.get(), 0.001);
        assertEquals(0, right1.get(), 0.001);
        auto.run();
        assertEquals(1, left1.get(), 0.001);
        assertEquals(1, right1.get(), 0.001);
        auto.run();
        assertEquals(1, left1.get(), 0.001);
        assertEquals(1, right1.get(), 0.001);
        right1.setPosition(500);
        auto.run();
        assertEquals(1, left1.get(), 0.001);
        assertEquals(1, right1.get(), 0.001);
        auto.run();
        assertEquals(1, left1.get(), 0.001);
        assertEquals(1, right1.get(), 0.001);
        right1.setPosition(1001);
        auto.run();
        auto.run();
        assertEquals(0, left1.get(), 0.001);
        assertEquals(0, right1.get(), 0.001);
    }

    @Test
    public void driveInchesTest() {
        Autonomous auto = new JsonAutoFactory().makeAuto(getClass().getResource("talon-inches-auto.json").getPath());
        auto.start();
        assertEquals(0, left1.get(), 0.001);
        assertEquals(0, right1.get(), 0.001);
        auto.run();
        assertEquals(-1, left1.get(), 0.001);
        assertEquals(-1, right1.get(), 0.001);
        auto.run();
        assertEquals(-1, left1.get(), 0.001);
        assertEquals(-1, right1.get(), 0.001);
        right1.setPosition(-2000);
        auto.run();
        assertEquals(-1, left1.get(), 0.001);
        assertEquals(-1, right1.get(), 0.001);
        auto.run();
        assertEquals(-1, left1.get(), 0.001);
        assertEquals(-1, right1.get(), 0.001);
        right1.setPosition(-2155);
        auto.run();
        auto.run();
        assertEquals(0, left1.get(), 0.001);
        assertEquals(0, right1.get(), 0.001);
    }
}
